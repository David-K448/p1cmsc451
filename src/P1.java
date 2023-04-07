import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class P1 {

    public static void main(String[] args) throws Exception {
        int[] sizeArray = new int[] { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200 };

        int[][][] dataSet = new int[12][40][]; // initialize data set

        fillDataStructure(dataSet); // create data structure for use in both sorting algos

        Merge merge = new Merge(dataSet); // call merge class
        merge.startSort();

        Selection selection = new Selection(dataSet); // call selection class
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