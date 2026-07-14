package no.lukew.connect4.board;

public class ArrayBoardTest extends BoardTest{
    @Override
    protected Board createBoard() {
        return new ArrayBoard();
    }


}
