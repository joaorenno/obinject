
package org.obinject.sample.piratas;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import org.obinject.PersistentManager;
import org.obinject.PersistentManagerFactory;

/**
 *
 * @author well
 */
public class App
{

    public static final Random rand = new Random();
    public static final int maxMercadoria = 50;
    public static final int maxMercante = 4;
    public static final int maxPirata = 4;
    public static final int maxGuerra = 4;
    public static final int maxTripulante = 200;
    public static final int maxMapa = 40;
    public static final int maxLocal = 80;
    public static final int maxDesloca = 120;

    public static float latitudeGenerator()
    {
        Float latit = rand.nextFloat();
        int intPart = rand.nextInt() % 90;
        latit += intPart;
        return latit;
    }

    public static float longitudeGenerator()
    {
        Float longit = rand.nextFloat();
        int intPart = rand.nextInt() % 180;
        longit += intPart;
        return longit;
    }

    public static String stringGenerator()
    {
        int size = rand.nextInt(29) + 5;
        char[] ch = new char[size];
        for (int i = 0; i < size; i++)
        {
            ch[i] = (char) ((rand.nextInt(26)) + 65);
        }
        return new String(ch);
    }
    public static long startDate = new Date().getTime() - (365 * 24 * 60 * 60 * 1000);
    public static long rangeDate = (new Date().getTime()) - startDate;

