/*
 Copyright (C) 2013     Enzo Seraphim

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Lesser General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 or visit <http://www.gnu.org/licenses/>
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
import org.obinject.storage.MTree;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 * @author Renzo Mesquita <renzokuken31@gmail.com>
 *
 * @param <M>
 */
public abstract class ChanneledJoinMTreeMinimumQualificationPositionRoute<M extends Metric<M> & Entity<? super M>> extends AbstractStrategy<M> {

    // lista de objetos da rota
    private List<M> routeOfPoint;
    // alcance ou raio de busca
    private double range;

    // Construtor ChanneledJoinMTree passando a árvore, a lista de objetos da rota e o raio de pesquisa dos objetos da rota
    public ChanneledJoinMTreeMinimumQualificationPositionRoute(MTree<M> mtree, List<M> routeOfPoint, double range) {
        super(mtree);
        this.routeOfPoint = routeOfPoint;
        this.range = range;
    }

    public void setRouteOfPoint(List<M> routeOfPoint) {
        this.routeOfPoint = routeOfPoint;
    }

    public void setRange(double range) {
        this.range = range;
    }

    private class Qualify implements Comparable<Qualify> {

        // Id de uma página
        private final long pageId;
        // Distancia da página ao seu pai?
        private final double distanceParentToPointQuery;
        // Indice da rota
        private final int positionRoute;

        public Qualify(long pageId, double distanceParentToPointQuery, int positionRoute) {
            this.pageId = pageId;
            this.distanceParentToPointQuery = distanceParentToPointQuery;
            this.positionRoute = positionRoute;
        }

        public double getDistanceParentToPointQuery() {
            return distanceParentToPointQuery;
        }

        public long getPageId() {
            return pageId;
        }

        public int getPositionRoute() {
            return positionRoute;
        }

