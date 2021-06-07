package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveAndShuffleTokenTest {
    /**
     * Action test. We are testing if the action of the MoveandShuffleToken is done correctly.
     */
    @Test
    public void moveShuffleTest() {
        MoveandShuffleToken test = new MoveandShuffleToken(1);
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
        boolean res = test.doAction(black, decks);
        assertTrue(res);
        assertEquals(1, black.getPosition());
    }
}
