package no.lukew.connect4.board;

public enum Piece {
    NONE,
    ONE,
    TWO;

    public char toChar(){
        return name().charAt(0);
    }

    public Piece opposite() {
        return switch (this) {
            case ONE -> TWO;
            case TWO -> ONE;
            case NONE -> NONE;
        };
    }
}
