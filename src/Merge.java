import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

class Merge {

    private static int[][][] dataSet;
    private static long endTime = 0;

    public Merge(int[][][] dataSet) {
        this.dataSet = dataSet;
    }

    public static void startSort() {
        System.out.println("merge called");

        long[][] critCount = new long[12][40]; // count of each of the 40 runs
        long[][] elapsedTime = new long[12][40]; // elapsed time of each of the 40 runs
        int[] countArrAvg = new int[12]; // avg count for each of 12 groups
        long[] timeAvg = new long[12];
        

        // calling merge sort
        for (int i = 0; i < 12; i++) {
            int countPass = 0;
            int countTime = 0;
            for (int j = 0; j < 40; j++) {
                int[] arr = dataSet[i][j];

                long startTime = System.nanoTime(); // start time
                int tempCount = mergeSort(arr); // call sort
                

                long tempTimeCalc = endSort(startTime, endTime); // get temp elapsed time 

                countPass += tempCount;
                critCount[i][j] = tempCount;

                countTime += tempTimeCalc;
                elapsedTime[i][j] = tempTimeCalc;
            }
            countArrAvg[i] = countPass / 40;
            timeAvg[i] = countTime / 40;
        }
        // System.out.println(dataSet[0][0].length);
        // System.out.println(Arrays.toString(dataSet[1][0]));
        // System.out.println("avg count"+Arrays.toString(countArrAvg)+"\n");
        // System.out.println("avg time"+Arrays.toString(timeAvg)+"\n");
        // System.out.println("count" + Arrays.toString(critCount[0]) + "\n");
        // System.out.println("time"+Arrays.toString(elapsedTime[0])+"\n");
        printResults(critCount, elapsedTime, dataSet);
    }

    static void printResults(long[][] critCount, long[][] elapsedTime, int[][][] dataSet) {
        try {
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
                System.out.println(str.toString());
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
        Random random = new Random();
        int[][] testData = new int[10][10000];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10000; j++) {
                testData[i][j] = random.nextInt(10000);
            }
        }
    
        long startTime = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 10; j++) {
                Merge.mergeSort(testData[j]);
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Warmup complete in " + (endTime - startTime) / 1000000 + " ms");
    }
    
    
    
}
