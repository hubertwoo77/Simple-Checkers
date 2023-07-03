/**
* Supporting class for Checkers class allowing the bot to calculate what moves it can make and also to make a move during its turn
*
* @author Hubert Woo
* @version July 3 2023
*/
import java.util.ArrayList;
public class Computer{
    private String[][] chessBoard;
    private int numTargets;
    private ArrayList<String> whatMove = new ArrayList<String>();
    private ArrayList<Integer> validPieceRow = new ArrayList<Integer>();
    private ArrayList<Integer> validPieceCol = new ArrayList<Integer>();
    private String difficulty;

    /**
    * Constructor for the Computer class
    *
    * @param difficulty Determines whether or not the bot will prioritize eliminations over normal moves
    * @param chessBoard Given access to the most recent iteration of the chessboard to consider where itsp ieces are and what to move
    * @param Number of the opponent's pieces
    */
    public Computer(String difficulty, String[][] chessBoard, int numTargets){
        this.difficulty = difficulty;
        this.chessBoard = chessBoard;
        this.numTargets = numTargets;

        checkPossibleMoves();
        System.out.println("Bot's turn:");
        if( difficulty.equals("normal") == true){
            if( whatMove.indexOf("Eliminate Right") > 0){
                int rowPos = validPieceRow.get(whatMove.indexOf("Eliminate Right"));
                int colPos = validPieceCol.get(whatMove.indexOf("Eliminate Right"));
                elimRight(rowPos, colPos);
            }
            else if( whatMove.indexOf("Eliminate Left") > 0){
                int rowPos = validPieceRow.get(whatMove.indexOf("Eliminate Left"));
                int colPos = validPieceCol.get(whatMove.indexOf("Eliminate Left"));
                elimLeft(rowPos, colPos);
            }
            else{
                makeRandomMove();
            }
        }
        else{
            makeRandomMove();
        }
    }

    /**
    * Runs through the board checking for the bot's pieces and check if the piece can make any valid moves. If it can, its coordinates are added to an ArrayList
    */
    public void checkPossibleMoves(){
        for (int row = 1; row < chessBoard.length-1; row++)
        {
            for (int col = 1; col < chessBoard[0].length; col++)
            {
                if(checkValidPiece(row, col) == true){
                    if(col == 8){
                        if( checkValidMoveLastCol(row,col) == true){
                            validPieceRow.add(new Integer(row));
                            validPieceCol.add(new Integer(col));
                        }
                    }
                    else{
                        if( checkValidMove(row,col) == true){
                            validPieceRow.add(new Integer(row));
                            validPieceCol.add(new Integer(col));
                        }
                    }
                }
            }
        }
    }

    /**
    * Used as the only decision-making factor in easy mode of the bot and as a last resort in the normal difficulty
    * Chooses a random valid piece from the computer to make a random move
    */
    public void makeRandomMove(){
        int choosePiece = (int) (Math.random()*(validPieceRow.size()-1));
        int rowPos = validPieceRow.get(choosePiece);
        int colPos = validPieceCol.get(choosePiece);
        if( whatMove.get(choosePiece).equals("Go Right")){
            goRight( rowPos, colPos);
        }
        else if( whatMove.get(choosePiece).equals("Go Left")){
            goLeft(rowPos, colPos);
        }
        else if( whatMove.get(choosePiece).equals("Eliminate Right")){
            elimRight(rowPos, colPos);
        }
        else if( whatMove.get(choosePiece).equals("Eliminate Left")){
            elimLeft(rowPos, colPos);
        }
    }

    /**
    * Checks if the tile specified by the row and column inputs is a piece or not
    *
    * @param rowNum Index number of the tile that the loop in checkPossibleMoves() is checking
    * @param colNum Index number of the tile that the loop in checkPossibleMoves() is checking
    * @return Whether or not the given tile is a piece 
    */
    public boolean checkValidPiece(int rowNum, int colNum){
        boolean isPiece = false;
        if( (chessBoard[rowNum][colNum]).equals("⛀ ")){
            isPiece = true;
        }
        return isPiece;
    }

