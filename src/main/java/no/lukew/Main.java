package no.lukew;

import no.lukew.connect4.board.Board;

public class Main {
    static void main() {
        System.out.println("Hello World!");

        Board board = new Board();
        board.display();

        board.placePiece(0);
        board.display();
        board.placePiece(1);
        board.display();
    }
}
