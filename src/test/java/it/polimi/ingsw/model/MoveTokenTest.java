package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTokenTest {
    /**
     * Action test. We are testing if the action of the MoveToken is done correctly.
     */
    @Test
    public void moveTest() {
        MoveToken test = new MoveToken(2);
        FaithMarker black = new FaithMarker(0);
        DevelopDeck[][] decks = new DevelopDeck[4][3];
        Color actual = Color.GREEN;
        int col = 0;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.BLUE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.YELLOW;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        actual = Color.PURPLE;
        col++;
        for (int row = 0; row < 3; row++) {
            decks[col][row] = new DevelopDeck(row + 1, actual);
        }
        System.out.println("Position: " + black);
        assertEquals(0, black.getPosition());
        test.doAction(black, decks);
        assertEquals(2, black.getPosition());
        System.out.println("Position: " + black);
        test.doAction(black, decks);
        assertEquals(4, black.getPosition());
        System.out.println("Position: " + black);
        test.doAction(black, decks);
        assertEquals(6, black.getPosition());
        System.out.println("Position: " + black);
        test.doAction(black, decks);
        assertEquals(8, black.getPosition());
        System.out.println("Position: " + black);
        test.doAction(black, decks);
        assertEquals(10, black.getPosition());
        System.out.println("Position: " + black);
    }
}
