package cl.ucn.hpc.sudoku;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.*;
import java.util.concurrent.*;


/**
 * The Main Class of Sudoku Solver
 *
 * @author Sebastian Delgado Guerra
 */

@Slf4j
public class Main {


    // Matrix used for the sudoku board itself.
    // 0 equals to empty space in the sudoku board.
    static int[][] boardzcvx = {
            {3, 0, 0, 0, 0, 6, 0, 8, 0},
            {0, 0, 1, 9, 8, 2, 0, 4, 0},
            {0, 0, 0, 0, 0, 0, 9, 7, 0},
            {2, 0, 0, 1, 5, 0, 0, 0, 0},
            {5, 4, 0, 0, 0, 0, 0, 9, 7},
            {0, 0, 0, 0, 9, 8, 0, 0, 3},
            {0, 1, 2, 0, 0, 0, 0, 0, 0},
            {0, 5, 0, 8, 2, 4, 6, 0, 0},
            {0, 8, 0, 5, 0, 0, 0, 0, 2}
    };
    static int[][] board_easy_small_2 = {
            {0, 0, 3, 0, 2, 0, 4, 0, 0},
            {0, 0, 0, 4, 0, 9, 0, 0, 0},
            {0, 0, 8, 0, 0, 0, 0, 6, 0},
            {0, 0, 2, 0, 0, 3, 0, 7, 0},
            {7, 0, 0, 0, 0, 0, 0, 0, 9},
            {0, 4, 0, 1, 0, 0, 5, 0, 0},
            {0, 6, 0, 0, 0, 0, 2, 0, 0},
            {0, 0, 0, 3, 0, 7, 0, 0, 0},
            {0, 0, 9, 0, 5, 0, 1, 0, 0}
    };

    // 16x16 easy
    static int[][] boardbig_easy = {
            {0, 3, 12, 7, 10, 0, 8, 0, 9, 0, 14, 0, 0, 0, 0, 4},
            {0, 15, 0, 13, 16, 1, 0, 0, 0, 3, 4, 0, 6, 10, 5, 0},
            {14, 5, 0, 0, 0, 0, 0, 0, 10, 2, 1, 11, 16, 7, 0, 9},
            {4, 0, 10, 0, 0, 5, 9, 12, 16, 7, 13, 6, 3, 15, 0, 0},
            {3, 0, 1, 0, 11, 2, 0, 6, 0, 0, 16, 0, 0, 0, 0, 8},
            {2, 9, 0, 6, 0, 14, 5, 0, 7, 13, 8, 0, 0, 1, 15, 0},
            {10, 0, 16, 0, 8, 0, 0, 4, 0, 0, 2, 0, 9, 0, 14, 6},
            {0, 0, 15, 0, 9, 7, 16, 1, 0, 4, 0, 5, 0, 13, 10, 0},
            {0, 4, 8, 0, 5, 0, 14, 0, 2, 1, 3, 9, 0, 6, 0, 0},
            {15, 14, 0, 10, 0, 16, 0, 0, 8, 0, 0, 7, 0, 5, 0, 1},
            {0, 12, 3, 0, 0, 9, 6, 15, 0, 10, 5, 0, 7, 0, 11, 13},
            {7, 0, 0, 0, 0, 4, 0, 0, 15, 0, 11, 13, 0, 14, 0, 2},
            {0, 0, 14, 15, 4, 10, 1, 5, 3, 6, 7, 0, 0, 11, 0, 12},
            {5, 0, 13, 3, 6, 8, 2, 9, 0, 0, 0, 0, 0, 0, 4, 7},
            {0, 2, 11, 4, 0, 15, 3, 0, 0, 0, 10, 16, 1, 0, 6, 0},
            {6, 0, 0, 0, 0, 11, 0, 16, 0, 14, 0, 4, 15, 3, 2, 0}
    };

