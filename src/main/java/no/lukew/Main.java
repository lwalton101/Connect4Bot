package no.lukew;

import no.lukew.connect4.board.Board;

import java.util.Scanner;

public class Main {
    static void main() {
        System.out.println("Hello World!");

        Board board = new Board();
        board.display();
        while(!board.isGameOver()){
            board.placePiece(0);
            board.placePiece(1);
            board.placePiece(2);
            board.placePiece(3);
            if(!board.isGameOver()){
                board.display();
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
            }

        }
        board.display();
        System.out.println(board.getPieceWinner() + " won the game");
    }
}
