package no.lukew.connect4.board;

import org.junit.jupiter.api.Test;

public class ArrayBoardTest extends BoardTest{
    @Override
    protected Board createBoard() {
        return new ArrayBoard();
    }


}
