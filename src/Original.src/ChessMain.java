import javax.swing.*;
import javax.swing.WindowConstants;

public class ChessMain{
    private static ChessMain instance = null;

    private ChessMain() {}

    public static ChessMain getInstance() {
        if (instance == null) {
            instance = new ChessMain();
        }
        return instance;
    }
    
    public void startGame() {
        JFrame frame = new JFrame( "YetAnotherChessGame 1.0" );
        frame.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
        ChessPanel panel = ChessPanel.createChessPanel();
        frame.getContentPane().add( panel );
        frame.pack();
        frame.setVisible( true );
    }

    public static void main( String[] args ){
        ChessMain.getInstance().startGame();
    }
}