    public static void main(String[] args)
    {
        ArrayList<Costa> costas = new ArrayList<>(maxLocal);
        ArrayList<Ilha> ilhas = new ArrayList<>(maxLocal);
        ArrayList<Local> locais = new ArrayList<>(maxLocal * 2);
        ArrayList<Auxiliar> auxiliares = new ArrayList<>(maxTripulante);
        ArrayList<Navegador> navegadores = new ArrayList<>(maxTripulante);
        ArrayList<Desloca> deslocamentos = new ArrayList<>(maxDesloca);
        ArrayList<Pirata> piratas = new ArrayList<>(maxPirata);
        ArrayList<Mercante> mercantes = new ArrayList<>(maxMercante);
        ArrayList<Guerra> guerras = new ArrayList<>(maxGuerra);
        ArrayList<Mercadoria> mercadorias = new ArrayList<>(maxMercadoria);
        ArrayList<Navegacao> navegacoes = new ArrayList<>(maxMapa);
        ArrayList<Tesouro> tesouros = new ArrayList<>(maxMapa);

        PersistentManager pm = PersistentManagerFactory.createPersistentManager();
        
        //ADICIONANDO COSTAS
        for (int i = 0; i < maxLocal; i++)
        {
            Costa costa = new Costa();
            costa.setCodigo(i);
            costa.setCidade(stringGenerator());
            costa.setEstado(stringGenerator());
            costa.setLat(rand.nextDouble() * 1000 + 1);
            costa.setLont(rand.nextDouble() * 1000 + 1);
            costa.setPais(stringGenerator());
            costas.add(costa);
            locais.add(costa);
        }

        //ADICIONANDO ILHAS
        for (int i = 0; i < maxLocal; i++)
        {
            Ilha ilha = new Ilha();
            ilha.setLat(rand.nextDouble() * 1000 + 1);
            ilha.setLont(rand.nextDouble() * 1000 + 1);
            ilha.setPais(stringGenerator());
            ilha.setArea(rand.nextFloat() * 10000 + 1);
            ilha.setNome(stringGenerator());

            ilhas.add(ilha);
            locais.add(ilha);
        }

        //ADICIONANDO AUXILIARES
        for (int i = 0; i < maxTripulante; i++)
        {
            Auxiliar auxiliar = new Auxiliar();
            auxiliar.setNome(stringGenerator());
            auxiliar.setApelido(stringGenerator());
            auxiliar.setNacionalidade(stringGenerator());
            auxiliar.setHorasNavegacao(rand.nextInt(10000));
            auxiliares.add(auxiliar);
        }


        //ADICIONANDO NAVEGADORES
        for (int i = 0; i < maxTripulante; i++)
        {
            Navegador navegador = new Navegador();
            navegador.setNome(stringGenerator());
            navegador.setApelido(stringGenerator());
            navegador.setNacionalidade(stringGenerator());
            navegador.setCapitao(true);
            navegadores.add(navegador);
        }


        //ADICIONANDO DESLOCAMENTOS
        for (int i = 0; i < maxDesloca; i++)
        {
            Desloca desloca = new Desloca();
            desloca.setDistancia(rand.nextFloat() * 10000 + 1);
            desloca.setDtOrigem(new Date(startDate + (rand.nextLong() % rangeDate)));
            desloca.setDtDestino(new Date(desloca.getDtOrigem().getTime() + rangeDate));
            desloca.setLocalDestino(locais.get(rand.nextInt(maxLocal * 2)));
            desloca.setLocalOrigem(locais.get(rand.nextInt(maxLocal * 2)));
            deslocamentos.add(desloca);
        }

        //ADICIONANDO MERCADORIAS
        for (int i = 0; i < maxMercadoria; i++)
        {
            Mercadoria mercadoria = new Mercadoria();
            mercadoria.setPeso(rand.nextFloat() * 1000 + 1);
            mercadoria.setPreco(rand.nextFloat() * 10000 + 1);
            mercadorias.add(mercadoria);
        }


        ArrayList<Desloca> deslocaNavios = new ArrayList<>(deslocamentos);
        ArrayList<Navegador> navegadoresNavios = new ArrayList<>(navegadores);
        ArrayList<Auxiliar> auxiliaresNavios = new ArrayList<>(auxiliares);


        //ADICIONANDO NAVIO PIRATA -> DESLOCAMENTOS, AUXILIARES E NAVEGADORES
        for (int i = 0; i < maxPirata; i++)
        {
            Pirata pirata = new Pirata();

            pirata.setNome(stringGenerator());
            pirata.setConstruido(stringGenerator());
            pirata.setQtdMastro(rand.nextInt(100));
            pirata.setQtdVelas(rand.nextInt(100));
            pirata.setTamanho(rand.nextInt(10000));
            pirata.setQtdCanhoes(rand.nextInt(100));

            int maxDeslocNavios = rand.nextInt(maxDesloca / maxPirata / 3);

            for (int j = 0; j < maxDeslocNavios; j++)
            {
                int rem = rand.nextInt(deslocaNavios.size());
                pirata.addDesloca(deslocaNavios.get(rem));
                deslocaNavios.remove(rem);
            }


            int maxTripNavio = rand.nextInt(maxTripulante / maxPirata / 3);

            for (int j = 0; j < maxTripNavio; j++)
            {
                int remNav = rand.nextInt(navegadoresNavios.size());
                int remAux = rand.nextInt(auxiliaresNavios.size());

                pirata.addTripulante(navegadoresNavios.get(remNav));
                navegadoresNavios.remove(remNav);

                pirata.addTripulante(auxiliaresNavios.get(remAux));
                auxiliaresNavios.remove(remAux);
            }

            piratas.add(pirata);
        }

        ArrayList<Mercadoria> mercadoriasMercante = new ArrayList<>(mercadorias);


        //ADICIONANDO MERCANTES -> MERCADORIAS, TRIPULANTES, DESLOCAMENTOS,
        //PIRATAS
        for (int i = 0; i < maxMercante; i++)
        {

            Mercante mercante = new Mercante();
            mercante.setNome(stringGenerator());
            mercante.setConstruido(stringGenerator());
            mercante.setCapacidade(rand.nextInt(1000));
            mercante.setQtdMastro(rand.nextInt(100));
            mercante.setQtdVelas(rand.nextInt(100));
            mercante.setTamanho(rand.nextInt(10000));

            int maxMercNavio = rand.nextInt(mercadoriasMercante.size() / maxMercante);

            for (int j = 0; j < maxMercNavio; j++)
            {
                int rem = rand.nextInt(mercadoriasMercante.size());
                mercante.addMercadoria(mercadoriasMercante.get(rem));
                mercadoriasMercante.remove(rem);
            }

            int maxDeslocNavios = rand.nextInt(maxDesloca / maxMercante / 3);

            for (int j = 0; j < maxDeslocNavios; j++)
            {
                int rem = rand.nextInt(deslocaNavios.size());
                mercante.addDesloca(deslocaNavios.get(rem));
                deslocaNavios.remove(rem);
            }


            int maxTripNavio = rand.nextInt(maxTripulante / maxMercante / 3);

            for (int j = 0; j < maxTripNavio; j++)
            {
                int remNav = rand.nextInt(navegadoresNavios.size());
                int remAux = rand.nextInt(auxiliaresNavios.size());

                mercante.addTripulante(navegadoresNavios.get(remNav));
                navegadoresNavios.remove(remNav);

                mercante.addTripulante(auxiliaresNavios.get(remAux));
                auxiliaresNavios.remove(remAux);
            }

            int maxPiratasMerc = rand.nextInt(maxPirata);
            ArrayList<Pirata> piratasMercante = new ArrayList<>(piratas);

            for (int j = 0; j < maxPiratasMerc; j++)
            {
                int rem = rand.nextInt(maxPirata - j);
                mercante.addPirata(piratasMercante.get(rem));
                piratasMercante.remove(rem);
                piratas.get(rem).addMercante(mercante);
            }

            mercantes.add(mercante);

        }


        //ADICIONANDO GUERRAS -> DESLOCAMENTOS, TRIPULANTES, PIRATAS

        for (int i = 0; i < maxGuerra; i++)
        {
            Guerra guerra = new Guerra();
            guerra.setNome(stringGenerator());
            guerra.setConstruido(stringGenerator());
            guerra.setQtdCanhoes(rand.nextInt(100));
            guerra.setQtdMastro(rand.nextInt(100));
            guerra.setQtdVelas(rand.nextInt(100));
            guerra.setTamanho(rand.nextInt(10000));

            int maxDeslocNavios = rand.nextInt(maxDesloca / maxGuerra / 3);

            for (int j = 0; j < maxDeslocNavios; j++)
            {
                int rem = rand.nextInt(deslocaNavios.size());
                guerra.addDesloca(deslocaNavios.get(rem));
                deslocaNavios.remove(rem);
            }


            int maxTripNavio = rand.nextInt(maxTripulante / maxGuerra / 3);

            for (int j = 0; j < maxTripNavio; j++)
            {
                int remNav = rand.nextInt(navegadoresNavios.size());
                int remAux = rand.nextInt(auxiliaresNavios.size());

                guerra.addTripulante(navegadoresNavios.get(remNav));
                navegadoresNavios.remove(remNav);

                guerra.addTripulante(auxiliaresNavios.get(remAux));
                auxiliaresNavios.remove(remAux);
            }

            int maxPiratasGuerra = rand.nextInt(maxPirata);
            ArrayList<Pirata> piratasGuerra = new ArrayList<>(piratas);

            for (int j = 0; j < maxPiratasGuerra; j++)
            {
                int rem = rand.nextInt(maxPirata - j);
                guerra.addPirata(piratasGuerra.get(rem));
                piratasGuerra.remove(rem);
                piratas.get(rem).addGuerra(guerra);
            }

            guerras.add(guerra);

        }

        //ADICIONANDO MAPA DE NAVEGACAO-> COSTAS, NAVEGADOR
        for (int i = 0; i < maxMapa; i++)
        {
            Navegacao navegacao = new Navegacao();
            navegacao.setOceano(stringGenerator());
            navegacao.setEscala(rand.nextDouble() * 10000 + 1);
            navegacao.setNome(stringGenerator());
            navegacao.setQtdRota(rand.nextInt(100));

            navegacao.setCosta(costas.get(rand.nextInt(maxLocal)));

            int naveg = rand.nextInt(maxTripulante);
            navegacao.setNavegador(navegadores.get(naveg));
            navegadores.get(naveg).addMapa(navegacao);

            navegacoes.add(navegacao);

        }

        //ADICIONANDO MAPA DO TESOURO -> NAVEGADOR, ILHA
        for (int i = 0; i < maxMapa; i++)
        {
            Tesouro tesouro = new Tesouro();
            tesouro.setEscala(rand.nextDouble() * 10000 + 1);
            tesouro.setNome(stringGenerator());
            tesouro.setQtdRota(rand.nextInt(100));
            tesouro.setIlha(ilhas.get(rand.nextInt(maxLocal)));
            tesouro.setValorTesouro(rand.nextFloat() * 100000 + 1);

            int naveg = rand.nextInt(maxTripulante);
            tesouro.setNavegador(navegadores.get(naveg));
            navegadores.get(naveg).addMapa(tesouro);

            tesouros.add(tesouro);
        }

    }
}
