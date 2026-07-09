package no.lukew.connect4.board;

public class Board {
    private Piece[][] boardState = new Piece[7][7];

    public Board(){
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                boardState[x][y] = Piece.NONE;
            }
        }
    }

    public void display(){

    }
}
