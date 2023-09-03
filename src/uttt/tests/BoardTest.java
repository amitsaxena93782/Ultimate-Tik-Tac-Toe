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
import uttt.utils.Symbol;

public class BoardTest {
    private BoardInterface board;
    private MarkInterface[] marks;

    @Before
    public void setUp() {
        board = UTTTFactory.createBoard();
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);
    }

    @Test
    public void Initial() {

        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);

        MarkInterface[] marks = board.getMarks();
        assertNotNull(marks);

        assertEquals(9, marks.length);
        for (MarkInterface mark : marks) {
            assertNotNull(mark);
            assertEquals(Symbol.EMPTY, mark.getSymbol());
        }
        assertFalse(board.isClosed());
        assertEquals(Symbol.EMPTY, board.getWinner());
    }

    @Test
    public void testSetMarks() {
        MarkInterface[] marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.CROSS, i);
        }
        board.setMarks(marks);

        MarkInterface[] finalMarks = board.getMarks();
        assertNotNull(finalMarks);
        assertEquals(9, finalMarks.length);

        for (MarkInterface mark : finalMarks) {
            assertNotNull(mark);
            assertEquals(Symbol.CROSS, mark.getSymbol());
        }
        assertThrows(IllegalArgumentException.class, () -> board.setMarks(new MarkInterface[10]));
    }

    @Test
    public void testSetMarkAt() {
        assertTrue(board.setMarkAt(Symbol.CROSS, 4));
        MarkInterface[] marks = board.getMarks();
        assertNotNull(marks);
        assertEquals(Symbol.CROSS, marks[4].getSymbol());
        assertThrows(IllegalArgumentException.class, () -> board.setMarkAt(Symbol.CIRCLE, 9));
        assertThrows(IllegalArgumentException.class, () -> board.setMarkAt(null , 9));
    }

    @Test
    public void testIsClosed() {

        assertFalse(board.isClosed());

        // Completely filling the board
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.CIRCLE, i);
        }
        board.setMarks(marks);
        assertTrue(board.isClosed());

        // Now checking for a player win:

        // When a player has all 3 in a row:
        for (int i = 0; i < 3; i++) {
            setUp();
            board.setMarkAt(Symbol.CIRCLE, 0 + (3 * i));
            board.setMarkAt(Symbol.CIRCLE, 1 + (3 * i));
            board.setMarkAt(Symbol.CIRCLE, 2 + (3 * i));
            assertTrue(board.isClosed());
        }


        // When player has all 3 in a column
        for (int i = 0; i < 3; i++) {
            setUp();
            board.setMarkAt(Symbol.CIRCLE, 0 + i);
            board.setMarkAt(Symbol.CIRCLE, 3 + i);
            board.setMarkAt(Symbol.CIRCLE, 6 + i);
            assertTrue(board.isClosed());
        }

        //Reset
        setUp();

        // When player has all 3 in a diagonal
        board = UTTTFactory.createBoard();
        board.setMarkAt(Symbol.CIRCLE, 0);
        board.setMarkAt(Symbol.CIRCLE, 4);
        board.setMarkAt(Symbol.CIRCLE, 8);
        assertTrue(board.isClosed());

        // Reset
        setUp();

        board = UTTTFactory.createBoard();
        board.setMarkAt(Symbol.CIRCLE, 2);
        board.setMarkAt(Symbol.CIRCLE, 4);
        board.setMarkAt(Symbol.CIRCLE, 6);
        assertTrue(board.isClosed());

    }

    @Test
    public void testIsMovePossible() {
        board.setMarkAt(Symbol.EMPTY, 0);
        assertTrue(board.isMovePossible(0));
        board.setMarkAt(Symbol.CIRCLE, 0);
        assertFalse(board.isMovePossible(0));

        // Will check for any invalid argument
        assertThrows(IllegalArgumentException.class, 
        () -> board.isMovePossible(9));
    }

    @Test
    public void testGetWinner() {
        MarkInterface[] marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);
        // Check for empty Board
        assertEquals(Symbol.EMPTY, board.getWinner());

        // Check when all are O
        for (int i = 0; i < 9; i++) {
            board.setMarkAt(Symbol.CIRCLE, i);
        }
        assertEquals(Symbol.CIRCLE, board.getWinner());

        // Reset
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);

        // Check when only 1st row is O
        for (int i = 0; i < 3; i++) {
            board.setMarkAt(Symbol.CIRCLE, i);
        }
        assertEquals(Symbol.CIRCLE, board.getWinner());

        // Reset
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);

        // Check when only 2nd row is O
        for (int i = 3; i < 6; i++) {
            board.setMarkAt(Symbol.CIRCLE, i);
        }
        assertEquals(Symbol.CIRCLE, board.getWinner());

        // Reset
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);

        // Check when only 3rd row is O
        for (int i = 6; i < 9; i++) {
            board.setMarkAt(Symbol.CIRCLE, i);
        }
        assertEquals(Symbol.CIRCLE, board.getWinner());

        // Reset
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);

        // Check when only each Column is O
        for (int i = 0; i < 3; i++) {

            board.setMarkAt(Symbol.CIRCLE, 0 + i);
            board.setMarkAt(Symbol.CIRCLE, 3 + i);
            board.setMarkAt(Symbol.CIRCLE, 6 + i);

            assertEquals(Symbol.CIRCLE, board.getWinner());

            // Reset
            marks = new MarkInterface[9];
            for (int j = 0; j < 9; j++) {
                marks[j] = UTTTFactory.createMark(Symbol.EMPTY, j);
            }
            board.setMarks(marks);
        }

        // Checking diagonals
        board.setMarkAt(Symbol.CIRCLE, 0);
        board.setMarkAt(Symbol.CIRCLE, 4);
        board.setMarkAt(Symbol.CIRCLE, 8);

        assertEquals(Symbol.CIRCLE, board.getWinner());

        // Reset
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);

        board.setMarkAt(Symbol.CIRCLE, 2);
        board.setMarkAt(Symbol.CIRCLE, 4);
        board.setMarkAt(Symbol.CIRCLE, 6);

        assertEquals(Symbol.CIRCLE, board.getWinner());

        // Reset
        marks = new MarkInterface[9];
        for (int i = 0; i < 9; i++) {
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
        }
        board.setMarks(marks);

        // Checking for tie
        
        board.setMarkAt(Symbol.CIRCLE, 0);
        board.setMarkAt(Symbol.CIRCLE, 2);
        board.setMarkAt(Symbol.CIRCLE, 4);
        board.setMarkAt(Symbol.CIRCLE, 7);

        board.setMarkAt(Symbol.CROSS, 1);
        board.setMarkAt(Symbol.CROSS, 3);
        board.setMarkAt(Symbol.CROSS, 5);
        board.setMarkAt(Symbol.CROSS, 6);
        board.setMarkAt(Symbol.CROSS, 8);

        // Incase of tie, the winner should be empty
        assertEquals(Symbol.EMPTY, board.getWinner());
    }
}
