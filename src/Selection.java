import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

class Selection {
    private static int[][][] dataSet;
    private static long endTime = 0;

    public Selection(int[][][] dataSet) {
        this.dataSet = dataSet;
        // warms up the sorting method, I saw an enourmous drop in variance after doing
        // this
        System.out.println("Selection Warmup Started");
        // int[] warmUpData = new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        // for (int k = 0; k < 10000; k++) {
        //     selectionSort(warmUpData);
        // }
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
