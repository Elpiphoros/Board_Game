package boardgame.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    @Test
    void testMove() {
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
    void testIsGameComplete() {
        BoardGameModel model = new BoardGameModel();
        assertFalse(model.isGameComplete());

        // Test horizontal win [0][0]-[0][2]
        model.move(0, 0);
        model.move(0, 1);
        model.move(0, 2);
        assertTrue(model.isGameComplete());

        // Test vertical win  [0][1]-[2][0]
        model.move(0, 1);
        model.move(1, 0);
        model.move(2, 0);
        assertTrue(model.isGameComplete());

        // Test diagonal win [0][0]-[2][2]
        model.move(2, 0);
        model.move(1, 1);
        model.move(2, 2);
        assertTrue(model.isGameComplete());

        // Test diagonal win [2][0]-[0][2]
        model.move(1, 1);
        model.move(0, 2);
        assertTrue(model.isGameComplete());

        // Test no win
        model.move(1, 1);
        assertFalse(model.isGameComplete());
    }

    @Test
    void testPlayerExchange() {
        BoardGameModel model = new BoardGameModel();
        model.setPlayer("Jiang", "Binxu", "Jiang");
        model.playerExchange();
        assertEquals("Binxu", model.getCurrent_player());
        assertEquals(1, model.getOperationsOfPlayer1());

        model.playerExchange();
        assertEquals("Jiang", model.getCurrent_player());
        assertEquals(1, model.getOperationsOfPlayer2());

        model.playerExchange();
        assertEquals("Binxu", model.getCurrent_player());
        assertEquals(2, model.getOperationsOfPlayer1());
    }

    @Test
    void testToString() {
        BoardGameModel model = new BoardGameModel();
        String expect = "0 0 0 \n0 0 0 \n0 0 0 \n";
        assertEquals(expect, model.toString());
    }

    @Test
    void testMain() {
        BoardGameModel.main(new String[]{});
    }
}