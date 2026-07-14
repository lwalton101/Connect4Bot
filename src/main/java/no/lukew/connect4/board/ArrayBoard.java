package no.lukew.connect4.board;

import java.util.Arrays;

public class ArrayBoard extends Board {
    private final Piece[][] boardState;

    /**
     * Creates an empty Connect Four board ready for play.
     */
    public ArrayBoard() {
        boardState = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
        for (Piece[] column : boardState) {
            Arrays.fill(column, Piece.NONE);
        }
    }

    private ArrayBoard(ArrayBoard other) {
        super(other);

        boardState = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
        for (int x = 0; x < BOARD_WIDTH; x++) {
            boardState[x] = other.boardState[x].clone();
        }
    }

    @Override
    protected void dropPiece(int columnIndex, Piece piece) {
        int rowIndex = getNextPlaceInColumn(columnIndex);
        boardState[columnIndex][rowIndex] = piece;
    }

    @Override
    public boolean canPlaceInColumn(int columnIndex) {
        Piece[] column = boardState[columnIndex];
        return column[0] == Piece.NONE;
    }

    private int getNextPlaceInColumn(int columnIndex) {
        Piece[] column = boardState[columnIndex];
        int lastEmptyIndex = 0;
        for (int i = 0; i < column.length; i++) {
            if (column[i] == Piece.NONE) {
                lastEmptyIndex = i;
            }
        }

        return lastEmptyIndex;
    }

    @Override
    public Piece getPiece(int x, int y) {
        return boardState[x][y];
    }

    @Override
    public boolean doesPieceWin(int columnIndex) {
        Piece piece = getNextPiece();
        int rowIndex = getNextPlaceInColumn(columnIndex);
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

            count += countPieces(columnIndex, rowIndex, direction[0], direction[1], piece);

            count += countPieces(columnIndex, rowIndex, -direction[0], -direction[1], piece);

            if (count >= 4) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Board withMove(int columnIndex) {
        ArrayBoard copy = new ArrayBoard(this);
        copy.placePiece(columnIndex);
        return copy;
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


}