    // 16x16 medium
    static int[][] boardbig_medium = {
            {0, 0, 0, 10, 16, 0, 0, 0, 0, 0, 0, 13, 0, 12, 0, 11},
            {4, 16, 0, 13, 2, 15, 0, 14, 7, 0, 6, 5, 1, 0, 8, 9},
            {14, 0, 2, 6, 1, 0, 9, 13, 4, 15, 8, 0, 5, 0, 0, 0},
            {0, 0, 7, 9, 4, 0, 11, 0, 16, 0, 1, 10, 14, 0, 13, 0},
            {7, 10, 0, 8, 0, 11, 1, 15, 0, 6, 0, 0, 0, 5, 0, 2},
            {0, 0, 0, 15, 10, 0, 2, 0, 0, 13, 0, 14, 11, 9, 3, 1},
            {2, 0, 3, 16, 0, 6, 5, 12, 1, 0, 10, 9, 8, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 16, 11, 0, 0, 7, 0, 10},
            {13, 0, 16, 0, 0, 2, 7, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 0, 0, 7, 5, 4, 0, 10, 13, 8, 15, 0, 6, 11, 0, 14},
            {6, 15, 10, 11, 13, 0, 14, 0, 0, 5, 0, 7, 3, 0, 0, 0},
            {3, 0, 12, 0, 0, 0, 15, 0, 14, 1, 9, 0, 13, 0, 10, 7},
            {0, 7, 0, 3, 11, 14, 0, 6, 0, 4, 0, 1, 10, 13, 0, 0},
            {0, 0, 0, 1, 0, 3, 16, 2, 11, 10, 0, 8, 15, 14, 0, 4},
            {11, 13, 0, 2, 8, 10, 0, 1, 5, 0, 12, 15, 7, 0, 9, 6},
            {10, 0, 8, 0, 15, 0, 0, 0, 0, 0, 0, 16, 2, 0, 0, 0}
    };

    // CURRENTLY IN USE
    // 16x16 hard
    static int[][] board = {
            {0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 1, 0, 16, 0},
            {0, 3, 6, 0, 0, 0, 0, 9, 0, 0, 10, 11, 12, 0, 15, 0},
            {12, 14, 0, 10, 0, 0, 16, 2, 0, 0, 13, 0, 0, 4, 5, 11},
            {15, 0, 11, 8, 12, 14, 0, 0, 0, 0, 0, 5, 7, 0, 6, 0},
            {0, 0, 14, 0, 0, 11, 0, 0, 9, 3, 0, 12, 0, 0, 0, 0},
            {0, 4, 0, 11, 1, 0, 0, 16, 0, 5, 0, 0, 6, 0, 0, 0},
            {0, 16, 0, 0, 7, 0, 0, 14, 13, 11, 8, 4, 0, 10, 9, 15},
            {13, 0, 0, 15, 3, 4, 0, 8, 0, 0, 0, 0, 16, 0, 12, 0},
            {0, 5, 0, 16, 0, 0, 0, 0, 15, 0, 2, 9, 13, 0, 0, 7},
            {9, 2, 12, 0, 13, 1, 8, 15, 6, 0, 0, 3, 0, 0, 10, 0},
            {0, 0, 0, 1, 0, 0, 7, 0, 5, 0, 0, 13, 11, 0, 8, 0},
            {0, 0, 0, 0, 4, 0, 2, 3, 0, 0, 7, 0, 0, 6, 0, 0},
            {0, 8, 0, 6, 2, 0, 0, 0, 0, 0, 5, 16, 10, 9, 0, 12},
            {1, 13, 2, 0, 0, 12, 0, 0, 10, 9, 0, 0, 15, 0, 4, 5},
            {0, 15, 0, 3, 14, 9, 0, 0, 1, 0, 0, 0, 0, 13, 7, 0},
            {0, 12, 0, 9, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0}

    };

    static Deque<int[][]> stack = new ArrayDeque<int[][]>();

    static boolean solved = false;
    // Variables used for length of cycles inside the board
    public static int gridSize = board.length;
    public static int smallGridSize = (int) Math.sqrt(gridSize);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        // reduce the amount of candidates by elimination
        CheckElimination(board);

        // reduce the amount of candidates by lone ranger method
        CheckLoneRanger(board);

        stack.add(board);
        // get more iterations of the board
        BoardIteratorGen(stack.pop());


        log.debug("The max cores: {}.", Runtime.getRuntime().availableProcessors());
        final int cores = 13;
        System.out.println("USING " + cores + " CORES FOR SOLUTION ");
        final ExecutorService executor = Executors.newFixedThreadPool(cores);
        final StopWatch sw = StopWatch.createStarted();
        System.out.println("BOARD TO SOLVE: ");
        printBoard(board);
        System.out.println("=========================\n=========================");

        // for each iteration saved, try to solve it with threads.
        for (int i = 0; i < stack.size(); i++) {
            final int[][] testboard = stack.pop();
            executor.submit(() -> {
                if (SolveBoard(testboard)) {
                    System.out.println("BOARD SOLVED");
                    board = testboard;
                    executor.shutdownNow();
                }
            });
        }

