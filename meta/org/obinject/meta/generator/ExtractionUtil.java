/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.obinject.meta.generator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

/**
 *
 * @author windows
 */
public class ExtractionUtil {

    public static final int LENGTH_HISTOGRAM = 16;
    public static final int LENGTH_HISTOGRAM_STATISTICAL = 6;
    public static final int LENGTH_HARALICK_SYMMETRIC = 7;
    public static final int LENGTH_HARALICK_ASSYMMETRIC = 7;

    //haralickSymmetric
    private static final double glcm0[][] = new double[256][256];
    private static final double glcm45[][] = new double[256][256];
    private static final double glcm90[][] = new double[256][256];
    private static final double glcm135[][] = new double[256][256];
    private static final double glcm0Normalizada[][] = new double[256][256];
    private static final double glcm45Normalizada[][] = new double[256][256];
    private static final double glcm90Normalizada[][] = new double[256][256];
    private static final double glcm135Normalizada[][] = new double[256][256];
    private static final double vetorAsm[] = new double[4];
    private static final double vetorContraste[] = new double[4];
    private static final double vetorEntropia[] = new double[4];
    private static final double vetorHomogeneidade[] = new double[4];
    private static final double vetorHeterogeneidadeI[] = new double[4];
    private static final double vetorHeterogeneidadeJ[] = new double[4];
    private static final double vetorCorrelacao[] = new double[4];

