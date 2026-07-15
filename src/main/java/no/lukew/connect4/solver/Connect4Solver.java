package no.lukew.connect4.solver;

import no.lukew.connect4.board.Board;
import no.lukew.connect4.board.Piece;

public class Connect4Solver {

    public int score(Board board, Piece myPiece){
        int score = 0;
        if(board.isGameOver()){
            if(board.getWinner() == myPiece){
                score += 1_000_000;
            } else if (board.getWinner() == myPiece.opposite()) {
                score -= 1_000_000;
            }

            return score;
        }

        int[][] windowCounts = board.getWindowCounts(myPiece);
        for (int[] windowCount : windowCounts) {
            int m = windowCount[0];
            int t = windowCount[1];

            score += evaluateWindow(m, t);
        }

        return score;
    }

    private int evaluateWindow(int mine, int enemy) {

        if (mine > 0 && enemy > 0)
            return 0;

        if (mine == 4)
            return 100000;

        if (mine == 3)
            return 100;

        if (mine == 2)
            return 10;

        if (enemy == 3)
            return -100;

        return 0;
    }

    private int negamax(Board board, int depth){
        //if depth more than limit find score and return
        if(depth == 0 || board.isGameOver()){
            return score(board, board.getNextPiece());
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < Board.BOARD_WIDTH; i++) {
            if(!board.canPlaceInColumn(i)){
                continue;
            }
            Board newBoard = board.withMove(i);
            int score = -negamax(newBoard, depth - 1);

            if(score > max){
                max = score;
            }
        }

        return max;
    }

    public int[] evaluate(Board board){
        int[] scores = new int[Board.BOARD_WIDTH];
        for (int i = 0; i < Board.BOARD_WIDTH; i++) {
            if(!board.canPlaceInColumn(i)){
                continue;
            }
            Board newBoard = board.withMove(i);
            int score = -negamax(newBoard, 5);

            scores[i] = score;
        }

        return scores;
    }
}
