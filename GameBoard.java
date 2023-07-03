/**
* Supporting class for Checkers class used for creating the board, printing the board, and various other functions
*
* @author Hubert Woo
* @version July 3 2023
*/
public class GameBoard{
    
    private String[][] chessBoard; 

    public GameBoard(){
        chessBoard = new String[9][9];
        createBoard();
    }

    /**
    * Creates the checker board displaying all the checker pieces, grid, and the coordinate system (A-H, 1-8)
    */
    public void createBoard(){
        for(int i = 0; i < chessBoard[0].length; i++){
            chessBoard[0][i] = (new Integer(i)).toString() + " ";
        }
        chessBoard[1][0] = "A ";
        chessBoard[2][0] = "B ";
        chessBoard[3][0] = "C ";
        chessBoard[4][0] = "D ";
        chessBoard[5][0] = "E ";
        chessBoard[6][0] = "F ";
        chessBoard[7][0] = "G ";
        chessBoard[8][0] = "H ";
        evenWhiteRow(1);
        oddWhiteRow();
        evenWhiteRow(3);
        for(int i = 4; i < 6; i++){
            for(int y = 1; y < chessBoard[0].length; y++){
                chessBoard[i][y] = "[]";
            }
        }
        evenBlackRow(6);
        oddBlackRow();
        evenBlackRow(8);

    }
    /**
    * Supporting method in creating the board, placing the white pieces on specific rows
    *
    * @param rowNum Indicates which row the white pieces will be placed on since there are two rows where the pattern of the white pieces are the same
    */
    public void evenWhiteRow( int rowNum){
        for(int i = 1; i < (chessBoard[0].length); i++){   //row
            if( i%2 == 0){
                chessBoard[rowNum][i] = "[]";
            }   
            else{
                chessBoard[rowNum][i] = "⛀ ";
            }
        }
    }
    
    /**
    * Supporting method in creating the board, placing the black pieces on specific rows
    *
    * @param rowNum Indicates which row the black pieces will be placed on since there are two rows where the pattern of the black pieces are the same
    */
    public void evenBlackRow(int rowNum){
        for(int i = 1; i < (chessBoard[0].length); i++){   //row
            if( i%2 != 0){
                chessBoard[rowNum][i] = "[]";
            }   
            else{
                chessBoard[rowNum][i] = "⛂ ";
            }
        }
    }
    /**
    * Supporting method in creating the board, placing the black pieces on a specific row
    */
    public void oddBlackRow(){
        for(int i = 1; i < (chessBoard[0].length); i++){   //row
            if( i%2 == 0){
                chessBoard[7][i] = "[]";
            }   
            else{
                chessBoard[7][i] = "⛂ ";
            }
        }
    }

    /**
    * Supporting method in creating the board, placing the white pieces on a specific row
    */
    public void oddWhiteRow(){
        for(int i = 1; i < (chessBoard[0].length); i++){   //row
            if( i%2 != 0){
                chessBoard[2][i] = "[]";
            }   
            else{
                chessBoard[2][i] = "⛀ ";
            }
        }
    }

    /**
    * Supporting method in gameplay, displaying the board after every move made by either the user or the computer
    */
    public void printBoard(){
        int detectRow = 0;
        for( String[] value : chessBoard){
            detectRow = 0;
            for(String position : value){
                if(detectRow !=8){
                    System.out.print(position);
                }
                else{
                    System.out.println(position);
                }
                detectRow++;

            }
        }
    }
    /**
    * Public mutator for the game board, used to update the gameboard after every move
    *
    * @param The gameboard that the match is happening on
    */
    public void setGameBoard( String[][] chessBoard){
        this.chessBoard = chessBoard; 
    }

    /**
    * Public accessor for the game board, used to pass the gameboard into parameters of other methods
    *
    * @return The gameboard that the match is happening on
    **/
    public String[][] getGameBoard(){
        return chessBoard;
    }

    
    
}