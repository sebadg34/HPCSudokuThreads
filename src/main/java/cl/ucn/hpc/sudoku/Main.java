package cl.ucn.hpc.sudoku;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *  The Main Class of Sudoku Solver
 *
 * @author Sebastian Delgado Guerra
 */

@Slf4j
public class Main {

    // Variables used for length of cycles inside the board
    public static int smallGridSize;
    public static int gridSize;





    // Matrix used for the sudoku board itself.
    // 0 equals to empty space in the sudoku board.
    static int [][] board2 = {
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
    static int [][] board5 = {
            {0,0,3,0,2,0,4,0,0},
            {0,0,0,4,0,9,0,0,0},
            {0,0,8,0,0,0,0,6,0},
            {0,0,2,0,0,3,0,7,0},
            {7,0,0,0,0,0,0,0,9},
            {0,4,0,1,0,0,5,0,0},
            {0,6,0,0,0,0,2,0,0},
            {0,0,0,3,0,7,0,0,0},
            {0,0,9,0,5,0,1,0,0}
    };

    // 16x16 easy
    static int [][] board_easy = {
            {0,3,12,7,10,0,8,0,9,0,14,0,0,0,0,4},
            {0,15,0,13,16,1,0,0,0,3,4,0,6,10,5,0},
            {14,5,0,0,0,0,0,0,10,2,1,11,16,7,0,9},
            {4,0,10,0,0,5,9,12,16,7,13,6,3,15,0,0},
            {3,0,1,0,11,2,0,6,0,0,16,0,0,0,0,8},
            {2,9,0,6,0,14,5,0,7,13,8,0,0,1,15,0},
            {10,0,16,0,8,0,0,4,0,0,2,0,9,0,14,6},
            {0,0,15,0,9,7,16,1,0,4,0,5,0,13,10,0},
            {0,4,8,0,5,0,14,0,2,1,3,9,0,6,0,0},
            {15,14,0,10,0,16,0,0,8,0,0,7,0,5,0,1},
            {0,12,3,0,0,9,6,15,0,10,5,0,7,0,11,13},
            {7,0,0,0,0,4,0,0,15,0,11,13,0,14,0,2},
            {0,0,14,15,4,10,1,5,3,6,7,0,0,11,0,12},
            {5,0,13,3,6,8,2,9,0,0,0,0,0,0,4,7},
            {0,2,11,4,0,15,3,0,0,0,10,16,1,0,6,0},
            {6,0,0,0,0,11,0,16,0,14,0,4,15,3,2,0}
    };

    // 16x16 medium
    static int [][] board = {
            {0,0,0,10,16,0,0,0,0,0,0,13,0,12,0,11},
            {4,16,0,13,2,15,0,14,7,0,6,5,1,0,8,9},
            {14,0,2,6,1,0,9,13,4,15,8,0,5,0,0,0},
            {0,0,7,9,4,0,11,0,16,0,1,10,14,0,13,0},
            {7,10,0,8,0,11,1,15,0,6,0,0,0,5,0,2},
            {0,0,0,15,10,0,2,0,0,13,0,14,11,9,3,1},
            {2,0,3,16,0,6,5,12,1,0,10,9,8,0,0,0},
            {0,1,0,0,0,0,0,0,0,16,11,0,0,7,0,10},
            {13,0,16,0,0,2,7,0,0,0,0,0,0,0,1,0},
            {0,0,0,7,5,4,0,10,13,8,15,0,6,11,0,14},
            {6,15,10,11,13,0,14,0,0,5,0,7,3,0,0,0},
            {3,0,12,0,0,0,15,0,14,1,9,0,13,0,10,7},
            {0,7,0,3,11,14,0,6,0,4,0,1,10,13,0,0},
            {0,0,0,1,0,3,16,2,11,10,0,8,15,14,0,4},
            {11,13,0,2,8,10,0,1,5,0,12,15,7,0,9,6},
            {10,0,8,0,15,0,0,0,0,0,0,16,2,0,0,0}
    };

    // 16x16 hard
    static int [][] board_hard = {
            {0,0,0,0,0,6,0,0,0,0,0,0,1,0,16,0},
            {0,3,6,0,0,0,0,9,0,0,10,11,12,0,15,0},
            {12,14,0,10,0,0,16,2,0,0,13,0,0,4,5,11},
            {15,0,11,8,12,14,0,0,0,0,0,5,7,0,6,0},
            {0,0,14,0,0,11,0,0,9,3,0,12,0,0,0,0},
            {0,4,0,11,1,0,0,16,0,5,0,0,6,0,0,0},
            {0,16,0,0,7,0,0,14,13,11,8,4,0,10,9,15},
            {13,0,0,15,3,4,0,8,0,0,0,0,16,0,12,0},
            {0,5,0,16,0,0,0,0,15,0,2,9,13,0,0,7},
            {9,2,12,0,13,1,8,15,6,0,0,3,0,0,10,0},
            {0,0,0,1,0,0,7,0,5,0,0,13,11,0,8,0},
            {0,0,0,0,4,0,2,3,0,0,7,0,0,6,0,0},
            {0,8,0,6,2,0,0,0,0,0,5,16,10,9,0,12},
            {1,13,2,0,0,12,0,0,10,9,0,0,15,0,4,5},
            {0,15,0,3,14,9,0,0,1,0,0,0,0,13,7,0},
            {0,12,0,9,0,0,0,0,0,0,6,0,0,0,0,0}

    };


    public static void main(String[] args) {

        gridSize = board.length;
        smallGridSize = (int) Math.sqrt(gridSize);
        int[][] boardCopy = board;

        int N = 1;

        List<Long> times = new ArrayList<>();




        System.out.println("CURRENT BOARD TO SOLVE");
        printBoard(board);
        System.out.println("========================");
        System.out.println("========================");

        for (int i = 0; i < N; i ++){
            long time = runInMillis();
            log.debug("N:{} -> Time. {}",i,time);
            times.add(time);
            board = boardCopy;
        }



    }


    private static long runInMillis(){

        // Stopwatch
        StopWatch sw = StopWatch.createStarted();


            if(SolveBoard(board)){
                System.out.println("BOARD SOLVED");
                printBoard(board);
            }else{
                System.out.println("ERROR, BOARD NOT SOLVABLE");
            }



        return sw.getTime(TimeUnit.MILLISECONDS);
    }




    // Check if number is valid for the row
    // returns true if is invalid
    private static boolean IsInRow(int[][] board, int number, int row){
        for (int i = 0; i < gridSize; i++){
            if(board[row][i] == number){
                return true;
            }
        }
        return false;
    }

    // Check if number is valid for the column
    // returns true if is invalid
    private static boolean IsInColumn(int[][] board, int number, int column){
        for (int i = 0; i < gridSize; i++){
            if(board[i][column] == number){
                return true;
            }
        }
        return false;
    }

    // Check if number is valid for the 3x3 grid.
    // returns true if is invalid.
    private static boolean IsInGrid(int[][] board, int number,int row, int column){
        int gridRow = row - row % smallGridSize;
        int gridColumn = column - column % smallGridSize;

        for(int i = gridRow; i < gridRow + smallGridSize; i++){
            for(int j = gridColumn; j < gridColumn + smallGridSize; j++){
                if(board[i][j] == number){
                    return true;
                }
            }
        }
        return false;
    }

    // Call all method to verify if number is valid.
    private static boolean NumberIsValid(int[][] board, int number, int row, int column){
        if(IsInRow(board,number,row) ||
           IsInColumn(board,number,column) ||
           IsInGrid(board,number,row,column)){
            return false;
        }
        return true;
    }

    // Main method for solving the Sudoku board, based in recursive/backtracking approach.
    private static boolean SolveBoard(int[][]board){
       for(int row = 0; row < gridSize; row++){
           for(int column = 0; column < gridSize; column++){
               if(board[row][column] == 0){
                   for(int testedNumber = 1; testedNumber <= gridSize; testedNumber++){
                       if(NumberIsValid(board,testedNumber,row,column)){
                           board[row][column] = testedNumber;

                           if(SolveBoard(board)){
                               return true;
                           }
                           else {
                               board[row][column] = 0;
                           };
                       }
                   }
                   return false;
               }
           }
       }
       return true;
    }

    // Function that prints the board on screen
    private static void printBoard(int[][] board){
        for (int i = 0; i < gridSize; i++){
            System.out.print("| ");

            // for 9x9 boards, 01,02 format is not needed
            if(smallGridSize == 3){
                for (int j = 0; j < gridSize; j++){
                    if((j + 1)%smallGridSize == 0){
                        System.out.print(board[i][j] + " | ");
                    }
                    else{
                        System.out.print(board[i][j] + " ");
                    }
                }
            }
            // for 16x16 boards and so on, numbers below 10 needs an extra "0" for better printing.
            else{
                for (int j = 0; j < gridSize; j++){
                    String number = String.valueOf(board[i][j]);
                    if(board[i][j] < 10){
                        number = "0"+board[i][j];
                    }
                    if((j + 1)%smallGridSize == 0){
                        System.out.print(number + " | ");
                    }
                    else{
                        System.out.print(number + " ");
                    }
                }

            }
            System.out.println();
            if((i + 1)%smallGridSize == 0)
                System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - -");

        }
    }


}
