package edu.jsu.mcis;

import java.awt.event.*;
import javax.swing.*;

public class TicTacToeController implements ActionListener {

    private final TicTacToeModel model;
    private final TicTacToeView view;
    
    /* CONSTRUCTOR */

    public TicTacToeController(int width) {
        
        /* Initialize model, view, and width */

        model = new TicTacToeModel(width);
        view = new TicTacToeView(this, width);
        
    }
    
    public String getMarkAsString(int row, int col) {       
        return (model.getMark(row, col).toString());       
    }
   
    public TicTacToeView getView() {       
        return view;       
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        
        if (event.getSource().getClass() == JButton.class) {
            
            JButton pressedButton = ((JButton)event.getSource());
            
            int row, col;
            
            /*
             * We're told to assume that the game board will be no larger than
             * 9x9, so the button coordinates, as stored in its name,
             * represent single digit integers and are in fixed positions.
             */
            
            row = Character.getNumericValue(pressedButton.getName().charAt(6));
            col = Character.getNumericValue(pressedButton.getName().charAt(7));
            
            if (model.makeMark(row, col)) {
				
				view.showResult(pressedButton.getName());
                
                /*
                 * Move is successful here; clear previous messages
                 * (potentially error messages) so to not confuse the next
                 * player/clear start message on first move
                 */
                
                view.clearResult();
                
                view.updateSquares(row, col);
            }
            
            else {
                
                /*
                * Note: it's not possible to submit an out-of-bounds move using
                * the default (untampered) GUI, assuming the 9x9 board
                * assumption is in place.
                *
                * That said, the same message from the previous version of this
                * project is retained for consistency, despite describing some
                * (assumed) impossible events.
                */
                
                view.showResult("Entered location is invalid, already marked, or out of bounds.");
                
            }
            
            if (model.isGameover()) {
                
                view.disableSquares();
                
                view.showResult(model.getResult().name());
                
            }
            
        }
        
    }

}
