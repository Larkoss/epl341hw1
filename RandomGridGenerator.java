import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class RandomGridGenerator {
    private static int rows = 99;
    private static int cols = 99;

    public static void generateGrid() {
        for (int k = 0; k < 100; k++) {
            int[][] grid = new int[rows][cols];
            Random rand = new Random();
            // generate random 0 or 1 for each element in the grid
            for (int i = 10; i < rows; i++) {
                for (int j = 10; j < cols; j++) {
                    // 20% of spaces will be bombs
                    if (rand.nextInt(10) < 2) {
                        if (i != rows - 1 && j != cols - 1)
                            grid[i][j] = 1;
                    } else
                        grid[i][j] = 0;
                }
            }

            // write the grid to a file

            String filename = (rows + "x" + cols + "_" + k + ".txt");
            try {
                FileWriter writer = new FileWriter(filename);
                PrintWriter printWriter = new PrintWriter(writer);
                printWriter.println(rows + " " + cols);
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        printWriter.print(grid[i][j]);
                    }
                    printWriter.write("\n");
                }
                printWriter.println(0 + " " + 0);
                printWriter.print((rows - 1) + " " + (cols - 1));
                printWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred while writing to the file");
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        generateGrid();
    }
}
