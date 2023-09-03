package uttt.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import uttt.UTTTFactory;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class MarkTest {

    private MarkInterface mark;
    
    @Test
    public void testMarkInterface() {
        // Create a valid Mark object
        mark = UTTTFactory.createMark(Symbol.CIRCLE, 2);
        assertNotNull(mark);
        // Verify the symbol, position
        assertEquals(Symbol.CIRCLE, mark.getSymbol());
        assertEquals(2, mark.getPosition());

        //Verifying setSymbol function
        mark.setSymbol(Symbol.CROSS);
        assertEquals(Symbol.CROSS, mark.getSymbol());


        // Test invalid inputs
        assertThrows(IllegalArgumentException.class, () -> UTTTFactory.createMark(Symbol.CROSS, 9)); // Invalid symbol
    }
}
