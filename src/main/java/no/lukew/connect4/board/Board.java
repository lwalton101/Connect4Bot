package no.lukew.connect4.board;

public interface Board {
    int BOARD_WIDTH = 7;
    int BOARD_HEIGHT = 6;

    boolean isGameOver();
    boolean isBoardFull();
    boolean canPlaceInColumn(int columnIndex);
    PlacementResult placePiece(int columnIndex);
    int getNumberOfMoves();
    Piece getWinner();
    boolean doesPieceWin(int columnIndex);
    String toNotation();
    /**
     * Outputs a quick debug view of the board to stdout
     * for debugging, or if using without making your own UI.
     */
    String toDebugString();
}
