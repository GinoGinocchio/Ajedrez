import java.awt.*;
import javax.swing.*;
// -------------------------------------------------------------------------
/**
 * The main panel of the Chess game.
 * 
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class ChessPanel extends JPanel {
    private ChessMenuBar menuBar;
    private ChessGameBoard gameBoard;
    private ChessGameLog gameLog;
    private ChessGraveyard playerOneGraveyard;
    private ChessGraveyard playerTwoGraveyard;
    private transient ChessGameEngine gameEngine;
    
    public ChessGameLog getGameLog() {
        return gameLog;
    }

    public ChessGameBoard getGameBoard() {
        return gameBoard;
    }

    public ChessGameEngine getGameEngine() {
        return gameEngine;
    }

    public ChessGraveyard getGraveyard(int whichPlayer) {
        if (whichPlayer == 1) {
            return playerOneGraveyard;
        } else if (whichPlayer == 2) {
            return playerTwoGraveyard;
        } else {
            return null;
        }
    }
    
    // Factory method implementation
    public static ChessPanel createChessPanel() {
        ChessPanel panel = new ChessPanel();
        panel.setLayout(new BorderLayout());
        panel.menuBar = new ChessMenuBar();
        panel.gameBoard = new ChessGameBoard();
        panel.gameLog = new ChessGameLog();
        panel.playerOneGraveyard = new ChessGraveyard("Player 1's graveyard");
        panel.playerTwoGraveyard = new ChessGraveyard("Player 2's graveyard");
        panel.add(panel.menuBar, BorderLayout.NORTH);
        panel.add(panel.gameBoard, BorderLayout.CENTER);
        panel.add(panel.gameLog, BorderLayout.SOUTH);
        panel.add(panel.playerOneGraveyard, BorderLayout.WEST);
        panel.add(panel.playerTwoGraveyard, BorderLayout.EAST);
        panel.setPreferredSize(new Dimension(800, 600));
        panel.gameEngine = new ChessGameEngine(panel.gameBoard);
        return panel;
    }
}
