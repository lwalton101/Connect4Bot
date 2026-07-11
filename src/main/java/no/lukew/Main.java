package no.lukew;

import no.lukew.connect4.board.ArrayBoard;
import no.lukew.connect4.board.PlacementResult;

import java.util.Scanner;

public class Main {
    static void main() {
        System.out.println("Hello World!");

        ArrayBoard board = new ArrayBoard();
        board.display();

        while(!board.isGameOver()){
            int input = -1;
            if(!board.isGameOver()){
                board.display();
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
            }

            PlacementResult result = board.placePiece(input);

            if(result != PlacementResult.Success){
                System.out.println("Placement failed because: " + result.name());
            }
        }
        board.display();
        System.out.println(board.getPieceWinner() + " won the game");
    }
}
