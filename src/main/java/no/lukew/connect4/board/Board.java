package no.lukew.connect4.board;

public class Board {
    private Piece[][] boardState;

    private final int BOARD_WIDTH = 7;
    private final int BOARD_HEIGHT = 6;

    private boolean isGameOver = false;
    private Piece pieceWinner = Piece.NONE;

    private Piece nextPiece = Piece.ONE;

    public Board() {
        boardState = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                setPiece(x, y, Piece.NONE);
            }
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

    public void placePiece(int columnIndex) {
        if(this.isGameOver()){
            return;
        }
        if (columnIndex > BOARD_WIDTH - 1) {
            System.out.println("Piece being placed in column " + columnIndex + " that is invalid");
            return;
        }

        if (!canPlaceInColumn(columnIndex)) {
            System.out.println("Piece being placed in column " + columnIndex + " that is full");
            return;
        }

        int rowIndex = getNextPlaceInColumn(columnIndex);
        setPiece(columnIndex, rowIndex, nextPiece);


        if (doesPieceWin(columnIndex, rowIndex)) {
            isGameOver = true;
            pieceWinner = nextPiece;
            return;
        }

        if (nextPiece == Piece.ONE) {
            nextPiece = Piece.TWO;
        } else if (nextPiece == Piece.TWO) {
            nextPiece = Piece.ONE;
        }
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
        if (x < 0 || x > BOARD_WIDTH || y < 0 || y > BOARD_HEIGHT) {
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
