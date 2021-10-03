package cl.ucn.hpc.sudoku;
import lombok.extern.slf4j.Slf4j;

/**
 *  The Main Class of Sudoku Solver
 *
 * @author Sebastian Delgado Guerra
 */

@Slf4j
public class Main {

    public static int gridSize = 9;

    public static void main(String[] args) {




        // Matrix used for the sudoku board itself.
        // 0 equals to empty space in the sudoku board.
        int [][] board = {
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

        if(SolveBoard(board)){
            System.out.println("BOARD SOLVED");
            printBoard(board);
        }else{
            System.out.println("ERROR, BOARD NOT SOLVABLE");
        }


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
        int gridRow = row - row % 3;
        int gridColumn = column - column % 3;

        for(int i = gridRow; i < gridRow + 3; i++){
            for(int j = gridColumn; j < gridColumn + 3; j++){
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
                   for(int testedNumber = 1; testedNumber <= 9; testedNumber++){
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

    private static void printBoard(int[][] board){
        for (int i = 0; i < gridSize; i++){
            System.out.print("| ");
            for (int j = 0; j < gridSize; j++){
                if((j + 1)%3 == 0)
                 System.out.print(board[i][j] + " | ");
                else
                    System.out.print(board[i][j] + " ");
            }
            System.out.println();
            if((i + 1)%3 == 0)
                System.out.println("- - - - - - - - - - - - -");

        }
    }
}