    public static double[] histogram(BufferedImage image) {
        double featureVector[] = new double[LENGTH_HISTOGRAM];
        if (image == null) {
            return featureVector;
        }
        Raster raster = image.getRaster();
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                featureVector[(int) (raster.getSample(i, j, 0) / 16)]++;
            }
        }
        return featureVector;
    }

    public static double[] histogramStatistical(BufferedImage image) {
        double featureVector[] = new double[LENGTH_HISTOGRAM_STATISTICAL];
        if (image == null) {
            return featureVector;
        }
        int histograma[] = new int[256];
        double histogramaNormalizado[] = new double[256];
        int largura = image.getWidth();
        int altura = image.getHeight();
        int tonsCinza = 256;
        double numeroPixels = image.getWidth() * image.getHeight();

        Raster raster = image.getRaster();
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                histograma[(int) (raster.getSample(i, j, 0))]++;
            }
        }

        //Calculo dos descritores estatisticos
        double media = 0;
        double variancia = 0;
        double desvioPadrao;
        double assimetria = 0;
        double energia = 0;
        double entropia = 0;

        //Normalizando o histograma
        for (int i = 0; i < tonsCinza; i++) {
            histogramaNormalizado[i] = histograma[i] / numeroPixels;
            media = media + (i * histograma[i]);
        }
        media = media / numeroPixels;

        for (int i = 0; i < tonsCinza; i++) {
            if (histogramaNormalizado[i] != 0) {
                variancia = variancia + (Math.pow(i - media, 2) * histograma[i]);
                assimetria = assimetria + (Math.pow(i - media, 3) * histograma[i]);
                energia = energia + (Math.pow(histogramaNormalizado[i], 2));
                entropia = entropia + (histogramaNormalizado[i] * Math.log10(histogramaNormalizado[i]));
            }
        }
        desvioPadrao = Math.sqrt(variancia);
        entropia = entropia * (-1);

        featureVector[0] = media;
        featureVector[1] = variancia;
        featureVector[2] = desvioPadrao;
        featureVector[3] = assimetria;
        featureVector[4] = energia;
        featureVector[5] = entropia;

        return featureVector;
    }

    public static double[] haralickSymmetric(BufferedImage image) {
        double featureVector[] = new double[LENGTH_HARALICK_SYMMETRIC];
        if (image == null) {
            return featureVector;
        }
        int largura = image.getWidth();
        int altura = image.getHeight();
        int tonsCinza = 256;
        int matrizImagem[][] = new int[image.getWidth()][image.getHeight()];

        Raster raster = image.getRaster();
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                matrizImagem[i][j] = (int) (raster.getSample(i, j, 0));
            }
        }

        //Preenchendo as matrizes de cococorrencia com valores negativos
        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                glcm0[i][j] = -1;
                glcm45[i][j] = -1;
                glcm90[i][j] = -1;
                glcm135[i][j] = -1;
            }
        }

        //Calculando a frequencia dos relacionamentos entre os pixels n e vizinho
        int n;
        int vizinho;
        int count = 0;

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura - 1; j++) {
                n = matrizImagem[j][i];
                vizinho = matrizImagem[j + 1][i];
                if ((glcm0[n][vizinho] == (-1)) || (glcm0[vizinho][n] == (-1))) {
                    for (int k = 0; k < altura; k++) {
                        for (int z = 0; z < largura - 1; z++) {
                            if ((matrizImagem[z][k] == n) && (matrizImagem[z + 1][k] == vizinho)) {
                                count++;
                            }
                            if ((matrizImagem[z][k] == vizinho) && (matrizImagem[z + 1][k] == n)) {
                                count++;
                            }
                        }
                    }
                    //Preechendo com os valores na matriz de coocorrencia - direcao 0º e distancia 1
                    if (n == vizinho) {             //Na diagonal principal
                        glcm0[n][vizinho] = count;
                    } else {                        //Quando nao e na diagonal principal (simetria)
                        glcm0[n][vizinho] = count;
                        glcm0[vizinho][n] = count;
                    }
                    count = 0;
                }
            }
        }

        count = 0;
        for (int i = 1; i < altura; i++) {
            for (int j = 0; j < largura - 1; j++) {
                n = matrizImagem[j][i];
                vizinho = matrizImagem[j + 1][i - 1];
                if ((glcm45[n][vizinho] == (-1)) || (glcm45[vizinho][n] == (-1))) {
                    for (int k = 1; k < altura; k++) {
                        for (int z = 0; z < largura - 1; z++) {
                            if ((matrizImagem[z][k] == n) && (matrizImagem[z + 1][k - 1] == vizinho)) {
                                count++;
                            }
                            if ((matrizImagem[z][k] == vizinho) && (matrizImagem[z + 1][k - 1] == n)) {
                                count++;
                            }
                        }
                    }
                    //Preechendo com os valores na matriz de coocorrencia - direcao 45º e distancia 1
                    if (n == vizinho) {             //Na diagonal principal
                        glcm45[n][vizinho] = count;
                    } else {                        //Quando nao e na diagonal principal (simetria)
                        glcm45[n][vizinho] = count;
                        glcm45[vizinho][n] = count;
                    }
                    count = 0;
                }
            }
        }

        count = 0;
        for (int i = 1; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                n = matrizImagem[j][i];
                vizinho = matrizImagem[j][i - 1];
                if ((glcm90[n][vizinho] == (-1)) || (glcm90[vizinho][n] == (-1))) {
                    for (int k = 1; k < altura; k++) {
                        for (int z = 0; z < largura; z++) {
                            if ((matrizImagem[z][k] == n) && (matrizImagem[z][k - 1] == vizinho)) {
                                count++;
                            }
                            if ((matrizImagem[z][k] == vizinho) && (matrizImagem[z][k - 1] == n)) {
                                count++;
                            }
                        }
                    }
                    //Preechendo com os valores na matriz de coocorrencia - direcao 90º e distancia 1
                    if (n == vizinho) {              //Na diagonal principal
                        glcm90[n][vizinho] = count;
                    } else {                         //Quando nao e na diagonal principal (simetria)
                        glcm90[n][vizinho] = count;
                        glcm90[vizinho][n] = count;

                    }
                    count = 0;
                }

            }
        }

        count = 0;
        for (int i = 1; i < altura; i++) {
            for (int j = 1; j < largura; j++) {
                n = matrizImagem[j][i];
                vizinho = matrizImagem[j - 1][i - 1];
                if ((glcm135[n][vizinho] == (-1)) || (glcm135[vizinho][n] == (-1))) {
                    for (int k = 1; k < altura; k++) {
                        for (int z = 1; z < largura; z++) {
                            if ((matrizImagem[z][k] == n) && (matrizImagem[z - 1][k - 1] == vizinho)) {
                                count++;
                            }
                            if ((matrizImagem[z][k] == vizinho) && (matrizImagem[z - 1][k - 1] == n)) {
                                count++;
                            }
                        }
                    }
                    //Preechendo com os valores na matriz de coocorrencia - direcao 135º e distancia 1

                    if (n == vizinho) {               //Na diagonal principal
                        glcm135[n][vizinho] = count;
                    } else {                          //Quando nao e na diagonal principal (simetria)
                        glcm135[n][vizinho] = count;
                        glcm135[vizinho][n] = count;
                    }
                    count = 0;
                }
            }
        }

        //Colocando valor zero onde não foi encontrado nenhuma frequencia
        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                if (glcm0[i][j] == -1) {
                    glcm0[i][j] = 0;
                }
                if (glcm45[i][j] == -1) {
                    glcm45[i][j] = 0;
                }
                if (glcm90[i][j] == -1) {
                    glcm90[i][j] = 0;
                }
                if (glcm135[i][j] == -1) {
                    glcm135[i][j] = 0;
                }
            }
        }

        //r1, r2, r3 e r4 são constantes de normalizacao
        double r1;
        double r2;
        double r3;
        double r4;

        r1 = 2 * altura * (largura - 1);
        r2 = 2 * (altura - 1) * (largura - 1);
        r3 = 2 * largura * (altura - 1);
        r4 = 2 * (largura - 1) * (altura - 1);

        //Normalizando as matrizes de coocorrencia
        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                glcm0Normalizada[i][j] = glcm0[i][j] / r1;
                glcm45Normalizada[i][j] = glcm45[i][j] / r2;
                glcm90Normalizada[i][j] = glcm90[i][j] / r3;
                glcm135Normalizada[i][j] = glcm135[i][j] / r4;
            }
        }

        //Calculo dos descritores estatisticos
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        double sum4 = 0;
        double sum5 = 0;
        double sum6 = 0;
        double sum7 = 0;
        double sum8 = 0;
        double sum9 = 0;
        double sum10 = 0;
        double sum11 = 0;
        double sum12 = 0;
        double sum13 = 0;
        double sum14 = 0;
        double sum15 = 0;
        double sum16 = 0;
        double sum17 = 0;
        double sum18 = 0;
        double sum19 = 0;
        double sum20 = 0;
        double sum21 = 0;
        double sum22 = 0;
        double sum23 = 0;
        double sum24 = 0;
        double sum25 = 0;
        double sum26 = 0;
        double sum27 = 0;
        double sum28 = 0;
        double sum29 = 0;
        double sum30 = 0;
        double sum31 = 0;
        double sum32 = 0;
        double sum33 = 0;
        double sum34 = 0;
        double sum35 = 0;
        double sum36 = 0;
        double sum37 = 0;
        double sum38 = 0;
        double sum39 = 0;
        double sum40 = 0;
        double sum41 = 0;
        double sum42 = 0;
        double sum43;
        double sum44;
        double sum45;
        double sum46;
        double sum47;
        double sum48;
        double sum49;
        double sum50;
        double sum51 = 0;

        //Calculo dos extratores de caracteristicas de Haralick
        //Calculo para media das distribuicoes marginais
        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                sum21 = sum21 + (i * glcm0Normalizada[i][j]);
                sum22 = sum22 + (i * glcm45Normalizada[i][j]);
                sum23 = sum23 + (i * glcm90Normalizada[i][j]);
                sum24 = sum24 + (i * glcm135Normalizada[i][j]);
                sum25 = sum25 + (j * glcm0Normalizada[i][j]);
                sum26 = sum26 + (j * glcm45Normalizada[i][j]);
                sum27 = sum27 + (j * glcm90Normalizada[i][j]);
                sum28 = sum28 + (j * glcm135Normalizada[i][j]);
            }
        }

        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                if (glcm0Normalizada[i][j] != 0) {
                    sum1 = sum1 + (Math.pow(glcm0Normalizada[i][j], 2));
                    sum5 = sum5 + ((Math.pow((i - j), 2)) * glcm0Normalizada[i][j]);
                    sum9 = sum9 + (glcm0Normalizada[i][j] * (Math.log(glcm0Normalizada[i][j]) / Math.log(10)));
                    sum13 = sum13 + ((1 / (1 + (Math.pow((i - j), 2)))) * glcm0Normalizada[i][j]);
                    sum29 = sum29 + ((Math.pow(i - sum21, 2) * glcm0Normalizada[i][j]));
                    sum33 = sum33 + ((Math.pow(j - sum25, 2) * glcm0Normalizada[i][j]));
                    sum39 = sum39 + ((i - sum21) * (j - sum25) * glcm0Normalizada[i][j]);
                }
                if (glcm45Normalizada[i][j] != 0) {
                    sum2 = sum2 + (Math.pow(glcm45Normalizada[i][j], 2));
                    sum6 = sum6 + ((Math.pow((i - j), 2)) * glcm45Normalizada[i][j]);
                    sum10 = sum10 + (glcm45Normalizada[i][j] * (Math.log(glcm45Normalizada[i][j]) / Math.log(10)));
                    sum14 = sum14 + ((1 / (1 + (Math.pow((i - j), 2)))) * glcm45Normalizada[i][j]);
                    sum30 = sum30 + ((Math.pow(i - sum22, 2) * glcm45Normalizada[i][j]));
                    sum34 = sum34 + ((Math.pow(j - sum26, 2) * glcm45Normalizada[i][j]));
                    sum40 = sum40 + ((i - sum22) * (j - sum26) * glcm45Normalizada[i][j]);
                }
                if (glcm90Normalizada[i][j] != 0) {
                    sum3 = sum3 + (Math.pow(glcm90Normalizada[i][j], 2));
                    sum7 = sum7 + ((Math.pow((i - j), 2)) * glcm90Normalizada[i][j]);
                    sum11 = sum11 + (glcm90Normalizada[i][j] * (Math.log(glcm90Normalizada[i][j]) / Math.log(10)));
                    sum15 = sum15 + ((1 / (1 + (Math.pow((i - j), 2)))) * glcm90Normalizada[i][j]);
                    sum31 = sum31 + ((Math.pow(i - sum23, 2) * glcm90Normalizada[i][j]));
                    sum35 = sum35 + ((Math.pow(j - sum27, 2) * glcm90Normalizada[i][j]));
                    sum41 = sum41 + ((i - sum23) * (j - sum27) * glcm90Normalizada[i][j]);
                }
                if (glcm135Normalizada[i][j] != 0) {
                    sum4 = sum4 + (Math.pow(glcm135Normalizada[i][j], 2));
                    sum8 = sum8 + ((Math.pow((i - j), 2)) * glcm135Normalizada[i][j]);
                    sum12 = sum12 + (glcm135Normalizada[i][j] * (Math.log(glcm135Normalizada[i][j]) / Math.log(10)));
                    sum16 = sum16 + ((1 / (1 + (Math.pow((i - j), 2)))) * glcm135Normalizada[i][j]);
                    sum32 = sum32 + ((Math.pow(i - sum24, 2) * glcm135Normalizada[i][j]));
                    sum36 = sum36 + ((Math.pow(j - sum28, 2) * glcm135Normalizada[i][j]));
                    sum42 = sum42 + ((i - sum24) * (j - sum28) * glcm135Normalizada[i][j]);
                }
            }
        }

        //Desvio padrao (raiz quadrada da variancia ou heterogeneidade)
        sum43 = Math.sqrt(sum29);
        sum44 = Math.sqrt(sum33);
        sum45 = Math.sqrt(sum30);
        sum46 = Math.sqrt(sum34);
        sum47 = Math.sqrt(sum31);
        sum48 = Math.sqrt(sum35);
        sum49 = Math.sqrt(sum32);
        sum50 = Math.sqrt(sum36);

        vetorAsm[0] = sum1;
        vetorAsm[1] = sum2;
        vetorAsm[2] = sum3;
        vetorAsm[3] = sum4;
        vetorContraste[0] = sum5;
        vetorContraste[1] = sum6;
        vetorContraste[2] = sum7;
        vetorContraste[3] = sum8;
        vetorEntropia[0] = (-1) * sum9;
        vetorEntropia[1] = (-1) * sum10;
        vetorEntropia[2] = (-1) * sum11;
        vetorEntropia[3] = (-1) * sum12;
        vetorHomogeneidade[0] = sum13;
        vetorHomogeneidade[1] = sum14;
        vetorHomogeneidade[2] = sum15;
        vetorHomogeneidade[3] = sum16;
        vetorHeterogeneidadeI[0] = sum29;
        vetorHeterogeneidadeJ[0] = sum33;
        vetorHeterogeneidadeI[1] = sum30;
        vetorHeterogeneidadeJ[1] = sum34;
        vetorHeterogeneidadeI[2] = sum31;
        vetorHeterogeneidadeJ[2] = sum35;
        vetorHeterogeneidadeI[3] = sum32;
        vetorHeterogeneidadeJ[3] = sum36;
        vetorCorrelacao[0] = sum39 * (1 / (sum43 * sum44));
        vetorCorrelacao[1] = sum40 * (1 / (sum45 * sum46));
        vetorCorrelacao[2] = sum41 * (1 / (sum47 * sum48));
        vetorCorrelacao[3] = sum42 * (1 / (sum49 * sum50));

        //Realizando a media dos valores 
        for (int i = 0; i < 4; i++) {
            sum17 = vetorAsm[i] + sum17;
            sum18 = vetorContraste[i] + sum18;
            sum19 = vetorEntropia[i] + sum19;
            sum20 = vetorHomogeneidade[i] + sum20;
            sum37 = vetorHeterogeneidadeI[i] + sum37;
            sum38 = vetorHeterogeneidadeJ[i] + sum38;
            sum51 = vetorCorrelacao[i] + sum51;
        }
        featureVector[0] = sum17 / 4;
        featureVector[1] = sum18 / 4;
        featureVector[2] = sum19 / 4;
        featureVector[3] = sum20 / 4;
        featureVector[4] = sum37 / 4;
        featureVector[5] = sum38 / 4;
        featureVector[6] = sum51 / 4;

        return featureVector;
    }

    public static double[] haralickAssymmetric(BufferedImage image) {
        double featureVector[] = new double[LENGTH_HARALICK_ASSYMMETRIC];
        if (image == null) {
            return featureVector;
        }
        int largura = image.getWidth();
        int altura = image.getHeight();
        int tonsCinza = 256;
        int matrizImagem[][] = new int[image.getWidth()][image.getHeight()];

        Raster raster = image.getRaster();
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        for (int i = 0; i < largura; i++) {
            for (int j = 0; j < altura; j++) {
                matrizImagem[i][j] = (int) (raster.getSample(i, j, 0));
            }
        }

        //Preenchendo as matrizes de cococorrencia com valores negativos
        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                glcm0[i][j] = -1;
                glcm45[i][j] = -1;
                glcm90[i][j] = -1;
                glcm135[i][j] = -1;
            }
        }

        //Calculando a frequencia dos relacionamentos entre os pixels n e vizinho
        int n;
        int vizinho;
        int count = 0;

        double soma1 = 0;
        double soma2 = 0;
        double soma3 = 0;
        double soma4 = 0;

        for (int i = 0; i < altura; i++) {
            for (int j = 0; j < largura - 1; j++) {
                n = matrizImagem[j][i];
                vizinho = matrizImagem[j + 1][i];
                if (glcm0[n][vizinho] == -1) {
                    for (int k = 0; k < altura; k++) {
                        for (int z = 0; z < largura - 1; z++) {
                            if ((matrizImagem[z][k] == n) && (matrizImagem[z + 1][k] == vizinho)) {
                                count++;
                            }
                        }
                    }
                    //Preechendo com os valores na matriz de coocorrencia - direcao 0º e distancia 1
                    glcm0[n][vizinho] = count;
                    soma1 = soma1 + count;
                    count = 0;

                }
            }
        }

        count = 0;
        for (int i = 1; i < altura; i++) {
            for (int j = 0; j < largura - 1; j++) {
                n = matrizImagem[j][i];
                vizinho = matrizImagem[j + 1][i - 1];
                if (glcm45[n][vizinho] == -1) {
                    for (int k = 1; k < altura; k++) {
                        for (int z = 0; z < largura - 1; z++) {
                            if ((matrizImagem[z][k] == n) && (matrizImagem[z + 1][k - 1] == vizinho)) {
                                count++;
                            }
                        }
                    }
                    //Preechendo com os valores na matriz de coocorrencia - direcao 45º e distancia 1
                    glcm45[n][vizinho] = count;
                    soma2 = soma2 + count;
                    count = 0;
                }
            }
        }

        count = 0;
        for (int i = 1; i < altura; i++) {
            for (int j = 0; j < largura; j++) {
                n = matrizImagem[j][i];
                vizinho = matrizImagem[j][i - 1];
                if (glcm90[n][vizinho] == -1) {
                    for (int k = 1; k < altura; k++) {
                        for (int z = 0; z < largura; z++) {
                            if ((matrizImagem[z][k] == n) && (matrizImagem[z][k - 1] == vizinho)) {
                                count++;
                            }
                        }
                    }
                    //Preechendo com os valores na matriz de coocorrencia - direcao 90º e distancia 1
                    glcm90[n][vizinho] = count;
                    soma3 = soma3 + count;
                    count = 0;
                }
            }
        }

        count = 0;
        for (int i = 1; i < altura; i++) {
            for (int j = 1; j < largura; j++) {
                n = matrizImagem[j][i];
                vizinho = matrizImagem[j - 1][i - 1];
                if (glcm135[n][vizinho] == -1) {
                    for (int k = 1; k < altura; k++) {
                        for (int z = 1; z < largura; z++) {
                            if ((matrizImagem[z][k] == n) && (matrizImagem[z - 1][k - 1] == vizinho)) {
                                count++;
                            }
                        }
                    }
                    //Preechendo com os valores na matriz de coocorrencia - direcao 135º e distancia 1
                    glcm135[n][vizinho] = count;
                    soma4 = soma4 + count;
                    count = 0;
                }
            }
        }

        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                if (glcm0[i][j] == -1) {
                    glcm0[i][j] = 0;
                }
                if (glcm45[i][j] == -1) {
                    glcm45[i][j] = 0;
                }
                if (glcm90[i][j] == -1) {
                    glcm90[i][j] = 0;
                }
                if (glcm135[i][j] == -1) {
                    glcm135[i][j] = 0;
                }
            }
        }

        //Normalizando as matrizes de coocorrencia
        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                glcm0Normalizada[i][j] = glcm0[i][j] / soma1;
                glcm45Normalizada[i][j] = glcm45[i][j] / soma2;
                glcm90Normalizada[i][j] = glcm90[i][j] / soma3;
                glcm135Normalizada[i][j] = glcm135[i][j] / soma4;
            }
        }
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        double sum4 = 0;
        double sum5 = 0;
        double sum6 = 0;
        double sum7 = 0;
        double sum8 = 0;
        double sum9 = 0;
        double sum10 = 0;
        double sum11 = 0;
        double sum12 = 0;
        double sum13 = 0;
        double sum14 = 0;
        double sum15 = 0;
        double sum16 = 0;
        double sum17 = 0;
        double sum18 = 0;
        double sum19 = 0;
        double sum20 = 0;
        double sum21 = 0;
        double sum22 = 0;
        double sum23 = 0;
        double sum24 = 0;
        double sum25 = 0;
        double sum26 = 0;
        double sum27 = 0;
        double sum28 = 0;
        double sum29 = 0;
        double sum30 = 0;
        double sum31 = 0;
        double sum32 = 0;
        double sum33 = 0;
        double sum34 = 0;
        double sum35 = 0;
        double sum36 = 0;
        double sum37 = 0;
        double sum38 = 0;
        double sum39 = 0;
        double sum40 = 0;
        double sum41 = 0;
        double sum42 = 0;
        double sum43;
        double sum44;
        double sum45;
        double sum46;
        double sum47;
        double sum48;
        double sum49;
        double sum50;
        double sum51 = 0;

        //Calculo dos extratores de caracteristicas de Haralick
        //Calculo para media das distribuicoes marginais
        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                sum21 = sum21 + (i * glcm0Normalizada[i][j]);
                sum22 = sum22 + (i * glcm45Normalizada[i][j]);
                sum23 = sum23 + (i * glcm90Normalizada[i][j]);
                sum24 = sum24 + (i * glcm135Normalizada[i][j]);
                sum25 = sum25 + (j * glcm0Normalizada[i][j]);
                sum26 = sum26 + (j * glcm45Normalizada[i][j]);
                sum27 = sum27 + (j * glcm90Normalizada[i][j]);
                sum28 = sum28 + (j * glcm135Normalizada[i][j]);
            }
        }

        for (int i = 0; i < tonsCinza; i++) {
            for (int j = 0; j < tonsCinza; j++) {
                if (glcm0Normalizada[i][j] != 0) {
                    sum1 = sum1 + (Math.pow(glcm0Normalizada[i][j], 2));
                    sum5 = sum5 + ((Math.pow((i - j), 2)) * glcm0Normalizada[i][j]);
                    sum9 = sum9 + (glcm0Normalizada[i][j] * (Math.log(glcm0Normalizada[i][j]) / Math.log(10)));
                    sum13 = sum13 + ((1 / (1 + (Math.pow((i - j), 2)))) * glcm0Normalizada[i][j]);
                    sum29 = sum29 + ((Math.pow(i - sum21, 2) * glcm0Normalizada[i][j]));
                    sum33 = sum33 + ((Math.pow(j - sum25, 2) * glcm0Normalizada[i][j]));
                    sum39 = sum39 + ((i - sum21) * (j - sum25) * glcm0Normalizada[i][j]);
                }
                if (glcm45Normalizada[i][j] != 0) {
                    sum2 = sum2 + (Math.pow(glcm45Normalizada[i][j], 2));
                    sum6 = sum6 + ((Math.pow((i - j), 2)) * glcm45Normalizada[i][j]);
                    sum10 = sum10 + (glcm45Normalizada[i][j] * (Math.log(glcm45Normalizada[i][j]) / Math.log(10)));
                    sum14 = sum14 + ((1 / (1 + (Math.pow((i - j), 2)))) * glcm45Normalizada[i][j]);
                    sum30 = sum30 + ((Math.pow(i - sum22, 2) * glcm45Normalizada[i][j]));
                    sum34 = sum34 + ((Math.pow(j - sum26, 2) * glcm45Normalizada[i][j]));
                    sum40 = sum40 + ((i - sum22) * (j - sum26) * glcm45Normalizada[i][j]);
                }
                if (glcm90Normalizada[i][j] != 0) {
                    sum3 = sum3 + (Math.pow(glcm90Normalizada[i][j], 2));
                    sum7 = sum7 + ((Math.pow((i - j), 2)) * glcm90Normalizada[i][j]);
                    sum11 = sum11 + (glcm90Normalizada[i][j] * (Math.log(glcm90Normalizada[i][j]) / Math.log(10)));
                    sum15 = sum15 + ((1 / (1 + (Math.pow((i - j), 2)))) * glcm90Normalizada[i][j]);
                    sum31 = sum31 + ((Math.pow(i - sum23, 2) * glcm90Normalizada[i][j]));
                    sum35 = sum35 + ((Math.pow(j - sum27, 2) * glcm90Normalizada[i][j]));
                    sum41 = sum41 + ((i - sum23) * (j - sum27) * glcm90Normalizada[i][j]);
                }
                if (glcm135Normalizada[i][j] != 0) {
                    sum4 = sum4 + (Math.pow(glcm135Normalizada[i][j], 2));
                    sum8 = sum8 + ((Math.pow((i - j), 2)) * glcm135Normalizada[i][j]);
                    sum12 = sum12 + (glcm135Normalizada[i][j] * (Math.log(glcm135Normalizada[i][j]) / Math.log(10)));
                    sum16 = sum16 + ((1 / (1 + (Math.pow((i - j), 2)))) * glcm135Normalizada[i][j]);
                    sum32 = sum32 + ((Math.pow(i - sum24, 2) * glcm135Normalizada[i][j]));
                    sum36 = sum36 + ((Math.pow(j - sum28, 2) * glcm135Normalizada[i][j]));
                    sum42 = sum42 + ((i - sum24) * (j - sum28) * glcm135Normalizada[i][j]);
                }
            }
        }

        //Desvio padrao (raiz quadrada da variancia ou heterogeneidade)
        sum43 = Math.sqrt(sum29);
        sum44 = Math.sqrt(sum33);
        sum45 = Math.sqrt(sum30);
        sum46 = Math.sqrt(sum34);
        sum47 = Math.sqrt(sum31);
        sum48 = Math.sqrt(sum35);
        sum49 = Math.sqrt(sum32);
        sum50 = Math.sqrt(sum36);

        vetorAsm[0] = sum1;
        vetorAsm[1] = sum2;
        vetorAsm[2] = sum3;
        vetorAsm[3] = sum4;
        vetorContraste[0] = sum5;
        vetorContraste[1] = sum6;
        vetorContraste[2] = sum7;
        vetorContraste[3] = sum8;
        vetorEntropia[0] = (-1) * sum9;
        vetorEntropia[1] = (-1) * sum10;
        vetorEntropia[2] = (-1) * sum11;
        vetorEntropia[3] = (-1) * sum12;
        vetorHomogeneidade[0] = sum13;
        vetorHomogeneidade[1] = sum14;
        vetorHomogeneidade[2] = sum15;
        vetorHomogeneidade[3] = sum16;
        vetorHeterogeneidadeI[0] = sum29;
        vetorHeterogeneidadeJ[0] = sum33;
        vetorHeterogeneidadeI[1] = sum30;
        vetorHeterogeneidadeJ[1] = sum34;
        vetorHeterogeneidadeI[2] = sum31;
        vetorHeterogeneidadeJ[2] = sum35;
        vetorHeterogeneidadeI[3] = sum32;
        vetorHeterogeneidadeJ[3] = sum36;
        vetorCorrelacao[0] = sum39 * (1 / (sum43 * sum44));
        vetorCorrelacao[1] = sum40 * (1 / (sum45 * sum46));
        vetorCorrelacao[2] = sum41 * (1 / (sum47 * sum48));
        vetorCorrelacao[3] = sum42 * (1 / (sum49 * sum50));

        //Realizando a media dos valores 
        for (int i = 0; i < 4; i++) {
            sum17 = vetorAsm[i] + sum17;
            sum18 = vetorContraste[i] + sum18;
            sum19 = vetorEntropia[i] + sum19;
            sum20 = vetorHomogeneidade[i] + sum20;
            sum37 = vetorHeterogeneidadeI[i] + sum37;
            sum38 = vetorHeterogeneidadeJ[i] + sum38;
            sum51 = vetorCorrelacao[i] + sum51;
        }
        featureVector[0] = sum17 / 4;
        featureVector[1] = sum18 / 4;
        featureVector[2] = sum19 / 4;
        featureVector[3] = sum20 / 4;
        featureVector[4] = sum37 / 4;
        featureVector[5] = sum38 / 4;
        featureVector[6] = sum51 / 4;

        return featureVector;
    }

}
