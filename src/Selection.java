class Selection {
    private int[][][] dataSet;

    public Selection(int[][][] dataSet) {
        this.dataSet = dataSet;
        prin();
    }

    private void prin() {
        System.out.println("select called");
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
}
