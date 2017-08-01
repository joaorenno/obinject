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

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.obinject.block.SequentialNode;
import org.obinject.device.Session;
import org.obinject.meta.Entity;
import org.obinject.meta.Metric;
import org.obinject.storage.Sequential;

/**
 *
 * @author Enzo Seraphim <seraphim@unifei.edu.br>
 * @author Carlos Ferro <carlosferro@gmail.com>
 * @author Thatyana de Faria Piola Seraphim <thatyana@unifei.edu.br>
 * @author Renzo Mesquita <renzokuken31@gmail.com>
 *
 * Classe que aplica a Junção Canalizada sobre pontos do domínio
 * @param <M>
 */
public abstract class ChanneledJoinSequential<M extends Metric<M> & Entity<? super M>> extends AbstractStrategy<M> {

    // Raio ou Distancia da busca canalizada
    private final double range;
    // Lista com objetos da Rota
    private List<M> routeOfPoint;
    private final Class<M> objectClass;

    // Construtor inicializando com estrutura sequential que guarda os pontos do dominio + raio de busca + lista de rotas
    public ChanneledJoinSequential(Sequential sequential, List<M> valueObjList, double range) {
        super(sequential);
        this.range = range;
        this.routeOfPoint = valueObjList;
        this.objectClass = ((Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public void setRouteOfPoint(List<M> routeOfPoint) {
        this.routeOfPoint = routeOfPoint;
    }

    private M newObjectClass() {
        try {
            return objectClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(AbstractStrategy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Classe implementada que realiza a Juncao Canalizada
    @Override
    public Collection<M> solve() {
        long time = System.nanoTime();
        // Abrindo uma sessao com a workspace do usuario
        Session se = this.getStructure().getWorkspace().openSession();
        long diskAccess = se.getBlockAccess();

        // Lista que retornara o conjunto resposta da Juncao Canalizada
        LinkedList<M> result = new LinkedList<>();

        // Pegando o primeiro ponto do dominio
        long firstNode = this.getStructure().getRootPageId();

        // Se o primeiro ponto nao for vazio
        if (firstNode != 0) {
            long actualPageId = firstNode;
            long firstPageId = actualPageId;
            int total;

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

            // Enquanto houverem blocos...
            do {
                // SequentialNode recebe o primeiro bloco
                SequentialNode actualSeqNode = new SequentialNode(se.load(actualPageId), this.getStructure().getObjectClass());
                // Aqui são pegos todos os ponto do domínio dentro do bloco
                total = actualSeqNode.readNumberOfEntitries();
                for (int j = 0; j < total; j++) {
                    // aqui é pego um dos pontos do domínio dentro do bloco atual
                    M build = this.newObjectClass();
                    actualSeqNode.rebuildEntity(j, build);

                    // Aqui são pegos todos os pontos da rota
                    for (int i = 0; i < routeOfPoint.size() - 1; i++) {
                        coordenadaA = routeOfPoint.get(i);
                        coordenadaB = routeOfPoint.get(i + 1);

                        // Calculando as distancias
                        a = coordenadaA.distanceTo(coordenadaB);
                        b = coordenadaA.distanceTo(build);
                        c = coordenadaB.distanceTo(build);
                        this.getPerformanceMeasurement().incrementVerification(3);

                        // DESCOBRINDO A REGIAO PELA QUAL O PONTO DO DOMINIO PERTENCE
                        if (Math.pow(c, 2) >= Math.pow(b, 2) + Math.pow(a, 2)) {
                            // REGIAO 1
                            //System.out.println("REGIAO 1!");
                            if (b <= range) {
                                //System.out.println("E PONTO É RESPOSTA!");
                                build.setPreservedDistance(b);
                                result.offer(build);
                                break;
                            }

                        } else if (Math.pow(b, 2) >= Math.pow(c, 2) + Math.pow(a, 2)) {
                            // REGIAO 3
                            //System.out.println("REGIAO 3!");
                            if (c <= range) {
                                //System.out.println("E PONTO É RESPOSTA!");
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

                            if (taltura <= range) {
                                //System.out.println("E PONTO É RESPOSTA!");
                                build.setPreservedDistance(taltura);
                                result.offer(build);
                                break;
                            }
                        }
                    }
                }
                actualPageId = actualSeqNode.readNextPageId();

                se.close();

            } while (actualPageId != firstPageId);

        }
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
