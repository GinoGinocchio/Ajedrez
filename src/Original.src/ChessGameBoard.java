import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridLayout;
// -------------------------------------------------------------------------
/**
 * The panel that represents the Chess game board. Contains a few methods that
 * allow other classes to access the physical board.
 *
 * @author Ben Katz (bakatz)
 * @author Myles David II (davidmm2)
 * @author Danielle Bushrow (dbushrow)
 * @version 2010.11.17
 */
public class ChessGameBoard extends JPanel{
    private BoardSquare[][] chessCells;
    private transient BoardListener  listener;
    // ----------------------------------------------------------
    /**
     * Returns the entire board.
     *
     * @return BoardSquare[][] the chess board
     */
    public BoardSquare[][] getCells(){
        return chessCells;
    }
    /**
     * Checks to make sure row and column are valid indices.
     * @param row the row to check
     * @param col the column to check
     * @return boolean true if they are valid, false otherwise
     */
    private boolean validateCoordinates( int row, int col ){
        return chessCells.length > 0 && chessCells[0].length > 0 &&
            row < chessCells.length && col < chessCells[0].length
            && row >= 0 && col >= 0;
    }
    // ----------------------------------------------------------
    /**
     * Gets the BoardSquare at row 'row' and column 'col'.
     * @param row the row to look at
     * @param col the column to look at
     * @return BoardSquare the square found, or null if it does not exist
     */
    public BoardSquare getCell( int row, int col ){
        if ( validateCoordinates( row, col ) ){
            return chessCells[row][col];
        }
        return null;
    }
    // ----------------------------------------------------------
    /**
     * Clears the cell at 'row', 'col'.
     * @param row the row to look at
     * @param col the column to look at
     */
    public void clearCell(int row, int col){
        if ( validateCoordinates( row, col ) ){
            chessCells[row][col].clearSquare();
        }
        else
        {
            throw new IllegalStateException( "Row " + row + " and column" +
            		" " + col + " are invalid, or the board has not been" +
            				"initialized. This square cannot be cleared." );
        }
    }
    // ----------------------------------------------------------
    /**
     * Gets all the white game pieces on the board.
     *
     * @return ArrayList<GamePiece> the pieces
     */
    public ArrayList<ChessGamePiece> getAllWhitePieces(){
        ArrayList<ChessGamePiece> whitePieces = new ArrayList<ChessGamePiece>();
        for ( int i = 0; i < 8; i++ ){
            for ( int j = 0; j < 8; j++ ){
                if ( chessCells[i][j].getPieceOnSquare() != null
                    && chessCells[i][j].getPieceOnSquare().getColorOfPiece() ==
                        ChessGamePiece.WHITE ){
                    whitePieces.add( chessCells[i][j].getPieceOnSquare() );
                }
            }
        }
        return whitePieces;
    }
    // ----------------------------------------------------------
    /**
     * Gets all the black pieces on the board
     *
     * @return ArrayList<GamePiece> the pieces
     */
    public ArrayList<ChessGamePiece> getAllBlackPieces(){
        ArrayList<ChessGamePiece> blackPieces = new ArrayList<ChessGamePiece>();
        for ( int i = 0; i < 8; i++ ){
            for ( int j = 0; j < 8; j++ ){
                if ( chessCells[i][j].getPieceOnSquare() != null
                    && chessCells[i][j].getPieceOnSquare().getColorOfPiece() ==
                        ChessGamePiece.BLACK ){
                    blackPieces.add( chessCells[i][j].getPieceOnSquare() );
                }
            }
        }
        return blackPieces;
    }
    // ----------------------------------------------------------
    /**
     * Create a new ChessGameBoard object.
     */
    public ChessGameBoard(){
        this.setLayout( new GridLayout( 8, 8, 1, 1 ) );
        listener = new BoardListener();
        chessCells = new BoardSquare[8][8];
        initializeBoard();
    }
    // ----------------------------------------------------------
    /**
     * Clears the board of all items, including any pieces left over in the
     * graveyard, and all old game logs.
     * @param addAfterReset if true, the board will add the BoardSquares
     * back to the board, if false it will simply reset everything and leave
     * the board blank.
     */
    public void resetBoard ( boolean addAfterReset ){
        chessCells = new BoardSquare[8][8];
        this.removeAll();
        if ( getParent() instanceof ChessPanel ){
            ( (ChessPanel)getParent() ).getGraveyard( 1 ).clearGraveyard();
            ( (ChessPanel)getParent() ).getGraveyard( 2 ).clearGraveyard();
            ( (ChessPanel)getParent() ).getGameLog().clearLog();
        }
        for ( int i = 0; i < chessCells.length; i++ ){
            for ( int j = 0; j < chessCells[0].length; j++ ){
                chessCells[i][j] = new BoardSquare( i, j, null );
                if ( ( i + j ) % 2 == 0 ){
                    chessCells[i][j].setBackground( Color.WHITE );
                }
                else
                {
                    chessCells[i][j].setBackground( Color.BLACK );
                }
                if ( addAfterReset ){
                    chessCells[i][j].addMouseListener( listener );
                    this.add( chessCells[i][j] );
                }
            }
        }
        repaint();
    }
    
