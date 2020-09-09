package edu.jsu.mcis;

import java.util.Scanner;

public class TicTacToeView {
    
    private final Scanner keyboard;
    
    /* CONSTRUCTOR */
	
    public TicTacToeView() {
        
        /* Initialize scanner (for console keyboard) */
        
        keyboard = new Scanner(System.in);
        
    }
	
    public TicTacToeMove getNextMove(boolean isXTurn) {
        
        System.out.println("\nPlayer " + (isXTurn ? "1 (X)" : "2 (O)") + " Move:");
        System.out.println("Enter the row and column numbers, separated by a space: ");
        
        /*
         * To avoid the utilization of RegEx and/or per-character `String`
         * parsing, we will assume that, regardless of form entered, the first
         * two `int`s from the user's response are their desired row and column,
         * respectively
        */
        
        return new TicTacToeMove(keyboard.nextInt(), keyboard.nextInt());
    }

    public void showInputError() {

        System.out.println("Entered location is invalid, already marked, or out of bounds.");

    }

    public void showResult(String r) {

        System.out.println(r + "!");

    }
    
    public void showBoard(String board) {
        
        System.out.println("\n\n" + board);
        
    }
	
}