package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DevelopDeckTest {
    @Test
    public void levelTest() {
        DevelopDeck test= new DevelopDeck(1, Color.YELLOW);
        assertEquals(1, test.getLevel());
    }
    @Test
    public void colorTest() {
        DevelopDeck test= new DevelopDeck(2, Color.BLUE);
        assertEquals(Color.BLUE, test.getColor());
    }
    @Test
    public void cardTest() {
        DevelopDeck test= new DevelopDeck(3, Color.GREEN);
        DevelopCard first= test.getCard();
        int initial= test.getTop();
        assertEquals(first, test.getCard());
        assertEquals(initial, test.getTop());
        System.out.println(test.getCard());
        test.removeCard();
        assertNotEquals(first, test.getCard());
        assertNotEquals(initial, test.getTop());
        System.out.println(test.getCard());

    }
}
