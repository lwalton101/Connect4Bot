package no.lukew.connect4.board;

public class Board {
    private Piece[][] boardState;

    private final int BOARD_WIDTH = 7;
    private final int BOARD_HEIGHT = 6;

    public Board() {
        boardState = new Piece[BOARD_WIDTH][BOARD_HEIGHT];
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                setPiece(x, y, Piece.NONE);
            }
        }

        setPiece(3, 0, Piece.ONE);
    }

    private Piece getPiece(int x, int y) {
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
                rowString.append(getPiece(x, y).toChar());
            }
            rowString.append("\n");
            boardString.append(rowString);
        }
        System.out.println(boardString);
    }
}
