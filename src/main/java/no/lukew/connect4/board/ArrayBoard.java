package no.lukew.connect4.board;

import java.util.Arrays;

/**
 * Represents the state of a Connect Four game board.
 * The board manages piece placement, player turns,
 * win detection, and game completion states.
 * Board coordinates use zero-based indexing.
 * The first index represents the column and the second represents the row.
 */
public class ArrayBoard extends Board {
    private final Piece[][] boardState;
    private int[] moves;
    Piece nextPiece = Piece.ONE;
    boolean gameWon = false;

    int moveCount = 0;
    /**
     * Creates an empty Connect Four board ready for play.
     */
    public ArrayBoard() {
        boardState = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
        for (Piece[] column : boardState) {
            Arrays.fill(column, Piece.NONE);
        }

        moves = new int[BOARD_WIDTH * BOARD_HEIGHT];
        Arrays.fill(moves, -1);
    }

    public ArrayBoard(String notation){
        boardState = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
        for (Piece[] column : boardState) {
            Arrays.fill(column, Piece.NONE);
        }

        this.moves = new int[BOARD_WIDTH * BOARD_HEIGHT];
        Arrays.fill(this.moves, -1);

        for (char move: notation.toCharArray()){
            placePiece(Character.getNumericValue(move));
        }
    }

    @Override
    public boolean doesPieceWin(int columnIndex) {

        Piece piece = nextPiece;
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
    public boolean isBoardFull(){
        for (Piece[] column : boardState) {
            if (Arrays.asList(column).contains(Piece.NONE)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toNotation() {
        StringBuilder notationBuilder = new StringBuilder();
        for(int move : moves){
            if(move == -1){
                break;
            }
            notationBuilder.append(move);
        }
        return notationBuilder.toString();
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

        if(doesPieceWin(columnIndex)){
            gameWon = true;
        }

        int rowIndex = getNextPlaceInColumn(columnIndex);
        setPiece(columnIndex, rowIndex, nextPiece);

        moves[moveCount] = columnIndex;
        moveCount++;

        nextPiece = nextPiece.opposite();

        return PlacementResult.Success;
    }

    @Override
    public int getNumberOfMoves() {
        return moveCount;
    }

    @Override
    public Piece getWinner() {
        return nextPiece.opposite();
    }

    /**
     * Checks if the game has ended
     * @return true - if the game is over, false - if the game can still be played
     */
    public boolean isGameOver() {
        if(isBoardFull()){
            return true;
        }

        return gameWon;
    }
    public boolean canPlaceInColumn(int columnIndex) {
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


    public String toDebugString() {
        StringBuilder boardString = new StringBuilder();
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            StringBuilder rowString = new StringBuilder();
            for (int x = 0; x < BOARD_WIDTH; x++) {
                Piece piece = getPiece(x, y);
                if (piece == null) {
                    continue;
                }
                rowString.append(piece.toChar());
            }
            rowString.append("\n");
            boardString.append(rowString);
        }
        return boardString.toString();
    }
}
