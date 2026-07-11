package no.lukew;

import no.lukew.connect4.board.ArrayBoard;
import no.lukew.connect4.board.PlacementResult;

import java.util.Scanner;

public class Main {
    static void main() {
        System.out.println("Hello World!");

        ArrayBoard board = new ArrayBoard();

        while(!board.isGameOver()){
            int input = -1;
            if(!board.isGameOver()){
                System.out.println(board.toDebugString());
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
            }

            PlacementResult result = board.placePiece(input);

            if(result != PlacementResult.Success){
                System.out.println("Placement failed because: " + result.name());
            }
        }
        System.out.println(board.toDebugString());
        System.out.println(board.getWinner() + " won the game");
    }
}
