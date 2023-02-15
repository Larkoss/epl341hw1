import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class AStar {
    // DIRECTIONS represents the possible directions for movement in the grid.
    // Each direction is represented as a {row, col} pair.
    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    // BOMB and EMPTY represent the characters used in the input file to denote
    // BOMBs and empty cells.
    private static final char BOMB = '1';

    // Node represents a single cell in the grid.
    // The fields g and h represent the cost of reaching the node from the start
    // node and the estimated cost to the end node, respectively.
    // The field parent represents the parent node of the current node in the path.
    private static class Node implements Comparable<Node> {
        int row, col, g, h;
        Node parent;

        Node(int row, int col, int g, int h, Node parent) {
            this.row = row;
            this.col = col;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        // getF() returns the sum of g and h, which represents the estimated total cost
        // of the path through this node.
        int getF() {
            return g + h;
        }

        // compareTo() compares nodes based on their estimated total cost.
        // This is used by the PriorityQueue to determine which node to visit next.
        public int compareTo(Node other) {
            return Integer.compare(getF(), other.getF());
        }
    }

    // The Manhattan distance is defined as the sum of the absolute differences of
    // their row and column coordinates.
    private static int manhattanDistance(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) + Math.abs(col1 - col2);
    }

    // the Euclidean distance between two points in Euclidean space is the length of
    // a line segment between the two points.
    private static double euclideanDistance(int row1, int col1, int row2, int col2) {
        return Math.sqrt(Math.pow(row2 - row1, 2) + Math.pow(col2 - col1, 2));
    }

    // isValid() checks whether a given cell is valid.
    // A cell is valid if it is inside the grid and is not a BOMB (-1).
    private static boolean isValid(int[][] grid, int row, int col) {
        int rows = grid.length, cols = grid[0].length;
        return row >= 0 && row < rows && col >= 0 && col < cols && grid[row][col] != -1;
    }

    // getNeighbors() returns a list of valid neighbor nodes for a given node.
    // A neighbor is considered valid if it is inside the grid and is not a BOMB.
    private static ArrayList<Node> getNeighbors(int[][] grid, Node node) {
        ArrayList<Node> neighbors = new ArrayList<>();
        for (int[] direction : DIRECTIONS) {
            int newRow = node.row + direction[0];
            int newCol = node.col + direction[1];
            if (isValid(grid, newRow, newCol)) {
                int newG = node.g + 1;
                int newH = manhattanDistance(newRow, newCol, grid.length - 1, grid[0].length - 1);
                neighbors.add(new Node(newRow, newCol, newG, newH, node));
            }
        }
        return neighbors;
    }

    // This method takes in a Node object and reconstructs the path by following the
    // parent nodes
    // until the initial node is reached. It returns an ArrayList of Node objects
    // representing the path.
    private static ArrayList<Node> reconstructPath(Node node) {
        ArrayList<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node); // Add each node to the beginning of the path ArrayList
            node = node.parent; // Move to the parent node
        }
        return path;
    }

    public static void main(String[] args) {
        try {
            // Open and read from the input file
            Scanner scanner = new Scanner(new File("input.txt"));
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            int[][] grid = new int[rows][cols];
            scanner.nextLine();
            for (int i = 0; i < rows; i++) {
                String line = scanner.nextLine();
                // Mark BOMBs as -1 and non-BOMBs as 0
                for (int j = 0; j < cols; j++) {
                    grid[i][j] = line.charAt(j) == BOMB ? -1 : 0;
                }
            }
            int startRow = scanner.nextInt();
            int startCol = scanner.nextInt();
            int endRow = scanner.nextInt();
            int endCol = scanner.nextInt();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Create a new start node with the given start and end coordinates
            PriorityQueue<Node> openSet2 = new PriorityQueue<>();
            // Add the start node to the open set
            Node startNode2 = new Node(startRow, startCol, 0,
                    (int) euclideanDistance(startRow, startCol, endRow, endCol),
                    null);
            openSet2.add(startNode2);

            while (!openSet2.isEmpty()) {
                // Remove the node with the lowest f score from the open set
                Node current = openSet2.remove();
                // Check if the end node has been reached
                if (current.row == endRow && current.col == endCol) {
                    ArrayList<Node> path = reconstructPath(current);
                    System.out.println("Path using Euclidean Distance");
                    System.out.print("Path: ");
                    for (Node node : path) {
                        System.out.print("(" + node.row + ", " + node.col + ") ");
                    }
                    System.out.println();
                    break;
                }
                // Skip this node if it is a BOMB
                if (grid[current.row][current.col] == -1) {
                    continue;
                }
                // Mark this node as visited
                grid[current.row][current.col] = -2;
                // Check each neighbor of the current node
                for (Node neighbor : getNeighbors(grid, current)) {
                    // Skip this neighbor if it is a BOMB or has been visited
                    if (grid[neighbor.row][neighbor.col] == -1 || grid[neighbor.row][neighbor.col] == -2) {
                        continue;
                    }
                    // Update the g score of this neighbor if it is already in the open set
                    if (openSet2.contains(neighbor)) {
                        Node openSetNeighbor = null;
                        for (Node node : openSet2) {
                            if (node.equals(neighbor)) {
                                openSetNeighbor = node;
                                break;
                            }
                        }
                        if (neighbor.g < openSetNeighbor.g) {
                            openSet2.remove(openSetNeighbor);
                            openSet2.add(neighbor);
                        }
                    } else {
                        // Add this neighbor to the open set if it is not already in it
                        openSet2.add(neighbor);
                    }
                }
            }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            PriorityQueue<Node> openSet1 = new PriorityQueue<>();
            Node startNode1 = new Node(startRow, startCol, 0, manhattanDistance(startRow, startCol, endRow, endCol),
                    null);
            openSet1.add(startNode1);
            while (!openSet1.isEmpty()) {
                // Remove the node with the lowest f score from the open set
                Node current = openSet1.remove();
                // Check if the end node has been reached
                if (current.row == endRow && current.col == endCol) {
                    ArrayList<Node> path = reconstructPath(current);
                    System.out.println("Path using Manhattan Distance");
                    System.out.print("Path: ");
                    for (Node node : path) {
                        System.out.print("(" + node.row + ", " + node.col + ") ");
                    }
                    System.out.println();
                    break;
                }
                // Skip this node if it is a BOMB
                if (grid[current.row][current.col] == -1) {
                    continue;
                }
                // Mark this node as visited
                grid[current.row][current.col] = -3;
                // Check each neighbor of the current node
                for (Node neighbor : getNeighbors(grid, current)) {
                    // Skip this neighbor if it is a BOMB or has been visited
                    if (grid[neighbor.row][neighbor.col] == -1 || grid[neighbor.row][neighbor.col] == -3) {
                        continue;
                    }
                    // Update the g score of this neighbor if it is already in the open set
                    if (openSet1.contains(neighbor)) {
                        Node openSetNeighbor = null;
                        for (Node node : openSet1) {
                            if (node.equals(neighbor)) {
                                openSetNeighbor = node;
                                break;
                            }
                        }
                        if (neighbor.g < openSetNeighbor.g) {
                            openSet1.remove(openSetNeighbor);
                            openSet1.add(neighbor);
                        }
                    } else {
                        // Add this neighbor to the open set if it is not already in it
                        openSet1.add(neighbor);
                    }
                }
            }

            // System.out.println("No path found.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}