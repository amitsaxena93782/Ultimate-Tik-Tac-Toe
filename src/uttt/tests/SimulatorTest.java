package uttt.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

public class SimulatorTest {
    private SimulatorInterface simulator;
    private BoardInterface[] boards;

    @Before
    public void setUp() {
        simulator = UTTTFactory.createSimulator();
        boards = new BoardInterface[9];

        for (int i = 0; i < 9; i++) {
            boards[i] = UTTTFactory.createBoard();
        }
        simulator.setBoards(boards);
    }

    @Test
    public void testGetBoards() {
        // First checking on empty board:
        BoardInterface[] boards = simulator.getBoards();
        for (int i = 0; i < 9; i++) {
            MarkInterface[] marks = boards[i].getMarks();
            for (int j = 0; j < 9; j++) {
                assertEquals(Symbol.EMPTY, marks[j].getSymbol());
            }
        }

        // Now checking for an non-empty board
        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 0);

        // Now fetching the boards again:
        boards = simulator.getBoards();
        assertEquals(Symbol.CIRCLE, boards[0].getMarks()[0].getSymbol());

    }

    @Test
    public void testSetBoards() {
        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);

        boards[5].setMarkAt(Symbol.CIRCLE, 0);
        simulator.setBoards(boards);

        BoardInterface[] outcome = simulator.getBoards();
        assertEquals(9, outcome.length);
        assertEquals(Symbol.CIRCLE, outcome[5].getMarks()[0].getSymbol());

        assertThrows(IllegalArgumentException.class, () -> simulator.setBoards(new BoardInterface[10]));
    }

    @Test
    public void testCurrentSymbol() {

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);

        assertEquals(Symbol.CIRCLE, simulator.getCurrentPlayerSymbol());

        simulator.setCurrentPlayerSymbol(Symbol.CROSS);
        assertEquals(Symbol.CROSS, simulator.getCurrentPlayerSymbol());

    }

    @Test
    public void testSetMarkAt() {
        setUp();

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 4);
        assertEquals(Symbol.CIRCLE, simulator.getBoards()[0].getMarks()[4].getSymbol());
        assertThrows(IllegalArgumentException.class,
                () -> simulator.setMarkAt(Symbol.CROSS, 0, 0));

        simulator.setMarkAt(Symbol.CIRCLE, 4, 0);
        assertFalse(simulator.setMarkAt(Symbol.CIRCLE, 0, 4));

        assertTrue(simulator.setMarkAt(Symbol.CIRCLE, 4, 1));
        assertEquals(Symbol.CIRCLE, simulator.getBoards()[4].getMarks()[1].getSymbol());

        assertThrows(IllegalArgumentException.class,
                () -> simulator.setMarkAt(Symbol.CIRCLE, 2, 2));

        assertThrows(IllegalArgumentException.class, () -> simulator.setMarkAt(Symbol.CIRCLE, 9, 0));
        assertThrows(IllegalArgumentException.class, () -> simulator.setMarkAt(Symbol.CIRCLE, 0, 9));
        assertThrows(IllegalArgumentException.class, () -> simulator.setMarkAt(Symbol.CIRCLE, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> simulator.setMarkAt(Symbol.CIRCLE, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> simulator.setMarkAt(null, 4, 5));
    }

    @Test
    public void testIndex() {
        simulator.setIndexNextBoard(3);
        int outcome = simulator.getIndexNextBoard();
        assertNotNull(outcome);
        assertEquals(3, outcome);
        assertThrows(IllegalArgumentException.class, () -> simulator.setIndexNextBoard(9));
    }

    @Test
    public void testIsGameOver() {

        BoardInterface[] newBoards = new BoardInterface[9];
        for (int i = 0; i < 9; i++) {
            newBoards[i] = UTTTFactory.createBoard();
        }
        simulator.setBoards(newBoards);

        // Testing on empty boards
        assertFalse(simulator.isGameOver());

        // Winning the 1st board with circle

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 1);
        simulator.setMarkAt(Symbol.CIRCLE, 1, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 2);

        // But since other boards are empty, game isn't over yet
        assertFalse(simulator.isGameOver());

        // Now making the boards empty
        setUp();

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        // Now if Circle wins 3 boards diagonally
        simulator.setMarkAt(Symbol.CIRCLE, 0, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 1);
        simulator.setMarkAt(Symbol.CIRCLE, 1, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 2);

        simulator.setMarkAt(Symbol.CIRCLE, 2, 4);

        simulator.setMarkAt(Symbol.CIRCLE, 4, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 4);
        simulator.setMarkAt(Symbol.CIRCLE, 4, 1);
        simulator.setMarkAt(Symbol.CIRCLE, 1, 4);
        simulator.setMarkAt(Symbol.CIRCLE, 4, 2);

        simulator.setMarkAt(Symbol.CIRCLE, 2, 8);

        simulator.setMarkAt(Symbol.CIRCLE, 8, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 8);
        simulator.setMarkAt(Symbol.CIRCLE, 8, 1);
        simulator.setMarkAt(Symbol.CIRCLE, 1, 8);
        simulator.setMarkAt(Symbol.CIRCLE, 8, 2);

        assertTrue(simulator.isGameOver());

        // Now again reset
        setUp();

        simulator.setCurrentPlayerSymbol(Symbol.CROSS);
        // Now if circle wins 3 boards diagonally with Cross:

        simulator.setMarkAt(Symbol.CROSS, 0, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 2);

        simulator.setMarkAt(Symbol.CROSS, 2, 4);

        simulator.setMarkAt(Symbol.CROSS, 4, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 4);
        simulator.setMarkAt(Symbol.CROSS, 4, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 4);
        simulator.setMarkAt(Symbol.CROSS, 4, 2);

        simulator.setMarkAt(Symbol.CROSS, 2, 8);

        simulator.setMarkAt(Symbol.CROSS, 8, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 8);
        simulator.setMarkAt(Symbol.CROSS, 8, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 8);
        simulator.setMarkAt(Symbol.CROSS, 8, 2);

        assertTrue(simulator.isGameOver());

        // Reset
        setUp();

        // Now winning a row with Circle:
        simulator.setCurrentPlayerSymbol(Symbol.CROSS);

        simulator.setMarkAt(Symbol.CROSS, 0, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 2);

        simulator.setMarkAt(Symbol.CROSS, 2, 1);

        simulator.setMarkAt(Symbol.CROSS, 1, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 2);

        simulator.setMarkAt(Symbol.CROSS, 2, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 3);
        simulator.setMarkAt(Symbol.CROSS, 3, 2);
        simulator.setMarkAt(Symbol.CROSS, 2, 2);

        assertTrue(simulator.isGameOver());

        // Reset
        setUp();

        // Winning the boards in a column with Circle:
        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);

        simulator.setMarkAt(Symbol.CIRCLE, 0, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 1);
        simulator.setMarkAt(Symbol.CIRCLE, 1, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 2);

        simulator.setMarkAt(Symbol.CIRCLE, 2, 3);

        simulator.setMarkAt(Symbol.CIRCLE, 3, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 3);
        simulator.setMarkAt(Symbol.CIRCLE, 3, 4);
        simulator.setMarkAt(Symbol.CIRCLE, 4, 3);
        simulator.setMarkAt(Symbol.CIRCLE, 3, 8);

        simulator.setMarkAt(Symbol.CIRCLE, 8, 6);

        simulator.setMarkAt(Symbol.CIRCLE, 6, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 6);
        simulator.setMarkAt(Symbol.CIRCLE, 6, 3);
        simulator.setMarkAt(Symbol.CIRCLE, 3, 6);
        simulator.setMarkAt(Symbol.CIRCLE, 6, 6);

        assertTrue(simulator.isGameOver());
    }

    @Test
    public void testIsMovePossible() {
        setUp();

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);

        simulator.setMarkAt(Symbol.CIRCLE, 2, 0);
        assertTrue(simulator.isMovePossible(0));
        assertTrue(simulator.isMovePossible(0, 4));

        assertFalse(simulator.isMovePossible(4));
        assertFalse(simulator.isMovePossible(4, 4));

        simulator.setMarkAt(Symbol.CIRCLE, 0, 4);
        assertTrue(simulator.isMovePossible(4));
        assertTrue(simulator.isMovePossible(4, 4));

        assertFalse(simulator.isMovePossible(0));
        assertFalse(simulator.isMovePossible(0, 6));

        assertThrows(IllegalArgumentException.class, () -> simulator.isMovePossible(10));
        assertThrows(IllegalArgumentException.class, () -> simulator.isMovePossible(0, 10));
        assertThrows(IllegalArgumentException.class, () -> simulator.isMovePossible(10, 0));
        assertThrows(IllegalArgumentException.class, () -> simulator.isMovePossible(10, 10));
    }

    @Test
    public void testGetWinner() {

        assertEquals(Symbol.EMPTY, simulator.getWinner());

        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        // We'll win the 1st board with Circle:

        simulator.setMarkAt(Symbol.CIRCLE, 0, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 1);
        simulator.setMarkAt(Symbol.CIRCLE, 1, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 2);

        // But as other boards are empty, there should be no winner yet!
        assertEquals(Symbol.EMPTY, simulator.getWinner());

        simulator.setMarkAt(Symbol.CIRCLE, 2, 4);

        // Now winning the other boards diagonally with Circles
        simulator.setMarkAt(Symbol.CIRCLE, 4, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 4);
        simulator.setMarkAt(Symbol.CIRCLE, 4, 1);
        simulator.setMarkAt(Symbol.CIRCLE, 1, 4);
        simulator.setMarkAt(Symbol.CIRCLE, 4, 2);

        simulator.setMarkAt(Symbol.CIRCLE, 2, 8);

        simulator.setMarkAt(Symbol.CIRCLE, 8, 0);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 8);
        simulator.setMarkAt(Symbol.CIRCLE, 8, 3);
        simulator.setMarkAt(Symbol.CIRCLE, 3, 8);
        simulator.setMarkAt(Symbol.CIRCLE, 8, 6);

        // Now Circle is the winner!
        assertEquals(Symbol.CIRCLE, simulator.getWinner());

        // Reset
        setUp();
        // Now winning the game with cross diagonally:
        simulator.setCurrentPlayerSymbol(Symbol.CROSS);

        simulator.setMarkAt(Symbol.CROSS, 0, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 2);

        simulator.setMarkAt(Symbol.CROSS, 2, 4);

        simulator.setMarkAt(Symbol.CROSS, 4, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 4);
        simulator.setMarkAt(Symbol.CROSS, 4, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 4);
        simulator.setMarkAt(Symbol.CROSS, 4, 2);

        simulator.setMarkAt(Symbol.CROSS, 2, 8);

        simulator.setMarkAt(Symbol.CROSS, 8, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 8);
        simulator.setMarkAt(Symbol.CROSS, 8, 3);
        simulator.setMarkAt(Symbol.CROSS, 3, 8);
        simulator.setMarkAt(Symbol.CROSS, 8, 6);

        assertEquals(Symbol.CROSS, simulator.getWinner());

        // Reset
        setUp();

        // Now winning 3 boards, but neither in a row, column nor diagonally
        // With cross:
        simulator.setCurrentPlayerSymbol(Symbol.CROSS);

        simulator.setMarkAt(Symbol.CROSS, 0, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 2);

        simulator.setMarkAt(Symbol.CROSS, 2, 4);

        simulator.setMarkAt(Symbol.CROSS, 4, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 4);
        simulator.setMarkAt(Symbol.CROSS, 4, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 4);
        simulator.setMarkAt(Symbol.CROSS, 4, 2);

        simulator.setMarkAt(Symbol.CROSS, 2, 7);

        simulator.setMarkAt(Symbol.CROSS, 7, 0);
        simulator.setMarkAt(Symbol.CROSS, 0, 7);
        simulator.setMarkAt(Symbol.CROSS, 7, 1);
        simulator.setMarkAt(Symbol.CROSS, 1, 7);
        simulator.setMarkAt(Symbol.CROSS, 7, 2);

        // But still nobody should win:

        assertEquals(Symbol.EMPTY, simulator.getWinner());

    }

    @Test
    public void testGetIndexNextBoard() {
        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        simulator.setMarkAt(Symbol.CIRCLE, 0, 4);
        assertEquals(4, simulator.getIndexNextBoard());
        simulator.setMarkAt(Symbol.CIRCLE, 4, 5);
        assertEquals(5, simulator.getIndexNextBoard());
    }

    @Test
    public void testSetNextIndexBoard() {
        simulator.setCurrentPlayerSymbol(Symbol.CIRCLE);
        simulator.setIndexNextBoard(4);
        assertEquals(4, simulator.getIndexNextBoard());

        simulator.setIndexNextBoard(6);
        assertEquals(6, simulator.getIndexNextBoard());

        assertThrows(IllegalArgumentException.class,
                () -> simulator.setIndexNextBoard(9));
    }
}
