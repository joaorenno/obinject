package org.obinject.sample.join.uniform;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App2ndGenerationShuffle {

    public static final int AMOUNT_BASE = 100000000;
    public static final int SQUARE_ROOT_BASE = (int) Math.sqrt(AMOUNT_BASE);
    public static final Random RANDOM_INSERT = new Random(100); 

    private static class Item implements Comparable<Item>{
        private final int x;
        private final int y;
        private final int priority;

        public Item(int x, int y, int priority) {
            this.x = x;
            this.y = y;
            this.priority = priority;
        }

         @Override
        public int compareTo(Item obj) {
            if (priority < obj.priority) {
                return -1;
            } else if (priority > obj.priority) {
                return 1;
            } else {
                return 0;
            }
        }
    }
    
    public static void main(String[] args) {
        BufferedWriter txtFile;
        try {
            PriorityQueue<Item> queue = new PriorityQueue<>(AMOUNT_BASE);
            for (int i = 0; i < SQUARE_ROOT_BASE; i++) {
                for (int j = 0; j < SQUARE_ROOT_BASE; j++) {
                    queue.add(new Item(i, j, RANDOM_INSERT.nextInt(AMOUNT_BASE)));
                }
            }
            //generation unordered of the file uniform.txt
            txtFile = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("resources/uniformRandom.txt")));
            while (!queue.isEmpty()) {
                Item i = queue.poll();                
                txtFile.write(i.x+"\t"+i.y);
                txtFile.newLine();
            }
            txtFile.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(App2ndGenerationShuffle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(App2ndGenerationShuffle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
