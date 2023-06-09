/*
 * David Killian
 * CMSC 451 P1
 */

 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import java.util.Random;
 
 public class P1 {
    private static Random rand = new Random(123456789);
 
     public static void main(String[] args) throws Exception {
         // int[] sizeArray = new int[] { 200, 400, 600, 800, 1000, 1200, 1400, 1600,
         // 1800, 2000, 2200, 2400 }; <-- base array for sizes fo data structures to be
         // built
 
         int[][][] dataSet = new int[12][40][]; // initialize 3d data structure
         int[][][] dataSetTestSelect = new int[12][40][];
         int[][][] dataSetTestMerge = new int[12][40][];
 
         fillDataStructure(dataSet); // create data structure for use in both sorting algos, rand values
 
         for (int i = 0; i < 12; i++) {
             for (int j = 0; j < 40; j++) {
                 dataSetTestSelect[i][j] = dataSet[i][j].clone();
                 dataSetTestMerge[i][j] = dataSet[i][j].clone();
             }
         }
 
         Merge merge = new Merge(dataSet, rand); // call merge class, also calls warmup method to 'warmup' the merge sort class
         merge.startSort();
 
         Selection selection = new Selection(dataSet, rand); // call selection class, also calls warmup method to 'warmup' the
                                                 
         selection.startSort();
 
     }
 
     /*
      * generates random values from 1-10,000, fills the arrays stored in the 3rd
      * dimension of our 3d data structure
      */
     // using the Fisher-Yates shuffle algorithm to shuffle array randomly after
     // initialization
     public static void fillDataStructure(int[][][] dataSet) {
 
         for (int i = 0; i < 12; i++) {
             int size = (i + 1) * 200;
             for (int j = 0; j < 40; j++) {
                 dataSet[i][j] = new int[size];
 
                 // Initialize values in the current level
                 for (int k = 0; k < size; k++) {
                     dataSet[i][j][k] = rand.nextInt(1500000) + 1;
                 }
 
                 // Shuffle the array randomly
                 for (int k = size - 1; k > 0; k--) {
                     int index = rand.nextInt(k + 1);
                     int temp = dataSet[i][j][index];
                     dataSet[i][j][index] = dataSet[i][j][k];
                     dataSet[i][j][k] = temp;
                 }
             }
         }
     }
 }