import java.util.Random;

class Selection {
    private int[][][] dataSet;
    private static long endTime = 0; 

    public Selection(int[][][] dataSet) {
        this.dataSet = dataSet;
    }

    public static void startSort() {
        System.out.println("Selection called");
        // warms up the sorting method, I saw an enourmous drop in variance after doing
        // this
        int[] warmUpData = new int[] { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        for (int k = 0; k < 10; k++) {
            selectionSort(warmUpData);
        }
        warmup();
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