    public void initializeBoard(){
        resetBoard(false);
        for (int i = 0; i < chessCells.length; i++){
            for (int j = 0; j < chessCells[0].length; j++){
                ChessGamePiece pieceToAdd = createPiece(i, j);
                chessCells[i][j] = new BoardSquare(i, j, pieceToAdd);
                chessCells[i][j].setBackground((i + j) % 2 == 0 ? Color.WHITE : Color.BLACK);
                chessCells[i][j].addMouseListener(listener);
                this.add(chessCells[i][j]);
            }
        }
    }
    
    private ChessGamePiece createPiece(int i, int j) {
    ChessGamePiece pieceToAdd;
        switch (i) {
            case 1, 6 -> pieceToAdd = new Pawn(this, i, j, i == 1 ? ChessGamePiece.BLACK : ChessGamePiece.WHITE);
            case 0, 7 -> {
                int color = i == 0 ? ChessGamePiece.BLACK : ChessGamePiece.WHITE;
                pieceToAdd = switch (j) {
            case 0, 7 -> new Rook(this, i, j, color);
            case 1, 6 -> new Knight(this, i, j, color);
            case 2, 5 -> new Bishop(this, i, j, color);
            case 3 -> new King(this, i, j, color);
            default -> new Queen(this, i, j, color);
        };
            }
            default -> pieceToAdd = null;
        }
    return pieceToAdd;
}
    
    
    // ----------------------------------------------------------
    /**
     * Clears the colors on the board.
     */
    public void clearColorsOnBoard(){
        for ( int i = 0; i < chessCells.length; i++ ){
            for ( int j = 0; j < chessCells[0].length; j++ ){
                if ( ( i + j ) % 2 == 0 ){
                    chessCells[i][j].setBackground( Color.WHITE );
                }
                else
                {
                    chessCells[i][j].setBackground( Color.BLACK );
                }
            }
        }
    }
    /**
     * Listens for clicks on BoardSquares.
     *
     * @author Ben Katz (bakatz)
     * @author Danielle Bushrow (dbushrow)
     * @author Myles David (davidmm2)
     * @version 2010.11.16
     */
    private class BoardListener
        implements MouseListener
    {
        /**
         * Do an action when the left mouse button is clicked.
         *
         * @param e
         *            the event from the listener
         */
        public void mouseClicked( MouseEvent e ){
            if ( e.getButton() == MouseEvent.BUTTON1 &&
                getParent() instanceof ChessPanel ){
                ( (ChessPanel)getParent() ).getGameEngine()
                    .determineActionFromSquareClick( e );
            }
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         */
        public void mouseEntered( MouseEvent e ){ /* not used */
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         */
        public void mouseExited( MouseEvent e ){ /* not used */
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         */
        public void mousePressed( MouseEvent e ){ /* not used */
        }
        /**
         * Unused method.
         *
         * @param e
         *            the mouse event from the listener
         */
        public void mouseReleased( MouseEvent e ){ /* not used */
        }
    }
}
