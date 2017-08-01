/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.queries;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import org.obinject.block.MTreeIndex;
import org.obinject.block.MTreeLeaf;
import org.obinject.block.Node;
import org.obinject.device.Session;
import org.obinject.meta.Entity;
import org.obinject.meta.Metric;
import org.obinject.meta.Uuid;
import org.obinject.storage.MTree;

/**
 *
 * @author RenZo Classe que realizada a Juncao Canalizada Paginando seus
 * resultados
 * @param <M>
 */
public class PagedChanneledJoinMTree<M extends Metric<M> & Entity<? super M>> extends AbstractStrategy<M> {

    // lista de objetos da rota
    private final List<M> routesList;
    // alcance ou raio de busca
    private double range;
    // raio inferior
    private final double rinf;
    // variavel que guarda o identificador do último objeto da pesquisa anterior
    private final Uuid oid;
    // variavel que guadara o oid que sera utilizado pela próxima pesquisa
    private Uuid oidLast;
    // numero de objeto maximo em cada pagina
    private final int k;
    // Variavel que guarda a maior distancia de um objeto dentro de um conjunto de resultados
    private double maiordistancia = 0;

    /**
     * Construtor PagedChanneledJoinMTree passando a árvore, a lista de objetos
     * da rota, o raio de pesquisa dos objetos da rota, o raio inferior, o
     * identificador do ultimo objeto adicionado na pesquisa anterior e o limite
     * k de objeto de uma página
     *
     * @param mtree
     * @param routeList
     * @param range
     * @param rinf
     * @param oid
     * @param k
     *
     */
    public PagedChanneledJoinMTree(MTree<M> mtree, List<M> routeList, double range, double rinf, Uuid oid, int k) {
        super(mtree);
        this.routesList = routeList;
        this.range = range;
        this.rinf = rinf;
        this.oid = oid;
        this.k = k;
    }

    // Objeto que utiliza a interfae Comparable para ordenar objetos na PriorityQueue pela distancia do pai
    private class Qualify implements Comparable<Qualify> {

        // Id de uma página
        private final long pageId;
        // Distancia da página ao seu pai
        private final double distanceParentToPointQuery;

        public Qualify(long pageId, double distanceParentToPointQuery) {
            this.pageId = pageId;
            this.distanceParentToPointQuery = distanceParentToPointQuery;
        }

        public double getDistanceParentToPointQuery() {
            return distanceParentToPointQuery;
        }

        public long getPageId() {
            return pageId;
        }

