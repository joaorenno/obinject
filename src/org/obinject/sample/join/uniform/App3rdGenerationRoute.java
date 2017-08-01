package org.obinject.sample.join.uniform;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App3rdGenerationRoute {
    
    
    // Configuração OK - 1,5,10,10,1,10
    
    public static final int AMOUNT_SERIE_ROUTE = 1; // qtd de conjuntos (variações) // OK
    public static final int AMOUNT_SERIE_POINT = 10; // qtd de arquivos por conjunto (variações) // OK

    public static final int AMOUNT_ROUTE_INITIAL = 950; // qtd inicial de rotas nos conjuntos // OK
    public static final int AMOUNT_POINT_INITIAL = 500; // qtd inicial de pontos em cada conjunto // OK

    public static final int INC_ROUTE_INITIAL = 1; // qtd de incremento de rotas nos conjuntos (se amount_serie_route > 2?) // OK
    public static final int INC_POINT_INITIAL = 1000; // qtd de incremento de pontos nos conjuntos // OK
    
    public static final int TOTAL_ROUTE = AMOUNT_ROUTE_INITIAL + (AMOUNT_SERIE_ROUTE * INC_ROUTE_INITIAL) - INC_ROUTE_INITIAL;
    public static final int TOTAL_POINT = AMOUNT_POINT_INITIAL + (AMOUNT_SERIE_POINT * INC_POINT_INITIAL) - INC_POINT_INITIAL;
    public static final Random RANDOM_JOIN = new Random();

    public static void main(String[] args) {
        BufferedWriter txtFile;
        //generation of the file route.txt
        try {
            //amount of the serie
            for (int amountRoute = AMOUNT_ROUTE_INITIAL; amountRoute <= TOTAL_ROUTE; amountRoute += INC_ROUTE_INITIAL) {
                for (int amountPoint = AMOUNT_POINT_INITIAL; amountPoint <= TOTAL_POINT; amountPoint += INC_POINT_INITIAL) {
                    txtFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                            "resources/route" + amountRoute + "point" + amountPoint + ".txt")));
                    //amount of the route
                    //SNAKE GAME TECNIQUE OF THE ROUTE GENERATION 
                    for (int i = 0; i < amountRoute; i++) {
                        int pointX;
                        int pointY;
                        int incX = 0;
                        int incY = 0;
                        int opt = RANDOM_JOIN.nextInt(4); // Mudei aqui..o intervalo estava 3
                        switch (opt) {
                            case 0:
                                pointX = RANDOM_JOIN.nextInt(App2ndGenerationShuffle.SQUARE_ROOT_BASE - 2) + 1;
                                pointY = RANDOM_JOIN.nextInt(App2ndGenerationShuffle.SQUARE_ROOT_BASE - amountPoint - 2) + 1;
                                incY = 1;
                                break;
                            case 1:
                                pointX = RANDOM_JOIN.nextInt(App2ndGenerationShuffle.SQUARE_ROOT_BASE - amountPoint - 2) + 1;
                                pointY = RANDOM_JOIN.nextInt(App2ndGenerationShuffle.SQUARE_ROOT_BASE - 2) + 1;
                                incX = 1;
                                break;
                            case 2:
                                pointX = RANDOM_JOIN.nextInt(App2ndGenerationShuffle.SQUARE_ROOT_BASE - 2) + 1;
                                pointY = RANDOM_JOIN.nextInt(App2ndGenerationShuffle.SQUARE_ROOT_BASE - amountPoint - 2) + amountPoint + 1;
                                incY = -1;
                                break;
                            default: // Mudei aqui tb, não tinha nada em 3
                                pointX = RANDOM_JOIN.nextInt(App2ndGenerationShuffle.SQUARE_ROOT_BASE - amountPoint - 2) + amountPoint + 1;
                                pointY = RANDOM_JOIN.nextInt(App2ndGenerationShuffle.SQUARE_ROOT_BASE - 2) + 1;
                                incX = -1;
                                break;
                        }
                        for (int j = 0; j < amountPoint; j++) {
                            if (((pointX + incX) >= 1) && ((pointX + incX) < App2ndGenerationShuffle.SQUARE_ROOT_BASE - 1)
                                    && ((pointY + incY) >= 1) && ((pointY + incY) < App2ndGenerationShuffle.SQUARE_ROOT_BASE - 1)) {
                                txtFile.write(pointX + "\t" + pointY + "\t");
                                pointX += incX;
                                pointY += incY;
                            } else {
                                System.out.println("erro generation route");
                                break;
                            }
                        }
                        txtFile.newLine();
                    }
                    txtFile.close();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(App3rdGenerationRoute.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App3rdGenerationRoute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
