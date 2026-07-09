package no.lukew.connect4.board;

import java.util.Arrays;

public class Board {
    private final Piece[][] boardState;

    private static final int BOARD_WIDTH = 7;
    private static final int BOARD_HEIGHT = 6;

    private boolean isGameOver = false;
    private Piece pieceWinner = Piece.NONE;

    private Piece nextPiece = Piece.ONE;

    public Board() {
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

    public boolean isGameOver() {
        return isGameOver;
    }

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