        // * AQUI SERIA NECESSARIO MESMO ORDENAR PELA DISTANCIA DO PAI AO OBJETO DE PESQUISA??? E SE ORDENASSE PELA DISTANCIA?
        @Override
        public int compareTo(Qualify obj) {
            if (this.getDistanceParentToPointQuery() < obj.getDistanceParentToPointQuery()) {
                return -1;
            } else {
                if (this.getDistanceParentToPointQuery() > obj.getDistanceParentToPointQuery()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    // Classe que utiliza a interface Comparator para ordenar uma lista por distancia e identificadores
    private class PagedResultQualify implements Comparable<PagedResultQualify> {

        // uuid de um objeto
        private final Uuid uuid;
        // objeto metric
        M build;

        public PagedResultQualify(M build, Uuid uuid) {
            this.uuid = uuid;
            this.build = build;
        }

        public M getBuild() {
            return build;
        }

        public Uuid getUuid() {
            return uuid;
        }

        // Ordenando pela distancia - da maior para a menor. Se as distancias forem iguais, ordena pelo numero do Oid
        @Override
        public int compareTo(PagedResultQualify p2) {
            if (this.getBuild().getPreservedDistance() < p2.getBuild().getPreservedDistance()) {
                return -1;
            } else if (this.getBuild().getPreservedDistance() > p2.getBuild().getPreservedDistance()) {
                return 1;
            } else {
                if (this.getUuid().getMostSignificantBits() < p2.getUuid().getMostSignificantBits()) {
                    return -1;
                } else if (this.getUuid().getMostSignificantBits() > p2.getUuid().getMostSignificantBits()) {
                    return 1;
                } else if (this.getUuid().getMostSignificantBits() == p2.getUuid().getMostSignificantBits()) {
                    if (this.getUuid().getLeastSignificantBits() < p2.getUuid().getMostSignificantBits()) {
                        return -1;
                    } else if (this.getUuid().getLeastSignificantBits() > p2.getUuid().getMostSignificantBits()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }
            return 0;
        }
    }

    /**
     * Método implementado que realiza a Juncao Canalizada na MTree
     *
     * @return result
     */
    @Override
    public Collection<M> solve() {
        // Variavel que recebera o id de uma pagina do bloco
        long pageId;
        // Variavel que pega a cobertura de um determinado nó
        double cov;
        // Variavel que guardara a distancia do objeto de busca ao pai
        double distParentToQuery;
        // Variável que conta o número de elementos dentro do bloco
        int total;
        // Objeto generico do tipo RangeQualify que recebera cada um dos objetos da fila qualifies
        Qualify objQualify;
        PagedResultQualify pageMaiorDistancia;

        // Abrindo uma sessao com a workspace do usuario onde está localizados os objetos da mtree
        Session se = this.getStructure().getWorkspace().openSession();
        // Lista que guardara os resultados da busca paginada
        PriorityQueue<PagedResultQualify> pagedresult = new PriorityQueue<>();
        // Lista que vai ordenar os nós que foram qualificados de uma MTree / PriorityQueue ordena os elementos baseando-se no metodo compareTo da Classe RangeQualify (Neste caso organiza elemento por quem é mais próximo do pai)
        PriorityQueue<Qualify> qualifies = new PriorityQueue<>();
        // Passando o nó raiz da MTree e um valor bem grande
        qualifies.add(new Qualify(this.getStructure().getRootPageId(), Double.MAX_VALUE));
        // Lista auxiliar que ajudara a retirar objetos excedentes da lista
        LinkedList<PagedResultQualify> repeatedList = new LinkedList<>();
        // Lista de resultados finais
        LinkedList<M> result = new LinkedList<>();

        // Variavel que guardara a distancia da coordenada 'A' à coordenada 'B';
        double aR2;
        // Variavel que guardara a distancia da coordenada 'A' ao ponto do domínio
        double bR2;
        // Variavel que guardara a distancia da coordenada 'B' ao ponto do domínio
        double cR2;

        double aR3;
        double bR3;
        double cR3;

        // Variavel que calcula o perimetro de um triangulo
        double perimetroR2, perimetroR3;
        // Variavel que calcula a area de um triangulo
        double areaR2, areaR3;
        // Variavel que calcula a altura de um triangulo
        double alturaR2, alturaR3;

        // OBJETOS QUE SERAO UTILIZADOS PARA SEMPRE GUARDAR DOIS OBJETOS DA ROTA
        // LEMBRANDO QUE A JUNCAO CANALIZADA VAI SENDO REALIZADA PEGANDO DE DOIS EM DOIS PONTOS
        M coordenadaA;
        M coordenadaB;
        M coordenadaC;

        // Enquanto a lista qualifies nao estiver vazia
        do {

            // Pegando/Retirando primeiro objeto da lista
            objQualify = qualifies.poll();

            // Pegando o identificador do objeto
            pageId = objQualify.getPageId();

            // Calculando a distancia do pai do objeto que esta sendo analisado até o objeto de pesquisa
            // Esta distância é importante pois ela auxilia no cálculo da desigualdade triangular
            distParentToQuery = objQualify.getDistanceParentToPointQuery();

            // Carregando o nó de dentro do bloco a partir de seu identificador
            Node node = se.load(pageId);

            // SE for um NÓ DE ROTEAMENTO (ÍNDICE) SENAO se for um NÓ FOLHA 
            if (MTreeIndex.matchNodeType(node)) {
                // Criando um novo bloco do tipo indice
                MTreeIndex index = new MTreeIndex(node, this.getStructure().getObjectClass());
                // Pegando o número de paginas dentro deste bloco
                total = index.readNumberOfKeys();

                // Para cada um dos objetos dentro do indice
                for (int j = 0; j < total; j++) {
                    // Pegando a cobertura do nó que está sendo analisado
                    cov = index.readCoveringRadius(j);

                    // Desigualdade Triangular (Triangle Inequality) - Pode Externa e depois Poda Interna
                    // Podas são realizadas para evitar calculos de distancia desnecessarios
                    if ((distParentToQuery <= index.readDistanceToParent(j) + cov + range)
                            && (distParentToQuery + cov + index.readDistanceToParent(j) >= rinf)) {
                        M build = (M) index.buildKey(j);

                        // Caso o objeto nao seja podado, realiza o calculo sobre a Juncao Canalizada
                        // Pega-se de dois em dois objetos da lista de rotas
                        for (int i = 0; i < routesList.size() - 1; i++) {
                            coordenadaA = routesList.get(i);
                            coordenadaB = routesList.get(i + 1);

                            // *** AQUI CALCULO DAS TRES REGIOES COM AS BOLOTAS DO OBJETO INDICE
                            aR2 = coordenadaA.distanceTo(coordenadaB);
                            bR2 = build.distanceTo(coordenadaA);
                            cR2 = build.distanceTo(coordenadaB);

                            // DESCOBRINDO A REGIAO PELA QUAL O PONTO DO DOMINIO PERTENCE
                            if (Math.pow(cR2, 2) > Math.pow(bR2, 2) + Math.pow(aR2, 2)) {
                                // REGIAO 1
                                // Se a distancia for menor que o raio e maior que o raio inferior
                                if ((bR2 <= cov + range) && (bR2 + cov >= rinf)) {
                                    if (!qualifies.add(new Qualify(index.readSubPageId(j), bR2))) {
                                        System.out.println("(X) FALHA inserção!!!");
                                    }
                                    break;
                                }

                            } else if (Math.pow(bR2, 2) > Math.pow(cR2, 2) + Math.pow(aR2, 2)) {
                                // REGIAO 3
                                // Se a distancia for menor que o raio e maior que o raio inferior
                                if ((cR2 <= cov + range) && (cR2 + cov >= rinf)) {
                                    if (!qualifies.add(new Qualify(index.readSubPageId(j), cR2))) {
                                        System.out.println("(X) FALHA inserção!!!");
                                    }
                                    break;
                                }
                            } else {
                                // REGIAO 2
                                // Calculando o Perimetro do Triangulo
                                perimetroR2 = (aR2 + bR2 + cR2) / 2;
                                // Calculando a Area do Triangulo
                                areaR2 = Math.sqrt(perimetroR2 * (perimetroR2 - aR2) * (perimetroR2 - bR2) * (perimetroR2 - cR2));
                                // Calculando a altura do triângulo
                                alturaR2 = (2 * areaR2) / aR2;
                                // Se a distancia for menor que o raio e maior que o raio inferior
                                if ((alturaR2 <= cov + range) && (alturaR2 + cov >= rinf)) {
                                    if (!qualifies.add(new Qualify(index.readSubPageId(j), alturaR2))) {
                                        System.out.println("(X) FALHA inserção!!!");
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                // Criando um novo bloco do tipo folha
                MTreeLeaf leaf = new MTreeLeaf(node, this.getStructure().getObjectClass());
                // Pegando o numero de paginas dentro do bloco
                total = leaf.readNumberOfKeys();

                // Para cada uma das páginas dentro do bloco
                for (int j = 0; j < total; j++) {
                    
//                    // Simulacao Sem Poda Externa
//                    M build = this.newGenericType();
//                    leaf.rebuildKey(j, build);
//                    PointFirstAntenna pfa = (PointFirstAntenna) build;
//                    System.out.println("Lat:"+pfa.getLatitude()+","+"Long:"+pfa.getLongitude());
//                    System.out.println("Distancia Pai ao objeto: "+distParentToQuery);
//                    System.out.println("Distancia p/ o Pai: "+leaf.readDistanceToParent(j));
//                    System.out.println("-----------------------------------------------------");
                                
                    
                    // Desigualdade Triangular (Triangle Inequality) - Poda Externa e depois Poda Interna
                    if ((distParentToQuery <= leaf.readDistanceToParent(j) + range)
                            && (distParentToQuery + leaf.readDistanceToParent(j) >= rinf)) {
                        M build = (M) leaf.buildKey(j);

                        // Caso o objeto nao seja podado, realiza o calculo sobre a Juncao Canalizada
                        // Pega-se de dois em dois objetos da lista de rotas
                        for (int i = 0; i < routesList.size() - 1; i++) {
                            coordenadaA = routesList.get(i);
                            coordenadaB = routesList.get(i + 1);

                            // *** AQUI CALCULO DAS TRES REGIOES NA FOLHA
                            aR2 = coordenadaA.distanceTo(coordenadaB);
                            bR2 = build.distanceTo(coordenadaA);
                            cR2 = build.distanceTo(coordenadaB);
                            
//                            PointFirstAntenna pfa = (PointFirstAntenna) build;
//                            if(pfa.getLatitude() == 4 && pfa.getLongitude() == 4)
//                            {
//                                System.out.println("Lat:"+pfa.getLatitude()+","+"Long:"+pfa.getLongitude());
//                                System.out.println("b:"+bR2);
//                                System.out.println("c:"+cR2);
//                            }

                            // DESCOBRINDO A REGIAO PELA QUAL O PONTO DO DOMINIO PERTENCE
                            if (Math.pow(cR2, 2) >= Math.pow(bR2, 2) + Math.pow(aR2, 2)) {
                                // REGIAO 1
                                // Se a distancia for menor que o raio e maior que o raio inferior OU 
                                // a distancia for igual ao raio inferior e a parte mais significativa do OID do nó ser maior que a parte mais significativa do OID do último nó da pesquisa anterior OU 
                                // a parte mais significativa do OID do nó ser igual a parte mais significativa do OID do último nó da pesquisa anterior e a parte menos significativa do OID do nó ser maior que a parte menos significativa do OID do último nó da pesquisa anterior

                                if ((bR2 < rinf) || ((bR2 == rinf)
                                        && ((leaf.readEntityUuid(j).getMostSignificantBits() < oid.getMostSignificantBits())
                                        || ((leaf.readEntityUuid(j).getMostSignificantBits() == oid.getMostSignificantBits())
                                        && (leaf.readEntityUuid(j).getLeastSignificantBits() <= oid.getLeastSignificantBits()))))) {
                                    break;
                                }

                                if (bR2 <= range) {

                                    build.setPreservedDistance(bR2);
                                    Uuid uuidBuild = leaf.readEntityUuid(j);
                                    build.getUuid().setMostSignificantBits(uuidBuild.getMostSignificantBits());
                                    build.getUuid().setLeastSignificantBits(uuidBuild.getLeastSignificantBits());

                                    //PointFirstAntenna pfa = (PointFirstAntenna) build;
                                    //System.out.println(pfa.getLatitude()+","+pfa.getLongitude());
                                    if (!pagedresult.add(new PagedResultQualify(build, leaf.readEntityUuid(j)))) {
                                        System.out.println("(X) FALHA inserção!!!");
                                    }

                                    // RETIRANDO ELEMENTOS ACIMA DO LIMITE DE K
                                    if (pagedresult.size() > k) {
                                        int count = k;
                                        // Retirando da lista de resultados os excedentes
                                        while (count > 0) {
                                            repeatedList.add(pagedresult.poll());
                                            count--;
                                        }
                                        pagedresult.clear();
                                        pagedresult.addAll(repeatedList);
                                        pageMaiorDistancia = repeatedList.get(repeatedList.size() - 1);
                                        repeatedList.clear();
                                        // Atualiza Range PORQUE MESMO?
                                        if (!pagedresult.isEmpty()) {
                                            range = pageMaiorDistancia.getBuild().getPreservedDistance();
                                        }
                                    }

                                    break;
                                }

                            } else if (Math.pow(bR2, 2) >= Math.pow(cR2, 2) + Math.pow(aR2, 2)) {
                                // REGIAO 3

//                                if (routesList.size() - 2 > i) {
//                                    coordenadaC = routesList.get(i + 2);
//                                    aR3 = coordenadaB.distanceTo(coordenadaC);
//                                    bR3 = build.distanceTo(coordenadaB);
//                                    cR3 = build.distanceTo(coordenadaC);
//                                    // Calculando o Perimetro do Triangulo
//                                    perimetroR3 = (aR3 + bR3 + cR3) / 2;
//                                    // Calculando a Area do Triangulo
//                                    areaR3 = Math.sqrt(perimetroR3 * (perimetroR3 - aR3) * (perimetroR3 - bR3) * (perimetroR3 - cR3));
//                                    // Calculando a altura do triângulo
//                                    alturaR3 = (2 * areaR3) / aR3;
//
//                                    if ((alturaR3 < rinf) || ((alturaR3 == rinf)
//                                            && ((leaf.readEntityUuid(j).getMostSignificantBits() < oid.getMostSignificantBits())
//                                            || ((leaf.readEntityUuid(j).getMostSignificantBits() == oid.getMostSignificantBits())
//                                            && (leaf.readEntityUuid(j).getLeastSignificantBits() <= oid.getLeastSignificantBits()))))) {
//                                        break;
//                                    }
//
//                                }                                
                                
//                                PointFirstAntenna pfa = (PointFirstAntenna) build;
//                                if(pfa.getLatitude() == 2 && pfa.getLongitude() == 3)
//                                {
//                                    System.out.println("Lat:"+pfa.getLatitude()+","+"Long:"+pfa.getLongitude());
//                                    System.out.println(bR2);
//                                }
                                
                                
                                if ((cR2 < rinf) || ((cR2 == rinf)
                                        && ((leaf.readEntityUuid(j).getMostSignificantBits() < oid.getMostSignificantBits())
                                        || ((leaf.readEntityUuid(j).getMostSignificantBits() == oid.getMostSignificantBits())
                                        && (leaf.readEntityUuid(j).getLeastSignificantBits() <= oid.getLeastSignificantBits()))))) {
                                    break;
                                }
                                
                                if (cR2 <= range) {

                                    build.setPreservedDistance(cR2);
                                    Uuid uuidBuild = leaf.readEntityUuid(j);
                                    build.getUuid().setMostSignificantBits(uuidBuild.getMostSignificantBits());
                                    build.getUuid().setLeastSignificantBits(uuidBuild.getLeastSignificantBits());

//                                    PointFirstAntenna pfa = (PointFirstAntenna) build;
//                                    System.out.println("Lat:"+pfa.getLatitude()+","+"Long:"+pfa.getLongitude());
                                    if (!pagedresult.add(new PagedResultQualify(build, leaf.readEntityUuid(j)))) {
                                        System.out.println("(X) FALHA inserção!!!");
                                    }

                                    // RETIRANDO ELEMENTOS ACIMA DO LIMITE DE K
                                    if (pagedresult.size() > k) {
                                        int count = k;
                                        // Retirando da lista de resultados os excedentes
                                        while (count > 0) {
                                            repeatedList.add(pagedresult.poll());
                                            count--;
                                        }
                                        pagedresult.clear();
                                        pagedresult.addAll(repeatedList);
                                        pageMaiorDistancia = repeatedList.get(repeatedList.size() - 1);
                                        repeatedList.clear();
                                        // Atualiza Range *PORQUE MESMO?
                                        if (!pagedresult.isEmpty()) {
                                            range = pageMaiorDistancia.getBuild().getPreservedDistance();
                                        }
                                    }

                                    break;
                                }
                            } else {
                                // REGIAO 2
                                // Calculando o Perimetro do Triangulo
                                perimetroR2 = (aR2 + bR2 + cR2) / 2;
                                // Calculando a Area do Triangulo
                                areaR2 = Math.sqrt(perimetroR2 * (perimetroR2 - aR2) * (perimetroR2 - bR2) * (perimetroR2 - cR2));
                                // Calculando a altura do triângulo
                                alturaR2 = (2 * areaR2) / aR2;

                                if ((alturaR2 < rinf) || ((alturaR2 == rinf)
                                        && ((leaf.readEntityUuid(j).getMostSignificantBits() < oid.getMostSignificantBits())
                                        || ((leaf.readEntityUuid(j).getMostSignificantBits() == oid.getMostSignificantBits())
                                        && (leaf.readEntityUuid(j).getLeastSignificantBits() <= oid.getLeastSignificantBits()))))) {
                                    break;
                                }

                                if (alturaR2 <= range) {

                                    //System.out.println("E PONTO É RESPOSTA!");
                                    build.setPreservedDistance(alturaR2);
                                    Uuid uuidBuild = leaf.readEntityUuid(j);
                                    build.getUuid().setMostSignificantBits(uuidBuild.getMostSignificantBits());
                                    build.getUuid().setLeastSignificantBits(uuidBuild.getLeastSignificantBits());

                                    if (!pagedresult.add(new PagedResultQualify(build, leaf.readEntityUuid(j)))) {
                                        System.out.println("(X) FALHA inserção!!!");
                                    }

                                    // RETIRANDO ELEMENTOS ACIMA DO LIMITE DE K
                                    if (pagedresult.size() > k) {
                                        int count = k;
                                        // Retirando da lista de resultados os excedentes
                                        while (count > 0) {
                                            repeatedList.add(pagedresult.poll());
                                            count--;
                                        }
                                        pagedresult.clear();
                                        pagedresult.addAll(repeatedList);
                                        pageMaiorDistancia = repeatedList.get(repeatedList.size() - 1);
                                        repeatedList.clear();
                                        // Atualiza Range PORQUE MESMO?
                                        if (!pagedresult.isEmpty()) {
                                            range = pageMaiorDistancia.getBuild().getPreservedDistance();
                                        }
                                    }

                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } while (!qualifies.isEmpty());
        
        // Gerando a lista de respostas
        while (!pagedresult.isEmpty()) {
            M newbuild = pagedresult.poll().getBuild();
            result.add(newbuild);
        }

        if (result.size() > 0) {
            // Pegando distancia e oid do ultimo elemento da lista de resultados
            maiordistancia = result.get(result.size() - 1).getPreservedDistance();
            oidLast = result.get(result.size() - 1).getUuid();
        }

        se.close();
        
        System.out.println("******************************************************************************");
        return result;
    }

    public double getMaiordistancia() {
        return maiordistancia;
    }

    public void setMaiordistancia(double maiordistancia) {
        this.maiordistancia = maiordistancia;
    }

    public Uuid getOidLast() {
        return oidLast;
    }

    public void setOidLast(Uuid oidLast) {
        this.oidLast = oidLast;
    }

}
