package no.lukew;

import no.lukew.connect4.board.Board;
import no.lukew.connect4.board.PlacementResult;

import java.util.Scanner;

public class Main {
    static void main() {
        System.out.println("Hello World!");

        Board board = new Board();
        board.display();
        int input = 0;
        while(!board.isGameOver()){
            PlacementResult result = board.placePiece(input);

            if(result != PlacementResult.Success){
                System.out.println("Placement failed because: " + result.name());
            }

            if(!board.isGameOver()){
                board.display();
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
            }
        }
        board.display();
        System.out.println(board.getPieceWinner() + " won the game");
    }
}