    /**
    * Checks if the piece specified by the row and column inputs has any valid moves
    *
    * @param rowNum Index number of the piece that the loop in checkPossibleMoves() is checking
    * @param colNum Index number of the piece that the loop in checkPossibleMoves() is checking
    * @return Whether or not the given piece can move 
    */
    public boolean checkValidMove( int rowNum, int colNum){
        boolean isMove = false;
        int randomNum = (int) (Math.random()*2); 
        //If some pieces have either choice of moving left or right, this variable randomizes the move so that every viable piece doesn't lead to the same move
        if( (rowNum+1) <= 8 && (colNum+1) <=8){
            if( randomNum == 0){
                if( chessBoard[rowNum+1][colNum+1].equals("⛂ ")){
                    if( (rowNum + 2) <= 8 && (colNum+2) <=8){
                        if( chessBoard[rowNum+2][colNum+2].equals("[]")){
                            isMove = true;
                            whatMove.add("Eliminate Right");
                        }
                    }
                }
                else if( chessBoard[rowNum+1][colNum-1].equals("⛂ ")){
                    if( (rowNum + 2) <= 8 && (colNum-2) <=8){
                        if( chessBoard[rowNum+2][colNum-2].equals("[]")){
                            isMove = true;
                            whatMove.add("Eliminate Left");
                        }
                    }
                }
                else if( chessBoard[rowNum+1][colNum+1].equals("[]")){
                    isMove = true;
                    whatMove.add("Go Right");
                }
                else if( chessBoard[rowNum+1][colNum-1].equals("[]")){
                    isMove = true;
                    whatMove.add("Go Left");
                }
                //If any of these moves are valid, it is added to a string Arraylist indicating what type of move is valid
                //The index positions of the tpye of move will correspond with which piece can make that move across all three arraylists
            }
            if(randomNum == 1){
                if( chessBoard[rowNum+1][colNum-1].equals("⛂ ")){
                    if( (rowNum + 2) <= 8 && (colNum-2) <=8){
                        //Makes sure indices are included in array before checking that index
                        if( chessBoard[rowNum+2][colNum-2].equals("[]")){
                            isMove = true;
                            whatMove.add("Eliminate Left");
                        }
                    }
                }
                else if( chessBoard[rowNum+1][colNum+1].equals("⛂ ")){
                    if( (rowNum + 2) <= 8 && (colNum+2) <=8){
                        if( chessBoard[rowNum+2][colNum+2].equals("[]")){
                            isMove = true;
                            whatMove.add("Eliminate Right");
                        }
                    }
                }
                else if( chessBoard[rowNum+1][colNum-1].equals("[]")){
                    isMove = true;
                    whatMove.add("Go Left");
                }
                else if( chessBoard[rowNum+1][colNum+1].equals("[]")){
                    isMove = true;
                    whatMove.add("Go Right");
                }
            }
        }
        return isMove;
    }

    /**
    * Only checks the possible moves on the left of a piece to avoid an ArrayIndexOutOfBoundsException when the chosen piece is on the far right side
    *
    * @param rowNum Index number of the piece that the loop in checkPossibleMoves() is checking
    * @param colNum Index number of the piece that the loop in checkPossibleMoves() is checking
    * @return Whether or not the given piece can move 
    */
    public boolean checkValidMoveLastCol( int rowNum, int colNum){
        boolean isMove = false;
        if( chessBoard[rowNum+1][colNum-1].equals("[]")){
            isMove = true;
            whatMove.add("Go Left");
        }
        else if( chessBoard[rowNum+1][colNum-1].equals("⛂ ")){
            if( chessBoard[rowNum+2][colNum-2].equals("[]")){
                isMove = true;
                whatMove.add("Eliminate Left");
            }
        }
        return isMove;
    }

    /**
    * Moves the piece left
    *
    * @param rowNum Row index of the piece that is being moved
    * @param colNum Column index of the piece that is being moved
    */
    public void goLeft(int rowNum, int colNum){
        chessBoard[rowNum][colNum] = "[]";
        chessBoard[rowNum+1][colNum-1] = "⛀ ";
    }

    /**
    * Moves the piece right
    *
    * @param rowNum Row index of the piece that is being moved
    * @param colNum Column index of the piece that is being moved
    */
    public void goRight(int rowNum, int colNum){
        chessBoard[rowNum][colNum] = "[]";
        chessBoard[rowNum+1][colNum+1] = "⛀ ";
    }

    /**
    * Eliminates an opponent piece on the left
    *
    * @param rowNum Row index of the piece that is being moved
    * @param colNum Column index of the piece that is being moved
    */
    public void elimLeft(int rowNum, int colNum){
        chessBoard[rowNum][colNum] = "[]";
        chessBoard[rowNum+1][colNum-1] = "[]";
        chessBoard[rowNum+2][colNum-2] = "⛀ ";
        numTargets--;
    }

    /**
    * Eliminates an opponent piece on the right
    *
    * @param rowNum Row index of the piece that is being moved
    * @param colNum Column index of the piece that is being moved
    */
    public void elimRight(int rowNum, int colNum){
        chessBoard[rowNum][colNum] = "[]";
        chessBoard[rowNum+1][colNum+1] = "[]";
        chessBoard[rowNum+2][colNum+2] = "⛀ ";
        numTargets--;
    }

    /**
    * Get method for the chessboard, used to update the chessbard in the main class after the bot is done its turn
    *
    * @return Chessboard after bot makes its move
    */
    public String[][] getChessBoard(){
        return chessBoard;
    }

    /**
    * Get method for the number of opponent pieces left, used to update the number of pieces if the bot eliminated one during its turn
    *
    * @return Number of opponent pieces
    */
    public int getNumTargets(){
        return numTargets;
    } 

    
}