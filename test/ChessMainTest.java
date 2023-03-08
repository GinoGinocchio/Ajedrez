import org.junit.Test;
import static org.junit.Assert.*;


public class ChessMainTest {
    
    @Test
    public void testGetInstance() {
        ChessMain instance1 = ChessMain.getInstance();
        ChessMain instance2 = ChessMain.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testStartGame() {
        ChessMain instance = ChessMain.getInstance();
        instance.startGame();
        // TODO: assert something meaningful here
    }

    @Test
    public void testChessPanel() {
        // Verificar que se cree un panel de ajedrez correctamente
        ChessPanel panel = ChessPanel.createChessPanel();
        assertNotNull(panel);
    }
}
