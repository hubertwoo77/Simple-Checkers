/**
* Supporting class for Checkers class allowing the user to make moves during their turn
*
* @author Hubert Woo
* @version July 3 2023
*/
import java.util.Scanner;
public class PlainPiece{
    private static boolean canElim;
    private String[][] chessBoard;
    private int initRow;
    private int initCol;
    private int finalRow;
    private int finalCol;
    private int numTargets;

    /**
    * Constructor for the PlainPiece class, created using the game board, the coordinates of the piece that the user wants to move, and the number of pieces that the opponent has
    *
    * @param chessBoard The board that the game is happening on
    * @param initRow Row of the board tile that the user selected
    * @param initCol Column of the board tile that the user selected
    * @param finalRow Row of the board tile that the user wants to move to
    * @param finalCol Column of the board tile that the user wants to move to
    * @param numTargets Number of pieces the opponent has
    */
    public PlainPiece(String[][] chessBoard, int initRow, int initCol, int finalRow, int finalCol, int numTargets){
        this.chessBoard = chessBoard;
        this.initRow = initRow;
        this.initCol = initCol; 
        this.finalRow = finalRow;
        this.finalCol = finalCol;
        this.numTargets = numTargets;
        movePiece();
    }

    /**
    * Get method for the chessboard, used to update the chessbard in the main class after the user is done their turn
    *
    * @return Chessboard after the user makes their move
    */
    public String[][] getChessBoard(){
        return chessBoard;
    }

    /**
    * Get method for the number of opponent pieces left, used to update the number of pieces if the user eliminated one during their turn
    *
    * @return Number of opponent pieces
    */
    public int getNumTargets(){
        return numTargets;
    } 
    
    /**
    * Main code for executing the move that the user wants to make
    * If the move is invalid, nothing will happen
    */
    public void movePiece(){
        canElim = false;
        if(checkValidPiece() == true){
            if( checkValidMove() == true){
                if(canElim == true){
                    eliminationMove();
                }
                else{
                    chessBoard[initRow][initCol] = "[]";
                    chessBoard[finalRow][finalCol] = "⛂ ";
                }
            }
        }
    }

    /**
    * Checks if the tile the user selected has one of their pieces on it
    *
    * @return Whether or not the selected tile is a piece 
    */
    public boolean checkValidPiece(){
        boolean isPiece = false;
        if( (chessBoard[initRow][initCol]).equals("⛂ ")){
            isPiece = true;
        }
        return isPiece;
    }

    /**
    * Considers the piece that the user selected and checks if the tile they want to move to is valid
    *
    * @return Whether or not their move is valid or not
    */
    public boolean checkValidMove(){
        boolean isMove = false;
        if( initRow-1 == finalRow && initCol-1 == finalCol){
            if( chessBoard[initRow-1][initCol-1].equals("[]")){
                isMove = true;
            }
        }
        if( initRow-1 == finalRow && initCol+1 == finalCol){
            if( chessBoard[initRow-1][initCol+1].equals("[]")){
                isMove = true;
            }
        }
        if( initRow-1 == finalRow && initCol-1 == finalCol){
            if( chessBoard[initRow-1][initCol-1].equals("⛀ ")){
                if( finalCol < 8){
                    if( chessBoard[initRow-2][initCol-2].equals("[]")){
                        isMove = true;
                        canElim = true;
                    }
                }
            }
        }
        if( initRow-1 == finalRow && initCol+1 == finalCol){
            if( chessBoard[initRow-1][initCol+1].equals("⛀ ")){
                if( finalCol < 8){
                    if( chessBoard[initRow-2][initCol+2].equals("[]")){
                        isMove = true;
                        canElim = true;
                    }
                }
            }
        }
        return isMove;
    }

    /**
    * Interaction for taking one of the opponent's pieces
    */
    public void eliminationMove(){
        if( initRow-1 == finalRow && initCol-1 == finalCol){
            chessBoard[initRow][initCol] = "[]";
            chessBoard[finalRow][finalCol] = "[]";
            chessBoard[finalRow-1][finalCol-1] = "⛂ ";
            numTargets--;
        }
        else if( initRow-1 == finalRow && initCol+1 == finalCol){
            chessBoard[initRow][initCol] = "[]";
            chessBoard[finalRow][finalCol] = "[]";
            chessBoard[finalRow-1][finalCol+1] = "⛂ ";
            numTargets--;
        }
    }
}