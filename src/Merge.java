/*
 * David Killian
 * CMSC 451 P1
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Merge {

    private static int[][][] dataSet;
    private static long endTime = 0;
    private static Random rand;

    public Merge(int[][][] dataSet, Random rand) {
        this.dataSet = dataSet;
        this.rand = rand;
        System.out.println("merge warmup started");
        warmup();
    }

    public static void startSort() {
        System.out.println("merge called");
        long[][] critCount = new long[12][40]; // count of each of the 40 runs
        long[][] elapsedTime = new long[12][40]; // elapsed time of each of the 40runs
        int[] countArrAvg = new int[12]; // avg count for each of 12 groups
        long[] timeAvg = new long[12];

        // calling merge sort / performance run
        for (int i = 0; i < 12; i++) {
            int countPass = 0;
            int countTime = 0;
            for (int j = 0; j < 40; j++) {
                int[] arr = dataSet[i][j];

                long startTime = System.nanoTime(); // start time
                int tempCount = mergeSort(arr); // call sort // performance call
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
        System.out.println("Merge complete.\n");
    }

    
    

    private static long endSort(long startTime, long endTime) {
        return (endTime - startTime);
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
            FileWriter fw = new FileWriter("p1output/mergeResults.txt");
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

    /*
     * Merge sort algorithm implementation, returns count of passes
     */
    public static int mergeSort(int[] arr) {
        int count = 0;
        if (arr.length > 1) {
            int mid = arr.length / 2;
            int[] left = Arrays.copyOfRange(arr, 0, mid);
            int[] right = Arrays.copyOfRange(arr, mid, arr.length);

            count += mergeSort(left);
            count += mergeSort(right);

            int i = 0;
            int j = 0;
            int k = 0;

            while (i < left.length && j < right.length) {
                if (left[i] <= right[j]) {
                    arr[k] = left[i];
                    i++;
                } else {
                    arr[k] = right[j];
                    j++;
                    count += (mid - i);
                }
                k++;
            }

            while (i < left.length) {
                arr[k] = left[i];
                i++;
                k++;
            }

            while (j < right.length) {
                arr[k] = right[j];
                j++;
                k++;
            }
        }
        endTime = System.nanoTime();
        return count;
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
                    mergeSort(testData[j][k]);
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Warmup complete in " + (endTime - startTime) / 1000000 + " ms");
    }

 

}
