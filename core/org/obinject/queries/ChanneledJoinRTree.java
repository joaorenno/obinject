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

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.block.RTreeIndex;
import org.obinject.block.RTreeLeaf;
import org.obinject.block.Node;
import org.obinject.device.Session;
import org.obinject.meta.generator.DistanceUtil;
import org.obinject.meta.Entity;
import org.obinject.meta.Point;
import org.obinject.meta.Rectangle;
import org.obinject.storage.EuclideanGeometry;
import org.obinject.storage.RTree;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 * @author Renzo Mesquita <renzokuken31@gmail.com>
 *
 * @param <R>
 */
public abstract class ChanneledJoinRTree<R extends Rectangle<R> & Entity<? super R>> extends AbstractStrategy<R> {

    private final EuclideanGeometry<R> geometry;
    // lista de retangulos da rota
    private final List<R> routeOfRectangle = new ArrayList<>();
    // lista de objetos da rota
    private List<? extends Point> routeOfPoint;
    // alcance ou raio de busca
    private double range;

    private class Qualify {
        // Id de uma página
        private final long pageId;
        // Distancia da página ao seu pai?
        private final int positionRoute;

        public Qualify(long pageId, int positionRoute) {
            this.pageId = pageId;
            this.positionRoute = positionRoute;
        }

        public long getPageId() {
            return pageId;
        }

        public int getPositionRoute() {
            return positionRoute;
        }
                
    }

    // Construtor ChanneledJoinRTree passando a árvore, a lista de objetos da rota e o raio de pesquisa dos objetos da rota
    public ChanneledJoinRTree(RTree<R> rtree, List<? extends Point> routeOfPoint, double range) {
        super(rtree);
        this.range = range;
        this.geometry = new EuclideanGeometry<>(this.getStructure().getObjectClass());
        this.routeOfPoint = routeOfPoint;
        updateRouteOfRetangle();
    }
    
    private void updateRouteOfRetangle(){
        this.routeOfRectangle.clear();
        for (Point point : routeOfPoint) {
            try {
                R rectangle = this.getStructure().getObjectClass().newInstance();
                for (int i = 0; i < point.numberOfDimensions(); i++) {
                    rectangle.setOrigin(i, point.getOrigin(i) - range);
                    rectangle.setExtension(i, 2 * range);
                }
                this.routeOfRectangle.add(rectangle);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ChanneledJoinRTree.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }

    public void setRange(double range) {
        this.range = range;
    }

    public void setRouteOfPoint(List<? extends Point> routeOfPoint) {
        this.routeOfPoint = routeOfPoint;
        updateRouteOfRetangle();
    }

    /**
     *
     * @return
     */
    // Método implementado que realiza a Juncao Canalizada na RTree
    @Override
    public Collection<R> solve() {
        long time = System.nanoTime();
        // Variável que conta o número de elementos dentro do bloco
        int total;

        // Abrindo uma sessao com a workspace do usuario
        Session se = this.getStructure().getWorkspace().openSession();

        long diskAccess = se.getBlockAccess();
        long overlapCalc = this.geometry.getCalculatedOverlap();
        long distanceCalc = 0;

        // Lista que retornara os resultados da busca
        LinkedList<R> result = new LinkedList<>();
        // Lista que vai ordenar os elementos de uma RTree / PriorityQueue é uma fila que quando da um poll remove o elemento de maior prioridade da lista
        Stack<Qualify> qualifies = new Stack<>();
        // Passando o nó raiz da RTree e um valor bem grande para atingir toda a arvore
        qualifies.add(new Qualify(this.getStructure().getRootPageId(), 0));
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
        R unionAB;
        Point coordenadaA;
        Point coordenadaB;
        
        Qualify qualify; 

        // Enquanto a lista qualifies nao estiver vazia
        do {
            // Pegando primeiro identificador de pagina
            qualify = qualifies.pop();
            // Carregando o nó a partir de seu identificador
            Node node = se.load(qualify.getPageId());
            // SE for um NÓ DE ROTEAMENTO SENAO se for um NÓ FOLHA 
            if (RTreeIndex.matchNodeType(node)) {
                RTreeIndex<R> index = new RTreeIndex<>(node, this.getStructure().getObjectClass());
                // número de elementos dentro deste bloco
                total = index.readNumberOfKeys();
                // para cada um dos elementos dentro do bloco
                for (int j = 0; j < total; j++) {
                    R build = index.buildKey(j);
                    // para cada um dos elementos da rota a partir da última rota qualificada
                    for (int i = qualify.getPositionRoute(); i < routeOfRectangle.size() - 1; i++) {
                        unionAB = geometry.union(routeOfRectangle.get(i), routeOfRectangle.get(i + 1));
                        if (geometry.isOverlap(build, unionAB)) {
                            qualifies.add(new Qualify(index.readSubPageId(j), i));
                            break;
                        }
                    }
                }
            } else {
                RTreeLeaf<R> leaf = new RTreeLeaf<>(node, this.getStructure().getObjectClass());
                // número de elementos dentro deste bloco
                total = leaf.readNumberOfKeys();

                // para cada um dos elementos dentro do bloco
                for (int j = 0; j < total; j++) {
                    R build = leaf.buildKey(j);
                    for (int i = qualify.getPositionRoute(); i < routeOfPoint.size() - 1; i++) {
                        coordenadaA = routeOfPoint.get(i);
                        coordenadaB = routeOfPoint.get(i + 1);
                        // *** AQUI CALCULO DAS TRES REGIOES FOLHAS
                        a = coordenadaA.distanceTo(coordenadaB);
                        b = DistanceUtil.euclidean(coordenadaA, build);
                        c = DistanceUtil.euclidean(coordenadaB, build);
                        distanceCalc+=3;

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
        } while (!qualifies.isEmpty());

        se.close();

        // Calculando Estatísticas
        diskAccess = se.getBlockAccess() - diskAccess;
        this.getPerformanceMeasurement().incrementDiskAccess(diskAccess);
        overlapCalc = this.geometry.getCalculatedOverlap() - overlapCalc;
        this.getPerformanceMeasurement().incrementVerification(overlapCalc+distanceCalc);
        time = System.nanoTime() - time;
        this.getPerformanceMeasurement().incrementTime(time);
        this.getPerformanceMeasurement().incrementMeasurement();

        return result;
    }
}
