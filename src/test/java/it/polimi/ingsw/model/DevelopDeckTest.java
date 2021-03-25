package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DevelopDeckTest {
    /**
     * Level test. We are testing if the level of the Deck is correctly set.
     */
    @Test
    public void levelTest() {
        DevelopDeck test= new DevelopDeck(1, Color.YELLOW);
        assertEquals(1, test.getLevel());
    }

    /**
     * Color test. We are testing if the color of the deck is correctly set.
     */
    @Test
    public void colorTest() {
        DevelopDeck test= new DevelopDeck(2, Color.BLUE);
        assertEquals(Color.BLUE, test.getColor());
    }

    /**
     * Card test. We are testing if the cards are correctly shuffled once created, if the top card is correctly retrieved
     * by the getCard() method and if the removeCard() method works (if it correctly decrease top in order to the getCard()
     * method to returns a different card).
     */
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
