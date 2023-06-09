/*
 * David Killian
 * CMSC 451 P1
 */

 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.util.Arrays;
 import java.util.Collections;
 import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
 class Selection {
     private static int[][][] dataSet;
     private static long endTime = 0;
     private static Random rand;
 
     public Selection(int[][][] dataSet, Random rand) {
         this.dataSet = dataSet;
         this.rand = rand;
         // warms up the sorting method, I saw an enourmous drop in variance after doing
         // this
         System.out.println("Selection Warmup Started");
         warmup();
     }
 
     public static void startSort() {
         System.out.println("Selection Started");
         long[][] critCount = new long[12][40]; // count of each of the 40 runs
         long[][] elapsedTime = new long[12][40]; // elapsed time of each of the 40 runs
         int[] countArrAvg = new int[12]; // avg count for each of 12 groups
         long[] timeAvg = new long[12];
 
         for (int i = 0; i < 12; i++) {
             int countPass = 0;
             int countTime = 0;
             for (int j = 0; j < 40; j++) {
                 int[] arr = dataSet[i][j];
 
                 long startTime = System.nanoTime(); // start time
                 int tempCount = selectionSort(arr); // call sort // performance call
                 long tempTimeCalc = endSort(startTime, endTime); // get temp elapsed time
 
                 countPass += tempCount;
                 critCount[i][j] = tempCount;
 
                 countTime += tempTimeCalc;
                 elapsedTime[i][j] = tempTimeCalc;
             }
             countArrAvg[i] = countPass / 40;
             timeAvg[i] = countTime / 40;
         }
 
         printResults(critCount, elapsedTime, dataSet);
         System.out.println("Selection complete.\n");
     }
 
     static void printResults(long[][] critCount, long[][] elapsedTime, int[][][] dataSet) {
         try {
             File dir = new File("p1output");
             if (!dir.exists()) {
                 boolean created = dir.mkdir();
                 if (!created) {
                     throw new IOException("Failed to create directory 'p1output'");
                 }
             }
             FileWriter fw = new FileWriter("p1output/selectionResults.txt");
             BufferedWriter bw = new BufferedWriter(fw);
 
             for (int i = 0; i < 12; i++) {
                 StringBuilder str = new StringBuilder();
                 Integer x = dataSet[i][0].length;
                 str.append(x + " ");
                 for (int j = 0; j < 40; j++) {
                     Long c = critCount[i][j];
                     str.append(c + " ");
                     long t = elapsedTime[i][j];
                     str.append(t + " ");
                 }
                 bw.write(str.toString() + "\n");
                 // System.out.println(str.toString());
             }
 
             bw.close();
             fw.close();
         } catch (IOException e) {
             System.out.println("An error occurred while writing to file.");
             e.printStackTrace();
         }
     }
 
     private static long endSort(long startTime, long endTime) {
         return (endTime - startTime);
     }
 
     /*
      * implementation of selection sort, returns int count passes
      */
 
      
     public static int selectionSort(int[] array) {
         int passes = 0;
         for (int i = 0; i < array.length - 1; i++) {
             int minIndex = i;
             for (int j = i + 1; j < array.length; j++) {
                 if (array[j] < array[minIndex]) {
                     minIndex = j;
                 }
                 passes++;
             }
             int temp = array[minIndex];
             array[minIndex] = array[i];
             array[i] = temp;
         }
         endTime = System.nanoTime();
         return passes;
     }
 
     public static void warmup() {
         // Initialize test data
         int[][][] testData = new int[12][40][];
         for (int i = 0; i < testData.length; i++) {
             int size = (i + 1) * 200;
             for (int j = 0; j < testData[i].length; j++) {
                 testData[i][j] = new int[size];
                 // Initialize values in the current level
                 for (int k = 0; k < testData[i][j].length; k++) {
                     testData[i][j][k] = rand.nextInt(1500000) + 1;
                 }
                 // Shuffle the array randomly
                 for (int k = size - 1; k > 0; k--) {
                     int index = rand.nextInt(k + 1);
                     int temp = testData[i][j][index];
                     testData[i][j][index] = testData[i][j][k];
                     testData[i][j][k] = temp;
                 }
             }
         }
     
         // Sort the data to warm up JVM
         long startTime = System.nanoTime();
         for (int i = 0; i < 100; i++) {
             for (int j = 0; j < 10; j++) {
                 for (int k = 0; k < testData[j].length; k++) {
                     selectionSort(testData[j][k]);
                 }
             }
         }
         long endTime = System.nanoTime();
         System.out.println("Warmup complete in " + (endTime - startTime) / 1000000 + " ms");
     }
 }
 