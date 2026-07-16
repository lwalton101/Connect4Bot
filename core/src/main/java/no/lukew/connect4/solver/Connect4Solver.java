package no.lukew.connect4.solver;

import no.lukew.connect4.board.Board;
import no.lukew.connect4.board.Piece;

public class Connect4Solver {

    public int positionsSearched = 0;
    private final int[] moveOrder = {3,2,4,1,5,0,6};

    public int score(Board board, Piece myPiece){
        positionsSearched += 1;
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

    private int negamax(Board board, int depth, int alpha, int beta){
        //if depth more than limit find score and return
        if(depth == 0 || board.isGameOver()){
            return score(board, board.getNextPiece());
        }

        int bestScore = Integer.MIN_VALUE;
        for (int j : moveOrder) {
            if (!board.canPlaceInColumn(j)) {
                continue;
            }
            Board newBoard = board.withMove(j);
            int score = -negamax(newBoard, depth - 1, -beta, -alpha);

            bestScore = Math.max(bestScore, score);
            alpha = Math.max(alpha, score);

            if(alpha >= beta){
                break;
            }
        }

        return bestScore;
    }

    public int[] evaluate(Board board){
        positionsSearched = 0;
        int[] scores = new int[Board.BOARD_WIDTH];
        for (int i = 0; i < Board.BOARD_WIDTH; i++) {
            if(!board.canPlaceInColumn(i)){
                continue;
            }
            Board newBoard = board.withMove(i);
            int score = -negamax(newBoard, 5, Integer.MIN_VALUE, Integer.MAX_VALUE);

            scores[i] = score;
        }

        return scores;
    }
}
