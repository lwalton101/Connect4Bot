package no.lukew.connect4.board;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
abstract class BoardTest {
    protected abstract Board createBoard();
    @Test
    void newBoardHasNoMoves(){
        Board board = createBoard();

        assertEquals(0, board.getNumberOfMoves());
    }
    @Test
    void newBoardGameNotOver(){
        Board board = createBoard();

        assertFalse(board.isGameOver());
    }
    @Test
    void newBoardNotFull(){
        Board board = createBoard();

        assertFalse(board.isBoardFull());
    }
    @Test
    void newBoardPlayerOneStarts(){
        Board board = createBoard();

        assertEquals(Piece.ONE, board.getNextPiece());
    }

    @Test
    void newBoardHasNoWinner(){
        Board board = createBoard();

        assertEquals(Piece.NONE, board.getWinner());
    }

    @Test
    void placePieceInEmptyColumnSucceeds(){
        Board board = createBoard();
        for (int i = 0; i < Board.BOARD_WIDTH; i++) {
            assertEquals(PlacementResult.Success, board.placePiece(i));
        }
    }

    @Test
    void placePieceInColumnLessThanZeroFails(){
        Board board = createBoard();
        assertEquals(PlacementResult.InvalidColumn, board.placePiece(-1));
    }

    @Test
    void placePieceInColumnMoreThanWidthFails(){
        Board board = createBoard();
        assertEquals(PlacementResult.InvalidColumn, board.placePiece(Board.BOARD_WIDTH));
    }

    @Test
    void placePieceIncreasesMoveCount(){
        Board board = createBoard();
        board.placePiece(0);
        assertEquals(1, board.getNumberOfMoves());
    }

    @Test
    void placePieceAlternatesPlayers(){
        Board board = createBoard();

        board.placePiece(0);

        assertEquals(Piece.TWO, board.getNextPiece());
        board.placePiece(0);
        assertEquals(Piece.ONE, board.getNextPiece());
    }

    @Test
    void placePieceChangesMoveHistory(){
        Board board = createBoard();
        board.placePiece(1);

        assertEquals(1, board.moveHistory[0]);
    }

    @Test
    void placePieceStacksCorrectly(){
        Board board = createBoard();
        board.placePiece(0);
        board.placePiece(0);

        assertEquals(Piece.ONE, board.getPiece(0,Board.BOARD_HEIGHT - 1));
        assertEquals(Piece.TWO, board.getPiece(0,Board.BOARD_HEIGHT - 2));
    }

    @Test
    void placePieceColumnFullWorks(){
        Board board = createBoard();

        for (int i = 0; i < Board.BOARD_HEIGHT; i++) {
            board.placePiece(0);
        }

        assertEquals(PlacementResult.ColumnFull, board.placePiece(0));
    }

    @Test
    void doesDrawWork(){
        Board board = createBoard();

        String drawNotation = "350141550066002260552521162244446333463131";
        for (char notationChar : drawNotation.toCharArray()){
            board.placePiece(Character.getNumericValue(notationChar));
        }

        assertTrue(board.isGameOver());
        assertEquals(Piece.NONE, board.getWinner());
    }

    @Test
    void doesRightDiagonalWin(){
        Board board = createBoard();

        String drawNotation = "01122323363";
        for (char notationChar : drawNotation.toCharArray()){
            board.placePiece(Character.getNumericValue(notationChar));
        }

        assertTrue(board.isGameOver());
        assertEquals(Piece.ONE, board.getWinner());
    }

    @Test
    void doesLeftDiagonalWin(){
        Board board = createBoard();

        String drawNotation = "54453332222";
        for (char notationChar : drawNotation.toCharArray()){
            board.placePiece(Character.getNumericValue(notationChar));
        }

        assertTrue(board.isGameOver());
        assertEquals(Piece.ONE, board.getWinner());
    }

    @Test
    void doesColumnWin(){
        Board board = createBoard();

        for (int i = 0; i < 4; i++) {
            board.placePiece(0);
            board.placePiece(1);
        }

        assertTrue(board.isGameOver());
        assertEquals(Piece.ONE, board.getWinner());
    }

    @Test
    void doesRowWin(){
        Board board = createBoard();

        for (int i = 0; i < 4; i++) {
            board.placePiece(i);
            board.placePiece(i);
        }

        assertTrue(board.isGameOver());
        assertEquals(Piece.ONE, board.getWinner());
    }

    @Test
    void doesTestSetSucceed(){
        File testSet = new File("src/test/resources/games.csv");
        try {
            try (Stream<String> lines = Files.lines(testSet.toPath())) {
                List<List<String>> records = lines.map(line -> Arrays.asList(line.split(",")))
                        .skip(1).toList();

                for (List<String> record : records){
                    String notation = record.get(0);
                    int winningPieceIndex = Integer.parseInt(record.get(1));
                    Piece winningPiece = Piece.values()[winningPieceIndex];

                    Board board = createBoard();

                    for (char column : notation.toCharArray()){
                        board.placePiece(Character.getNumericValue(column));
                    }

                    assertTrue(board.isGameOver());
                    assertEquals(winningPiece, board.getWinner());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