        int maxTime = 5;
        if (executor.awaitTermination(maxTime, TimeUnit.MINUTES)) {
            printBoard(board);
            log.info("Executor ok. WITH {} CORES, in {} milliseconds", cores, sw.getTime(TimeUnit.MILLISECONDS));
        } else {
            log.warn("The Executor didn't finish in {} minutes", maxTime);
        }


    }


    // Check if number is valid for the row, returns true if is invalid
    private static boolean IsInRow(int[][] board, int number, int row) {
        for (int i = 0; i < gridSize; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    // Check if number is valid for the column, returns true if is invalid
    private static boolean IsInColumn(int[][] board, int number, int column) {
        for (int i = 0; i < gridSize; i++) {
            if (board[i][column] == number) {
                return true;
            }
        }
        return false;
    }

    // Check if number is valid for the 3x3 grid, returns true if is invalid
    private static boolean IsInGrid(int[][] board, int number, int row, int column) {
        int gridRow = row - row % smallGridSize;
        int gridColumn = column - column % smallGridSize;

        for (int i = gridRow; i < gridRow + smallGridSize; i++) {
            for (int j = gridColumn; j < gridColumn + smallGridSize; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    // Call all method to verify if number is valid.
    private static boolean NumberIsValid(int[][] board, int number, int row, int column) {
        if (IsInRow(board, number, row) ||
                IsInColumn(board, number, column) ||
                IsInGrid(board, number, row, column)) {
            return false;
        }
        return true;

    }

    // Main method for solving the Sudoku board, based in recursive/backtracking approach.
    private static boolean SolveBoard(int[][] board) {
        if (Thread.currentThread().isInterrupted()) return false;

        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (board[row][column] == 0) {
                    for (int testedNumber = 1; testedNumber <= gridSize; testedNumber++) {
                        if (NumberIsValid(board, testedNumber, row, column)) {
                            board[row][column] = testedNumber;
                            if (SolveBoard(board)) {
                                return true;
                            } else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;


    }

    // Function that prints the board on screen
    private static void printBoard(int[][] board) {

        if (Thread.currentThread().isInterrupted()) return;

        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        for (int i = 0; i < gridSize; i++) {
            System.out.print("| ");

            // for 9x9 boards, 01,02 format is not needed
            if (smallGridSize == 3) {
                for (int j = 0; j < gridSize; j++) {
                    if ((j + 1) % smallGridSize == 0) {
                        System.out.print(board[i][j] + " | ");
                    } else {
                        System.out.print(board[i][j] + " ");
                    }
                }
            }
            // for 16x16 boards and so on, numbers below 10 needs an extra "0" for better printing.
            else {
                for (int j = 0; j < gridSize; j++) {
                    String number = String.valueOf(board[i][j]);
                    if (board[i][j] < 10) {
                        number = "0" + board[i][j];
                    }
                    if ((j + 1) % smallGridSize == 0) {
                        System.out.print(number + " | ");
                    } else {
                        System.out.print(number + " ");
                    }
                }
            }
            System.out.println();
            if ((i + 1) % smallGridSize == 0)
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        }
        System.out.println("=================================================================");
    }


    // Function used for getting many boards with posible solutions.
    public static boolean BoardIteratorGen(int[][] board) {
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                if (board[row][column] == 0) {
                    for (int testedNumber = 1; testedNumber <= gridSize; testedNumber++) {
                        if (NumberIsValid(board, testedNumber, row, column)) {
                            // create new empty board
                            int[][] newBoard = new int[gridSize][gridSize];
                            CloneMatrix(board, newBoard);
                            newBoard[row][column] = testedNumber;
                            // save new board
                            stack.add(newBoard);
                        }
                    }
                }
            }
        }
        return true;
    }

    // Method utilized for matrix cloning.
    private static void CloneMatrix(int[][] original, int[][] result) {
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original.length; j++) {
                result[i][j] = original[i][j];
            }
        }
    }

    // call for Elimination method for each 0 in the board.
    private static void CheckElimination(int[][] board) {
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                // if zero, check for elimination
                if (board[row][column] == 0) {
                    Elimination(board, row, column);
                }
            }
        }
    }

    // Elimination method
    private static void Elimination(int[][] board, int cellRow, int cellColumn) {

        List<Integer> candidates = new ArrayList<Integer>();

        // Variables used for loop withing the small grid.
        int gridRow = cellRow - cellRow % smallGridSize;
        int gridColumn = cellColumn - cellColumn % smallGridSize;

        for (int i = 0; i < gridSize; i++) {
            candidates.add(i + 1);
        }
        // check the row and column
        for (int i = 0; i < gridSize; i++) {
            if (board[i][cellColumn] != 0) {
                if (candidates.contains(Integer.valueOf(board[i][cellColumn]))) {
                    candidates.remove(Integer.valueOf(board[i][cellColumn]));
                }
            }
            if (board[cellRow][i] != 0) {
                if (candidates.contains(Integer.valueOf(board[cellRow][i]))) {
                    candidates.remove(Integer.valueOf(board[cellRow][i]));
                }
            }
        }
        // check the grid
        for (int i = gridRow; i < gridRow + smallGridSize; i++) {
            for (int j = gridColumn; j < gridColumn + smallGridSize; j++) {
                if (board[i][j] != 0) {
                    if (candidates.contains(Integer.valueOf(board[i][j]))) {
                        candidates.remove(Integer.valueOf(board[i][j]));
                    }
                }
            }
        }
        // check if only one candidate remains
        if (candidates.size() == 1) {
            //System.out.println("ELIMINATION FOR OBVIOUS NUMBER: " + candidates.get(0));
            //log.debug("AT row {} and column {}", cellRow, cellColumn);
            board[cellRow][cellColumn] = candidates.get(0);
        }
    }


    private static void CheckLoneRanger(int[][] board) {
        //CHECK LONE RANGER BY COLUMN
        for (int i = 0; i < gridSize; i++) {
            LoneRangerRow(board, i);
        }
        for (int i = 0; i < gridSize; i++) {
            LoneRangerColumn(board, i);
        }
    }

    private static void LoneRangerRow(int[][] board, int cellColumn) {

        List<int[]> position = new ArrayList<>();

        List<List<Integer>> candidates = new ArrayList<List<Integer>>();
        //check lone rangers in row
        String candi = "";
        for (int row = 0; row < gridSize; row++) {
            //if 0, check candidates for it

            if (board[row][cellColumn] == 0) {
                List<Integer> validNumber = new ArrayList<Integer>();
                for (int n = 1; n <= gridSize; n++) {
                    if (NumberIsValid(board, n, row, cellColumn)) {
                        validNumber.add(n);
                    }
                }
                candidates.add(validNumber);
                int[] pos = {row, cellColumn};
                position.add(pos);
            }
        }

        for (int i = 1; i <= gridSize; i++) {

            for (int group = 0; group < candidates.size(); group++) {

                // if it exist, check if it exsists in other group (lone ranger)
                if (candidates.get(group).contains(Integer.valueOf(i))) {
                    int counter = 0;

                    // checking if it is only 1 candidate for i number
                    for (int n = 0; n < candidates.size(); n++) {
                        if (candidates.get(n).contains(Integer.valueOf(i))) {
                            candidates.get(n).remove(Integer.valueOf(i));
                            counter++;
                        }
                    }
                    // if counter equals one, then i is a lone ranger.
                    if (counter == 1) {
                        board[position.get(group)[0]][position.get(group)[1]] = i;
                    }
                }
            }
        }
    }

    private static void LoneRangerColumn(int[][] board, int cellRow) {

        List<int[]> position = new ArrayList<>();
        List<List<Integer>> candidates = new ArrayList<List<Integer>>();
        //check lone rangers in column
        String candi = "";
        for (int column = 0; column < gridSize; column++) {
            //if 0, check candidates for it

            if (board[cellRow][column] == 0) {
                List<Integer> validNumber = new ArrayList<Integer>();
                for (int n = 1; n <= gridSize; n++) {
                    if (NumberIsValid(board, n, cellRow, column)) {
                        validNumber.add(n);
                    }
                }
                candidates.add(validNumber);
                int[] pos = {cellRow, column};
                position.add(pos);
            }
        }
        for (int i = 1; i <= gridSize; i++) {

            for (int group = 0; group < candidates.size(); group++) {

                // if it exist, check if it exsists in other group (lone ranger)
                if (candidates.get(group).contains(Integer.valueOf(i))) {
                    int counter = 0;
                    // checking if it is only 1 candidate for i number
                    for (int n = 0; n < candidates.size(); n++) {
                        if (candidates.get(n).contains(Integer.valueOf(i))) {
                            candidates.get(n).remove(Integer.valueOf(i));
                            counter++;
                        }
                    }
                    // if counter equals one, then i is a lone ranger.
                    if (counter == 1) {
                        board[position.get(group)[0]][position.get(group)[1]] = i;
                    }
                }
            }
        }
    }


}





