/*
* A short, quick simple game of checkers against a bot with two difficulties
*
* @author Hubert Woo
* @version July 3 2023
*/
import java.util.Scanner;
import java.util.ArrayList;
public class Checkers {

    public static GameBoard chessBoard = new GameBoard();
    public static Scanner sc = new Scanner(System.in);

    /**
    * Mainline logic for the game
    */
    public static void main(String[] args) {
        introduction();
        playGame();
    }

    /**
    * Introduces the user to the game explaining how the rules work and how to play
    */
    public static void introduction(){
        System.out.println("Welcome to checkers, the most well balanced game of all mankind. Inferior only to chess.");
        System.out.println("You are controlling the white pieces.");
        System.out.println("Move your pieces by typing in a letter + number to indicate which piece you want to move. (Ex. \"F2\")");
        System.out.println("Then type in another combination of a letter + number (example: C1, D2) to indicate where you want to move your piece. (Ex. \"E1\")");
        System.out.println("To take an opponent's piece, input the coordinates of the opponent's piece as your piece's destination coordinate (second input).");
        System.out.println("If your move is illegal or your first input is not a checker piece, your turn will be skipped.");
        System.out.println("First to take 3 of the opponent's pieces wins. Indicate the difficulty of the bot that you want then start your turn. \n");
    }

    /**
    * Game unfolds here with a loop running until either the player or the computer loses enough pieces to break the loop. The winner is announced afterwards
    */
    public static void playGame(){
        int blackPieces = 12;
        int whitePieces = 12;
        int initRow;
        int initCol;
        int finalRow;
        int finalCol;
        PlainPiece playerInteraction;
        chessBoard.printBoard();
        String difficulty = whatDifficulty();
        Computer bot;
        while( blackPieces > 9 && whitePieces > 9){
            System.out.println("Your Turn.");
            String firstInput = sc.nextLine();
            //indicate what piece you want to move
            if( !checkCoordinates(firstInput)){
                while(!checkCoordinates(firstInput)){
                    System.out.println("Try again.");
                    firstInput = sc.nextLine();
                }
            }
            String secondInput = sc.nextLine();
            //indicate where you want to move that piece
            if( !checkCoordinates(secondInput)){
                while(!checkCoordinates(secondInput)){
                    System.out.println("Try again.");
                    secondInput = sc.nextLine();
                }
            }
            initRow = sequentialSearchRow(firstInput.substring(0,1) + " ");
            initCol = sequentialSearchColumn(firstInput.substring(1,2) + " ");
            finalRow = sequentialSearchRow(secondInput.substring(0,1) + " ");
            finalCol =sequentialSearchColumn(secondInput.substring(1,2) + " ");
            //Breaks the user's input (ex. A3) into individual components for one searching algorithm to find and return only the row, while the other algorithm returns the column
            playerInteraction = new PlainPiece(chessBoard.getGameBoard(), initRow, initCol, finalRow, finalCol, whitePieces);
            //Chessboard is constantly re-initialized to simulate every move the user tries to make
            chessBoard.setGameBoard(playerInteraction.getChessBoard());
            whitePieces = playerInteraction.getNumTargets();
            chessBoard.printBoard();
            System.out.println();
            if(!(blackPieces > 9 && whitePieces > 9)){
                break;
            }
            bot = new Computer(difficulty, chessBoard.getGameBoard(), blackPieces);
            //Chessboard is constantly re-initialized to always analyze an updated iteration of the board and calculate what move to make
            chessBoard.setGameBoard(bot.getChessBoard());
            blackPieces = bot.getNumTargets();
            chessBoard.printBoard();
            System.out.println();
        }
        if( whitePieces > blackPieces){
            System.out.println("You Lose!");
        }
        else{
            System.out.println("You win!");
        }
    }

    /**
    * Determines the difficulty of the bot. Easy means the bot makes random moves, normal means the bot tries to look for eliminations first before making random moves
    *
    * @return The difficulty of the bot that the user selected
    */
    public static String whatDifficulty(){
        System.out.println("Choose difficulty (easy/normal)."); 
        String difficulty = sc.nextLine();
        if( !(difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("normal"))){
            while( !(difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("normal"))){
                System.out.println("Try again.");
                difficulty = sc.nextLine();
            }
        }
        return difficulty;
    }

    /**
    * Searching algorithm finding the index of the row that the user selected
    *
    * @param target The letter corresponding to the row that the user selected, and that the method will find the index of
    * @return The index of the letter
    */
    public static int sequentialSearchRow( String target){
        int trackRowIndex = 2000000;
        for (int i = 0; i < (chessBoard.getGameBoard()).length; i++)
        {
            for (int n = 0; n < (chessBoard.getGameBoard())[0].length; n++)
            {
                if( (chessBoard.getGameBoard())[i][n].equalsIgnoreCase(target)){
                    trackRowIndex = i;
                    break;
                }
            }
            if( trackRowIndex != 2000000){
                break;
            }
        }
        return trackRowIndex;
    }

    /**
    * Searching algorithm finding the index of the column that the user selected
    *
    * @param target The number corresponding to the column that the user selected, and that the method will find the index of
    * @return The index of the number
    */
    public static int sequentialSearchColumn( String target){
        int trackColumnIndex = 2000000;
        for (int i = 0; i < (chessBoard.getGameBoard()).length; i++)
        {
            for (int n = 0; n < (chessBoard.getGameBoard())[0].length; n++)
            {
                if( (chessBoard.getGameBoard())[i][n].equals(target)){
                    trackColumnIndex = n;
                    break;
                }
            }
            if( trackColumnIndex != 2000000){
                break;
            }
        }
        return trackColumnIndex;
    }

    /**
    * Creates an ArrayList consisting of every combination of letters A-H and numbers 1-8 to check if the user has inputted a valid coordinate
    *
    * @param userInput The "coordinate" that the user inputted
    * @return Whether or not the coordinat is valid or not
    */
    public static boolean checkCoordinates( String userInput){
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8"};
        boolean expectedInput = false;
        ArrayList<String> combinations = new ArrayList<String>();
        for( String character : letters){
            for( String num : numbers){
                combinations.add(character + num);
            }
        }
        for( int i = 0; i < combinations.size() && expectedInput == false; i++){
            if( combinations.get(i).equalsIgnoreCase(userInput)){
                expectedInput = true;
            }
        }
        return expectedInput;
    }
}