        @Override
        public int compareTo(Qualify obj) {
            if (this.getDistanceParentToPointQuery() < obj.getDistanceParentToPointQuery()) {
                return -1;
            } else if (this.getDistanceParentToPointQuery() > obj.getDistanceParentToPointQuery()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     *
     * @return
     */
    // Método implementado que realiza a Juncao Canalizada na MTree
    @Override
    public Collection<M> solve() {
        long time = System.nanoTime();
        // Variavel que recebera o id de um objeto
        long pageId;
        // Variavel que pega a cobertura de um determinado nó
        double cov;
        // Variavel que guardara a distancia do objeto de busca ao pai
        double distParentToQuery;
        //
        int positionRoute;
        // Variável que conta o número de elementos dentro do bloco
        int total;
        // Objeto generico do tipo RangeQualify que recebera cada um dos objetos da fila qualifies
        Qualify objQualify;

        // Abrindo uma sessao com a workspace do usuario
        Session se = this.getStructure().getWorkspace().openSession();

        long diskAccess = se.getBlockAccess();
        // Lista que retornara os resultados da busca
        LinkedList<M> result = new LinkedList<>();
        // Lista que vai ordenar os elementos de uma MTree / PriorityQueue é uma fila que quando da um poll remove o elemento de maior prioridade da lista
        PriorityQueue<Qualify> qualifies = new PriorityQueue<>();
        // Passando o nó raiz da MTree e um valor bem grande para atingir toda a arvore
        qualifies.add(new Qualify(this.getStructure().getRootPageId(), Double.MAX_VALUE, 0));

        // Variavel que guardara a distancia da coordenada 'A' à coordenada 'B';
        double a;
        // Variavel que guardara a distancia da coordenada 'A' ao ponto do domínio
        double b;
        // Variavel que guardara a distancia da coordenada 'B' ao ponto do domínio
        double c;
        // Variavel que calcula o perimetro de um triangulo
        double tperimetro;
        // Variavel que calcula a area de um triangulo
        double tarea;
        // Variavel que calcula a altura de um triangulo
        double taltura;

        // OBJETOS QUE SERAO UTILIZADOS PARA SEMPRE GUARDAR DOIS OBJETOS DA ROTA
        // LEMBRANDO QUE A JUNCAO CANALIZADA VAI SENDO REALIZADA PEGANDO DE DOIS EM DOIS PONTOS
        M coordenadaA;
        M coordenadaB;

        // Variável que terá de guardar a distância mais próxima do objeto que está sendo analisado ao seu pai
        // Pois na JuncaoCanalizada um objeto podera ser analisado mais de uma vez, e esta variavel deve permitir
        // que guarde o objeto mais próximo do pai
        double minDistParentToQuery;
        double minDistRepresentative;
        // Variável que guardará o PagaId do objeto que se encontra mais próximo do seu pai
        long minSubPageId;
        // min 
        int minPositionRoute;
        boolean notFoundMin;

        // Enquanto a lista qualifies nao estiver vazia
        do {
            // Pegando primeiro objeto da lista
            objQualify = qualifies.poll();

            // Pegando o identificador do objeto
            pageId = objQualify.getPageId();
            // calculando a distancia do pai do objeto que esta sendo analisado até o ponto de pesquisa
            distParentToQuery = objQualify.getDistanceParentToPointQuery();
            // posição da rota
            positionRoute = objQualify.getPositionRoute();

            // Carregando o nó a partir de seu identificador
            Node node = se.load(pageId);

            // SE for um NÓ DE ROTEAMENTO SENAO se for um NÓ FOLHA 
            if (MTreeIndex.matchNodeType(node)) {
                MTreeIndex index = new MTreeIndex(node, this.getStructure().getObjectClass());
                // número de elementos dentro deste bloco
                total = index.readNumberOfKeys();

                // para cada um dos elementos dentro do bloco
                for (int j = 0; j < total; j++) {
                    // Pegando a cobertura do nó que está sendo analisado
                    cov = index.readCoveringRadius(j);

                    //Desigualdade Triangular (Triangle Inequality)
                    if (distParentToQuery - index.readDistanceToParent(j) <= cov + range) {
                        M build = (M) index.buildKey(j);

                        // Esta variável recebe um valor grande para poder sempre pegar o valor mínimo
                        minDistParentToQuery = Double.MAX_VALUE;
                        minDistRepresentative = 0;
                        minSubPageId = 0;
                        minPositionRoute = positionRoute;
                        notFoundMin = true;

                        for (int i = positionRoute; i < routeOfPoint.size() - 1; i++) {
                            coordenadaA = routeOfPoint.get(i);
                            coordenadaB = routeOfPoint.get(i + 1);

                            // *** AQUI CALCULO DAS TRES REGIOES COM AS BOLOTAS
                            a = coordenadaA.distanceTo(coordenadaB);
                            b = coordenadaA.distanceTo(build);
                            c = coordenadaB.distanceTo(build);
                            this.getPerformanceMeasurement().incrementVerification(3);

                            // DESCOBRINDO A REGIAO PELA QUAL O PONTO DO DOMINIO PERTENCE
                            if ((Math.pow(c, 2) >= Math.pow(b, 2) + Math.pow(a, 2))) {
                                // REGIAO 1
                                // Se b for menor que o range + sua cobertura e b for menor que a distancia minima para o pai
                                if ((b <= cov + range) && (b < minDistParentToQuery)) {
                                    minDistParentToQuery = b;
                                    minDistRepresentative = b;
                                    minSubPageId = index.readSubPageId(j);
                                    if (notFoundMin) {
                                        minPositionRoute = i;
                                        notFoundMin = false;
                                    }
                                }
                            } else if ((Math.pow(b, 2) >= Math.pow(c, 2) + Math.pow(a, 2))) {
                                // REGIAO 3
                                // Se c for menor que o range + sua cobertura e c for menor que a distancia minima para o pai
                                if ((c <= cov + range) && (c < minDistParentToQuery)) {
                                    minDistParentToQuery = c;
                                    minDistRepresentative = c;
                                    minSubPageId = index.readSubPageId(j);
                                    if (notFoundMin) {
                                        minPositionRoute = i;
                                        notFoundMin = false;
                                    }
                                }
                            } else {
                                // REGIAO 2
                                //System.out.println("REGIAO 2!");
                                // Calculando o Perimetro do Triangulo
                                tperimetro = (a + b + c) / 2;
                                // Calculando a Area do Triangulo
                                tarea = Math.sqrt(tperimetro * (tperimetro - a) * (tperimetro - b) * (tperimetro - c));
                                // Calculando a altura do triângulo
                                taltura = (2 * tarea) / a;

                                // Se taltura for menor que o range + sua cobertura e taltura for menor que a distancia minima para o pai
                                if ((taltura <= cov + range) && (taltura < minDistParentToQuery)) {
                                    minDistParentToQuery = taltura;
                                    minDistRepresentative = 0;
                                    minSubPageId = index.readSubPageId(j);
                                    if (notFoundMin) {
                                        minPositionRoute = i;
                                        notFoundMin = false;
                                    }
                                }
                            }
                        }

                        // Guarda o objeto do tipo index que foi qualificado em qualifies
                        if (minDistParentToQuery != Double.MAX_VALUE) {
                            if (!qualifies.add(new Qualify(minSubPageId, minDistRepresentative, minPositionRoute))) {
                                System.out.println("@@@@@ falhou na insercao da qualificacao do range");
                            }
                        }
                    }
                }
            } else {
                MTreeLeaf leaf = new MTreeLeaf(node, this.getStructure().getObjectClass());
                // número de elementos dentro deste bloco
                total = leaf.readNumberOfKeys();

                // para cada um dos elementos dentro do bloco
                for (int j = 0; j < total; j++) {
                    //Desigualdade Triangular (Triangle Inequality)
                    if (distParentToQuery - leaf.readDistanceToParent(j) <= range) {
                        M build = (M) leaf.buildKey(j);

                        for (int i = positionRoute; i < routeOfPoint.size() - 1; i++) {
                            coordenadaA = routeOfPoint.get(i);
                            coordenadaB = routeOfPoint.get(i + 1);
                            // *** AQUI CALCULO DAS TRES REGIOES FOLHAS
                            a = coordenadaA.distanceTo(coordenadaB);
                            b = build.distanceTo(coordenadaA);
                            c = build.distanceTo(coordenadaB);
                            this.getPerformanceMeasurement().incrementVerification(3);

                            // DESCOBRINDO A REGIAO PELA QUAL O PONTO DO DOMINIO PERTENCE
                            if (Math.pow(c, 2) >= Math.pow(b, 2) + Math.pow(a, 2)) {
                                // REGIAO 1
                                // Se b for menor que o range e b for menor que a distancia minima para o pai
                                if (b <= range) {
                                    build.setPreservedDistance(b);
                                    result.offer(build);
                                    break;
                                }
                            } else if (Math.pow(b, 2) >= Math.pow(c, 2) + Math.pow(a, 2)) {
                                // REGIAO 3
                                // Se c for menor que o range e c for menor que a distancia minima para o pai
                                if (c <= range) {
                                    build.setPreservedDistance(c);
                                    result.offer(build);
                                    break;
                                }
                            } else {
                                // REGIAO 2
                                //System.out.println("REGIAO 2!");
                                // Calculando o Perimetro do Triangulo
                                tperimetro = (a + b + c) / 2;
                                // Calculando a Area do Triangulo
                                tarea = Math.sqrt(tperimetro * (tperimetro - a) * (tperimetro - b) * (tperimetro - c));
                                // Calculando a altura do triângulo
                                taltura = (2 * tarea) / a;

                                // Se taltura for menor que o range e taltura for menor que a distancia minima para o pai
                                if (taltura <= range) {
                                    build.setPreservedDistance(taltura);
                                    result.offer(build);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } while (!qualifies.isEmpty());

        se.close();

        // Calculando Estatísticas
        diskAccess = se.getBlockAccess() - diskAccess;
        this.getPerformanceMeasurement().incrementDiskAccess(diskAccess);
        time = System.nanoTime() - time;
        this.getPerformanceMeasurement().incrementTime(time);
        this.getPerformanceMeasurement().incrementMeasurement();

        return result;
    }
}
