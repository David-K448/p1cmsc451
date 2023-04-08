import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class P1 {

    public static void main(String[] args) throws Exception {
        // int[] sizeArray = new int[] { 200, 400, 600, 800, 1000, 1200, 1400, 1600, 1800, 2000, 2200, 2400 }; <-- base array for sizes fo data structures to be built 

        int[][][] dataSet = new int[12][40][]; // initialize 3d data structure 

        fillDataStructure(dataSet); // create data structure for use in both sorting algos, rand values

        Merge merge = new Merge(dataSet); // call merge class, also calls warmup method to 'warmup' the merge sort class
        merge.startSort();

        Selection selection = new Selection(dataSet); // call selection class, also calls warmup method to 'warmup' the selection sort class
        selection.startSort();
      
    }

    /*
     * generates random values from 1-10,000, fills the arrays stored in the 3rd dimension of our 3d data structure 
     */
    public static void fillDataStructure(int[][][] dataSet) {
        Random rand = new Random();

        for (int i = 0; i < 12; i++) {
            int size = (i + 1) * 200;
            for (int j = 0; j < 40; j++) {
                dataSet[i][j] = new int[size];

                // Initialize values in the current level
                for (int k = 0; k < size; k++) {
                    dataSet[i][j][k] = rand.nextInt(5000) + 1;
                }
            }
        }
    }
}