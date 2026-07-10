package no.lukew.connect4.board;

import java.util.Arrays;

/**
 * Represents the state of a Connect Four game board.
 * The board manages piece placement, player turns,
 * win detection, and game completion states.
 * Board coordinates use zero-based indexing.
 * The first index represents the column and the second represents the row.
 */
public class ArrayBoard {
    private final Piece[][] boardState;

    private static final int BOARD_WIDTH = 7;
    private static final int BOARD_HEIGHT = 6;

    private boolean isGameOver = false;
    private Piece pieceWinner = Piece.NONE;

    private Piece nextPiece = Piece.ONE;

    /**
     * Creates an empty Connect Four board ready for play.
     */
    public ArrayBoard() {
        boardState = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
        for (Piece[] column : boardState) {
            Arrays.fill(column, Piece.NONE);
        }
    }

    private boolean doesPieceWin(int x, int y) {
        Piece piece = getPiece(x, y);
        if (piece == Piece.NONE) {
            return false;
        }

        int[][] directions = {
                {1, 0},  // horizontal
                {0, 1},  // vertical
                {1, 1},  // diagonal /
                {1, -1}  // diagonal \
        };

        for (int[] direction : directions) {
            int count = 1;

            count += countPieces(x, y, direction[0], direction[1], piece);

            count += countPieces(x, y, -direction[0], -direction[1], piece);

            if (count >= 4) {
                return true;
            }
        }

        return false;
    }

    private boolean isBoardFull(){
        for (Piece[] column : boardState) {
            if (Arrays.asList(column).contains(Piece.NONE)) {
                return false;
            }
        }

        return true;
    }

    private int countPieces(int x, int y, int xDirection, int yDirection, Piece piece) {
        int count = 0;

        int newX = x + xDirection;
        int newY = y + yDirection;

        while (newX >= 0 && newX < BOARD_WIDTH &&
                newY >= 0 && newY < BOARD_HEIGHT &&
                getPiece(newX, newY) == piece) {

            count++;
            newX += xDirection;
            newY += yDirection;
        }

        return count;
    }

    /**
     * Attempts to place the current player's piece into the selected column.
     * The piece is placed in the lowest available row in the column.
     * If the move results in a win or draw, the game will be marked as complete.
     *
     * @param columnIndex zero-based column index where the piece should be placed
     * @return the result of the placement attempt
     */
    public PlacementResult placePiece(int columnIndex) {
        if(isGameOver()){
            return PlacementResult.GameOver;
        }
        if (columnIndex < 0 || columnIndex > BOARD_WIDTH - 1) {
            return PlacementResult.InvalidColumn;
        }

        if (!canPlaceInColumn(columnIndex)) {
            return PlacementResult.ColumnFull;
        }

        int rowIndex = getNextPlaceInColumn(columnIndex);
        setPiece(columnIndex, rowIndex, nextPiece);


        if (doesPieceWin(columnIndex, rowIndex)) {
            isGameOver = true;
            pieceWinner = nextPiece;
            return PlacementResult.Success;
        }

        if(isBoardFull()){
            isGameOver = true;
            return PlacementResult.Success;
        }

        nextPiece = nextPiece.opposite();

        return PlacementResult.Success;
    }

    /**
     * Checks if the game has ended
     * @return true - if the game is over, false - if the game can still be played
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Gets the winning piece.
     *
     * @return the winning player's piece, or Piece.NONE if there is no winner
     */
    public Piece getPieceWinner() {
        return pieceWinner;
    }

    private boolean canPlaceInColumn(int columnIndex) {
        Piece[] column = getColumn(columnIndex);

        return column[0] == Piece.NONE;
    }

    private int getNextPlaceInColumn(int columnIndex) {
        Piece[] column = getColumn(columnIndex);
        int lastEmptyIndex = 0;
        for (int i = 0; i < column.length; i++) {
            if (column[i] == Piece.NONE) {
                lastEmptyIndex = i;
            }
        }

        return lastEmptyIndex;
    }

    private Piece[] getColumn(int column) {
        return boardState[column];
    }

    private Piece getPiece(int x, int y) {
        if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_HEIGHT){
            return null;
        }
        return boardState[x][y];
    }

    private void setPiece(int x, int y, Piece piece) {
        boardState[x][y] = piece;
    }

    /**
     * Outputs a quick debug view of the board to stdout
     * for debugging, or if using without making your own UI.
     */
    public void display() {
        StringBuilder boardString = new StringBuilder();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            StringBuilder rowString = new StringBuilder();
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Piece piece = getPiece(x, y);
                if (piece == null) {
                    System.out.println("Invalid Piece Found " + x + "," + y);
                    continue;
                }
                rowString.append(piece.toChar());
            }
            rowString.append("\n");
            boardString.append(rowString);
        }
        System.out.println(boardString);
    }
}
