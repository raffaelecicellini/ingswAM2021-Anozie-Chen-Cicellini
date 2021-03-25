package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class SoloActionsTest {
    /**
     * Creation test. We are testing if the creations of the SoloActions and each single ActionToken is done correctly.
     * Also, we are testing if the tokens' array is shuffled correctly when created.
     */
    @Test
    public void creationTest() {
        SoloActions test= new SoloActions();
        System.out.println(test.toString());
    }

    /**
     * Action test. We are testing if a single action done by the first ActionToken is done correctly.
     */
    @Test
    public void doActionTest() {
        SoloActions test= new SoloActions();
        FaithMarker black= new FaithMarker(0);
        DevelopDeck[][] decks= new DevelopDeck[4][3];
        Color actual= Color.GREEN;
        int col=0;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.BLUE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual= Color.YELLOW;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        actual=Color.PURPLE;
        col++;
        for (int row=0; row<3; row++) {
            decks[col][row]= new DevelopDeck(row+1, actual);
        }
        System.out.println(test.toString());
        System.out.println(Arrays.deepToString(decks));
        System.out.println(black);
        test.doAction(black, decks);
        System.out.println(test.toString());
        System.out.println(Arrays.deepToString(decks));
        System.out.println(black);
    }
}
