package no.lukew.connect4.board;

public abstract class Board {
    int BOARD_WIDTH = 7;
    int BOARD_HEIGHT = 6;

    public Board(){

    }

    public Board(String notation){
        for (char move : notation.toCharArray()) {
            PlacementResult result = placePiece(Character.getNumericValue(move));
            if (result != PlacementResult.Success) {
                throw new IllegalArgumentException("Invalid notation at move: " + move);
            }
        }
    }

    public abstract boolean isGameOver();
    public abstract boolean isBoardFull();
    public abstract boolean canPlaceInColumn(int columnIndex);
    public abstract PlacementResult placePiece(int columnIndex);
    public abstract int getNumberOfMoves();
    /**
     * Gets the winning piece.
     *
     * @return the winning player's piece, or Piece.NONE if there is no winner
     */
    public abstract Piece getWinner();
    public abstract boolean doesPieceWin(int columnIndex);
    public abstract String toNotation();
    /**
     * Outputs a quick debug view of the board to stdout
     * for debugging, or if using without making your own UI.
     */
    public abstract String toDebugString();
}
