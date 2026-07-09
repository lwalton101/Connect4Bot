package no.lukew.connect4.board;

public enum Piece {
    NONE,
    ONE,
    TWO;

    public char toChar(){
        return name().charAt(0);
    }
}
