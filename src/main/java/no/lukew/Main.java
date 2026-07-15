package no.lukew;

import no.lukew.connect4.board.ArrayBoard;
import no.lukew.connect4.board.Board;
import no.lukew.connect4.board.Piece;
import no.lukew.connect4.board.PlacementResult;
import no.lukew.connect4.solver.Connect4Solver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static void main(String[] args) {
        System.out.println("Hello World!");

        if(Arrays.asList(args).contains("--generate")){
            generateGames();
            System.exit(0);
        }

        ArrayBoard board = new ArrayBoard();

        while(!board.isGameOver()){
            System.out.println(board.toDebugString());
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            PlacementResult result = board.placePiece(input);
            if(result != PlacementResult.Success){
                System.out.println("Placement failed because: " + result.name());
            }

            Connect4Solver solver = new Connect4Solver();

            StringBuilder scoreString = new StringBuilder();
            for (int i = 0; i < Board.BOARD_WIDTH; i++) {
                if(!board.canPlaceInColumn(i)){
                    continue;
                }

                Board newBoard = board.withMove(i);
                scoreString.append(solver.score(newBoard, Piece.TWO)).append(",");
            }
            System.out.println(scoreString);

            int[] scores = solver.evaluate(board);
            int bestColumn = 0;
            for (int i = 1; i < scores.length; i++) {
                if (scores[i] > scores[bestColumn]) {
                    bestColumn = i;
                }
            }
            board.placePiece(bestColumn);

            System.out.println("Searched " + solver.positionsSearched + " positions");
        }
        System.out.println(board.toDebugString());
        System.out.println(board.toNotation());
        System.out.println(board.getWinner() + " won the game");
    }

    private static void generateGames() {
        HashMap<String, Piece> gameResults = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            Board board = new ArrayBoard();
            while(!board.isGameOver()){
                int input = random.nextInt(0, Board.BOARD_WIDTH);
                board.placePiece(input);
            }

            gameResults.put(board.toNotation(), board.getWinner());
        }

        try {
            FileWriter myWriter = new FileWriter("games.csv");
            myWriter.write("notation,winner\n");
            for (String notationKey : gameResults.keySet()){
                myWriter.write(notationKey + "," + gameResults.get(notationKey).ordinal() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
