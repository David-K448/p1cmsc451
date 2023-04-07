import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class P2 {
    public static void main(String[] args) {
        // Create ArrayList to hold sizes
        ArrayList<Integer> sizes = new ArrayList<>();

        // Create 2D arrays to hold counts and times
        int[][] counts = new int[12][40];
        int[][] times = new int[12][40];

        try {
            // Open file for reading
            File file = new File("p1output/mergeResults.txt");
            Scanner scanner = new Scanner(file);
            // Loop through each line of the file
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                // Get next line and split into tokens
                String line = scanner.nextLine();
                String[] tokens = line.split(" ");
                // Add first token to sizes ArrayList
                int size = Integer.parseInt(tokens[0]);
                sizes.add(size);
                // Read even integers into counts array
                for (int i = 1; i < tokens.length; i += 2) {
                    int index = (i - 1) / 2;
                    counts[lineNum][index] = Integer.parseInt(tokens[i]);
                }
                // Read odd integers into times array
                for (int i = 2; i < tokens.length; i += 2) {
                    int index = (i - 2) / 2;
                    times[lineNum][index] = Integer.parseInt(tokens[i]);
                }
                // Increment line number
                lineNum++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
         * get avg counts
         */
        int[] avgCounts = new int[12];
        int[] avgTimes = new int[12];

        for (int i = 0; i < 12; i++) {
            int runnCount = 0;
            int runnTime = 0;
            for (int j = 0; j < 40; j++) {
                runnCount += counts[i][j];
                runnTime += times[i][j];
            }
            int tempAvg = runnCount / 40;
            int tempTimeAvg = runnTime / 40;
            avgCounts[i] = tempAvg;
            avgTimes[i] = tempTimeAvg;
        }

        // calc count diff coef
        double[] countCoef = calcDiffCoef(avgCounts, counts);

        // calc time diff coef
        double[] timeCoef = calcDiffCoef(avgTimes, times);

        displayTable(sizes, avgCounts, countCoef, avgTimes, timeCoef);

        try {
            writeCsv(sizes, avgCounts, avgTimes, countCoef, timeCoef, null);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void displayTable(ArrayList<Integer> sizes, int[] avgCounts, double[] countCoef, int[] avgTimes,
            double[] timeCoef) {
        // Create a 2D array to represent the grid of cells
        String[][] cells = new String[13][5];

        cells[0][0] = "Size";
        cells[0][1] = "Avg Counts";
        cells[0][2] = "Count Coef";
        cells[0][3] = "Avg Times";
        cells[0][4] = "Time Coef";

        for (int i = 1; i < 13; i++) {
            cells[i][0] = String.valueOf(sizes.get(i - 1));
            cells[i][1] = String.valueOf(avgCounts[i - 1]);
            cells[i][2] = String.valueOf(countCoef[i - 1]);
            cells[i][3] = String.valueOf(avgTimes[i - 1]);
            cells[i][4] = String.valueOf(timeCoef[i - 1]);
        }

        // Create a JTable to display the grid of cells
        JTable table = new JTable(cells, new String[] { "Size", "Avg Counts", "Count Coef", "Avg Times", "Time Coef" });

        // Create a JPanel to hold the JTable and the button
        JPanel panel = new JPanel();
        panel.add(table);
        JButton button = new JButton("Button");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeCsv(sizes, avgCounts, avgTimes, countCoef, timeCoef, null);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });
        panel.add(button);
        // Create the JOptionPane and display it
        JOptionPane.showMessageDialog(null, panel, "Merge Results", JOptionPane.PLAIN_MESSAGE);

       

    }

    // takes an avg array & counts, calculates diff coef
    private static double[] calcDiffCoef(int[] avgCounts, int[][] counts) {
        double[] diffCoef = new double[12];
        // max - min / avg

        int[] maxValues = new int[12];
        int[] minValues = new int[12];
        for (int i = 0; i < 12; i++) {
            int max = counts[i][0];
            int min = counts[i][0];
            for (int j = 1; j < 40; j++) {
                if (counts[i][j] > max) {
                    max = counts[i][j];
                }
                if (counts[i][j] < min) {
                    min = counts[i][j];
                }
            }
            maxValues[i] = max;
            minValues[i] = min;
        }

        // calculate coef
        for (int i = 0; i < 12; i++) {
            int tempMin = minValues[i];
            int tempMax = maxValues[i];
            int tempAvg = avgCounts[i];

            double tempCoef = (double) (tempMax - tempMin) / tempAvg * 100;
            diffCoef[i] = tempCoef;
        }
        return diffCoef;
    }

    public static void writeCsv(ArrayList<Integer> sizes, int[] avgCounts, int[] avgTimes, double[] countCoef,
            double[] timeCoef, String filename) throws IOException {
        FileWriter csvWriter = new FileWriter("p2output/mergeResultsFinal.csv");
        csvWriter.append("Size");
        csvWriter.append(",");
        csvWriter.append("Avg Count");
        csvWriter.append(",");
        csvWriter.append("Count Coef");
        csvWriter.append(",");
        csvWriter.append("Avg Time");
        csvWriter.append(",");
        csvWriter.append("Time Coef");
        csvWriter.append("\n");

        for (int i = 0; i < sizes.size(); i++) {
            csvWriter.append(sizes.get(i).toString());
            csvWriter.append(",");
            csvWriter.append(Integer.toString(avgCounts[i]));
            csvWriter.append(",");
            csvWriter.append(Double.toString(countCoef[i]));
            csvWriter.append(",");
            csvWriter.append(Integer.toString(avgTimes[i]));
            csvWriter.append(",");
            csvWriter.append(Double.toString(timeCoef[i]));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }
}
