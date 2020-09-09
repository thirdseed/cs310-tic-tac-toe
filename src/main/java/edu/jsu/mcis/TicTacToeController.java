package edu.jsu.mcis;

public class TicTacToeController {

    private final TicTacToeModel model;
    private final TicTacToeView view;
    
    /* CONSTRUCTOR */

    public TicTacToeController(int width) {
        
        /* Initialize model, view, and width */

        model = new TicTacToeModel(width);
        view = new TicTacToeView();
        
    }

    public void start() {
        
        /* MAIN LOOP (repeats until game is over) */
        
        while(!model.isGameover()) {
            
            /* Let the user know what the board looks like at the start of their turn */
            view.showBoard(model.toString());
            
            /* Take/validate turn inputs */
            TicTacToeMove t = view.getNextMove(model.isXTurn());

            while (!model.makeMark(t.getRow(), t.getCol())) {
                view.showInputError();
                
                t = view.getNextMove(model.isXTurn());
            }
        }
        
        /* After the game is over, show the final board and the winner */

        view.showBoard(model.toString());
        view.showResult(model.getResult().toString());
        
    }

}