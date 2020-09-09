package edu.jsu.mcis;

import java.util.*;

public class TicTacToeModel {
    
    private Mark[][] board; /* Game board */
    private boolean xTurn;  /* True if X is current player */
    private int width;      /* Size of game board */
    
    /* ENUM TYPE DEFINITIONS */
    
    /* Mark (represents X, O, or an empty square */
    
    public enum Mark {
        
        X("X"), 
        O("O"), 
        EMPTY("-");

        private String message;
        
        private Mark(String msg) {
            message = msg;
        }
        
        @Override
        public String toString() {
            return message;
        }
        
    };
    
    /* Result (represents the final state of the game: X wins, O wins, a TIE,
       or NONE if the game is not yet over) */
    
    public enum Result {
        
        X("X"), 
        O("O"), 
        TIE("TIE"), 
        NONE("NONE");

        private String message;
        
        private Result(String msg) {
            message = msg;
        }
        
        @Override
        public String toString() {
            return message;
        }
        
    };
    
    /* CONSTRUCTOR */
    
    public TicTacToeModel() {
        
        this(TicTacToe.DEFAULT_WIDTH);
        
    }
    
    /* CONSTRUCTOR */
    
    public TicTacToeModel(int width) {
        
        /* Initialize width; X goes first */
        
        this.width = width;
        xTurn = true;
        
        /* Create board (width x width) as a 2D Mark array */
        
        board = new Mark[width][width];

        /* Initialize board by filling every square with empty marks */
        
        for (int row = 0; row < width; ++row) {
            for (int col = 0; col < width; ++col) {
                board[row][col] = Mark.EMPTY;
            }
        }
        
    }

    /**
     * Given a row and column, marks its position on the board for the current
     * player.
     * @param row desired row to make mark
     * @param col desired column to make mark
     * @return TRUE if the position given was successfully marked, FALSE
     * otherwise (the position given is not available on this game board)
     */
    public boolean makeMark(int row, int col) {
        if (this.isValidSquare(row, col) && !this.isSquareMarked(row, col)) {
            // Note: Current player's turn is considered over after making mark
            this.board[row][col] = (this.isXTurn() ? Mark.X : Mark.O);
            
            /* Flip current player in anticipation of next round */
            this.xTurn = !this.xTurn;
            
            return true;
        }
        
        // otherwise, the given position is not valid
        else return false;
        
    }

    /**
     * Given a row and column, determines if their position is within the bounds
     * of the board.
     * @param row Row of position to check
     * @param col Column of position to check
     * @return TRUE if the specified location is within the bounds of the board
     */
    private boolean isValidSquare(int row, int col) {
        
        return (row >= 0 && col >= 0) && (row < this.width && col < this.width);
        
    }

    /**
     * Given a row and column, determines if their position is marked.
     * @param row Row of position to check
     * @param col Column of position to check
     * @return TRUE if the square at specified location is marked
     */
    private boolean isSquareMarked(int row, int col) {
        
        return (board[row][col] != Mark.EMPTY);
            
    }

    /**
     * Retrieves the mark that occupies the position at the given row and column;
     * appears as an "empty" mark if the position is not yet taken OR is out of
     * bounds.
     * @param row Row of mark to retrieve
     * @param col Column of mark to retrieve
     * @return the mark from the square at the specified location
     */
    public Mark getMark(int row, int col) {
        
        return (isValidSquare(row, col) ? board[row][col] : Mark.EMPTY);
        
    }

    /**
     * Provides the game's current status in the form of its result
     * @return game result (game status)
     */
    public Result getResult() {
        
        if (this.isMarkWin(Mark.X)) return Result.X;
        else if (this.isMarkWin(Mark.O)) return Result.O;
        else if (this.isTie()) return Result.TIE;
        else return Result.NONE;
        
    }

    /**
     * Determines if the given mark has won the game
     * @param mark mark to determine win status with
     * @return TRUE if the given mark has won the game
     */
    private boolean isMarkWin(Mark mark) {

        return this.hasStreak(mark, false);

    }
    
    /**
     * Determines if the given mark appears in any streaks
     * @param mark mark whose presence to check for within streaks
     * @param countEmptySpaces if whether empty ("unclaimed") spaces should be
     * counted in streak
     * @return TRUE if mark appear within streak
     */
    private boolean hasStreak(Mark mark, boolean countEmptySpaces) {
        
        List<Mark> marks = new ArrayList<Mark>();
        
        marks.add(mark);
        if (countEmptySpaces) marks.add(Mark.EMPTY);
        
        /* DIAGONAL search: Top-left through bottom-right */
        for (int i = 0; marks.contains(board[i][i]); ++i) {
            if (i == (width-1)) return true;
        }
         
        /* DIAGONAL search: Bottom-left through top-right */
        for (int i = width; marks.contains(board[i-1][width-i]); --i) {
            if (i == 1) return true;
        }
        
        /* ORTHOGONAL search: rows */
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < width && marks.contains(board[x][y]); ++y) {
                if (y == (width-1)) return true;
            }
        }
        
        /* ORTHOGONAL search: columns */
        for (int y = 0; y < width; ++y) {
            for (int x = 0; x < width && marks.contains(board[x][y]); ++x) {
                if (x == (width-1)) return true;
            }
        }
        
        return false;
    }

    /**
     * Determines if the game is over as a result of no more moves remaining
     * @return FALSE the moment a potential-future move is discovered; TRUE if
     * the game is a tie
     */
    private boolean isTie() {
        
        /* Basic tie detection ("board is full"), as defined by assignment */
        
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < width; ++y) {
                if (this.board[x][y] == Mark.EMPTY) return false;
            }
        }
        
        return true;
        
    }

    /**
     * Determines if the game is over
     * @return TRUE if the game is over
     */
    public boolean isGameover() {
        
        return (Result.NONE != getResult());
        
    }

    /**
     * Determines if the current turn belongs to X
     * @return TRUE if the current turn is held by X
     */
    public boolean isXTurn() {
        
        return xTurn;
        
    }

    /**
     * Gets the width of this board
     * @return board width
     */
    public int getWidth() {
        
        return width;
        
    }
    
    /**
     * Represents current game data as a text-based game board, noting columns,
     * rows, and which marks occupy which positions in a grid-like format
     * @return The current game data as a text-based grid game board
     */
    @Override
    public String toString() {
        
        // header (columns)
        StringBuilder output = new StringBuilder("  ");
        for (int i = 0; i < this.width; ++i) output.append(i);
        output.append("\n");
        
        // rows
        for (int x = 0; x < width; ++x) {
            output.append(new StringBuilder("\n" + x + " "));
            
            for (int y = 0; y < width; ++y) output.append(this.board[x][y]);
        }
        
        return output.toString();
        
    }
    
}