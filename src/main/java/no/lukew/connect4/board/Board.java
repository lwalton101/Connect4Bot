package no.lukew.connect4.board;

import java.util.function.Supplier;

public abstract class Board {
    public static final int BOARD_WIDTH = 7;
    public static final int BOARD_HEIGHT = 6;
    public int[] moveHistory = new int[BOARD_WIDTH * BOARD_HEIGHT];
    private int moveCount = 0;
    private boolean gameWon = false;

    public Board(){

    }

    protected Board(Board other) {
        System.arraycopy(other.moveHistory, 0, moveHistory, 0, other.moveHistory.length);
        moveCount = other.moveCount;
        gameWon = other.gameWon;
    }
    public static <B extends Board> B fromNotation(Supplier<B> emptyBoardFactory, String notation) {
        B board = emptyBoardFactory.get();
        for (char move : notation.toCharArray()) {
            int column = Character.getNumericValue(move);
            if (board.placePiece(column) != PlacementResult.Success) {
                throw new IllegalArgumentException("Invalid notation at move: " + move);
            }
        }
        return board;
    }

    public final PlacementResult placePiece(int columnIndex) {
        if (isGameOver()) {
            return PlacementResult.GameOver;
        }

        if(columnIndex < 0 || columnIndex >= BOARD_WIDTH){
            return PlacementResult.InvalidColumn;
        }

        if (!canPlaceInColumn(columnIndex)) {
            return PlacementResult.ColumnFull;
        }

        if (doesPieceWin(columnIndex)) {
            gameWon = true;
        }
        dropPiece(columnIndex, getNextPiece());

        moveHistory[moveCount++] = columnIndex;

        return PlacementResult.Success;
    }

    public final int getNumberOfMoves() {
        return moveCount;
    }
    public final Piece getNextPiece() {
        return moveCount % 2 == 0 ? Piece.ONE : Piece.TWO;
    }

    public final boolean isBoardFull() {
        return moveCount == BOARD_WIDTH * BOARD_HEIGHT;
    }

    public final boolean isGameOver() {
        return gameWon || isBoardFull();
    }

    public final String toNotation() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < moveCount; i++) {
            sb.append(moveHistory[i]);
        }
        return sb.toString();
    }
    /**
     * Outputs a quick debug view of the board to stdout
     * for debugging, or if using without making your own UI.
     */
    public final String toDebugString() {
        StringBuilder boardString = new StringBuilder();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                boardString.append(getPiece(x, y).toChar());
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }

    /**
     * Gets the winning piece.
     *
     * @return the winning player's piece, or Piece.NONE if there is no winner
     */
    public final Piece getWinner(){
        return gameWon ? getNextPiece().opposite() : Piece.NONE;
    }

    //IMPLEMENTATION SPECIFIC
    protected abstract void dropPiece(int columnIndex, Piece piece);
    public abstract boolean canPlaceInColumn(int columnIndex);
    public abstract Piece getPiece(int x, int y);
    public abstract boolean doesPieceWin(int columnIndex);
    public abstract Board withMove(int columnIndex);
}
