import java.awt.Component;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
// -------------------------------------------------------------------------
/**
 * Represents the north menu-bar that contains various controls for the game.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class ChessMenuBar extends JMenuBar {

    private final Map<String, Command> commands = new HashMap<>();

    // ----------------------------------------------------------
    /**
     * Create a new ChessMenuBar object.
     */
    public ChessMenuBar() {
        addCommand("About", this::aboutHandler);
        addCommand("New game/restart", this::restartHandler);
        addCommand("Toggle game log", this::toggleGameLogHandler);
        addCommand("Exit", this::exitHandler);
        addCommand("Toggle graveyard", this::toggleGraveyardHandler);

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(createMenuItem("New game/restart"));
        fileMenu.add(createMenuItem("Exit"));
        add(fileMenu);

        JMenu optionsMenu = new JMenu("Options");
        optionsMenu.add(createMenuItem("Toggle graveyard"));
        optionsMenu.add(createMenuItem("Toggle game log"));
        add(optionsMenu);

        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(createMenuItem("About"));
        add(helpMenu);
    }

    private void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    private JMenuItem createMenuItem(String name) {
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(e -> {
            Command command = commands.get(name);
            if (command != null) {
                command.execute();
            }
        });
        return item;
    }

    private interface Command {
        void execute();
    }

    // ----------------------------------------------------------
    /**
     * Takes an appropriate action if the about button is clicked.
     */
    private void aboutHandler() {
        JOptionPane.showMessageDialog(
            this.getParent(),
            "YetAnotherChessGame v1.0 by:\nBen Katz\nMyles David\n"
                + "Danielle Bushrow\n\nFinal Project for CS2114 @ VT" );
    }

    /**
     * Takes an appropriate action if the restart button is clicked.
     */
    private void restartHandler() {
        ((ChessPanel)this.getParent()).getGameEngine().reset();
    }

    /**
     * Takes an appropriate action if the exit button is clicked.
     * Uses Tony Allevato's code for exiting a GUI app without System.exit()
     * calls.
     */
    private void exitHandler() {
        JOptionPane.showMessageDialog(this.getParent(), "Thanks for leaving, quitter! >:(");

        Component frame = this;
        if (frame instanceof JFrame jFrame) {
            jFrame.setVisible(false);
            jFrame.dispose();
        }
    }

    /**
     * Takes an appropriate action if the toggle graveyard button is clicked.
     */
    private void toggleGraveyardHandler() {
        ((ChessPanel)this.getParent()).getGraveyard(1).setVisible(
            !((ChessPanel)this.getParent()).getGraveyard(1).isVisible());
        ((ChessPanel)this.getParent()).getGraveyard(2).setVisible(
            !((ChessPanel)this.getParent()).getGraveyard(2).isVisible());
    }

    /**
     * Takes an appropriate action if the toggle game log button is clicked.
     */
    private void toggleGameLogHandler() {
        ((ChessPanel)this.getParent()).getGameLog().setVisible(
            !((ChessPanel)this.getParent()).getGameLog().isVisible());
        ((ChessPanel)this.getParent()).revalidate();
    }
}

