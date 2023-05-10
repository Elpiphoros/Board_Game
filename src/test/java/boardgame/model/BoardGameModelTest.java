package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    @Test
    void move() {
        BoardGameModel model = new BoardGameModel();

        assertEquals("0 0 0 \n0 0 0 \n0 0 0 \n", model.toString());

        model.move(0, 0);
        model.move(1, 1);
        model.move(2, 2);
        assertEquals("1 0 0 \n0 1 0 \n0 0 1 \n", model.toString());

        model.move(0, 0);
        model.move(1, 1);
        model.move(2, 2);
        assertEquals("2 0 0 \n0 2 0 \n0 0 2 \n", model.toString());

        model.move(0, 0);
        model.move(1, 1);
        model.move(2, 2);
        assertEquals("3 0 0 \n0 3 0 \n0 0 3 \n", model.toString());

        model.move(0, 0);
        model.move(1, 1);
        model.move(2, 2);
        assertEquals("0 0 0 \n0 0 0 \n0 0 0 \n", model.toString());
    }

    @Test
    void testToString() {
        BoardGameModel model = new BoardGameModel();
        String expect = "0 0 0 \n0 0 0 \n0 0 0 \n";
        assertEquals(expect, model.toString());
    }

    @Test
    void main() {
        BoardGameModel.main(new String[]{});
    }
}