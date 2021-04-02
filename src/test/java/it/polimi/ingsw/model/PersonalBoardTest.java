package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class PersonalBoardTest {

    /**
     * Deposits test. We are testing if the methods regarding the deposits (get, set, swap) work. We try to cover each
     * possible scenario for the swapDeposits method (action ok, action ko for too much resources, action ko for leader
     * deposits problems).
     */
    @Test
    public void depositsTest(){
        PersonalBoard test= new PersonalBoard();
        ArrayList<ResourceAmount> deps= test.getDeposits();
        for (ResourceAmount dep: deps) {
            assertNull(dep.getColor());
            assertEquals(0, dep.getAmount());
        }
        deps.set(0, new ResourceAmount(Color.BLUE, 1));
        deps.set(1, new ResourceAmount(Color.GREY, 1));
        assertNotEquals(test.getDeposits().get(0).getColor(), deps.get(0).getColor());
        assertNotEquals(test.getDeposits().get(0).getAmount(), deps.get(0).getAmount());
        assertNotEquals(test.getDeposits().get(1).getColor(), deps.get(1).getColor());
        assertNotEquals(test.getDeposits().get(1).getAmount(), deps.get(1).getAmount());

        test.setDeposits(deps);
        assertEquals(deps.get(0).getColor(), test.getDeposits().get(0).getColor());
        assertEquals(deps.get(0).getAmount(), test.getDeposits().get(0).getAmount());
        assertEquals(deps.get(1).getColor(), test.getDeposits().get(1).getColor());
        assertEquals(deps.get(1).getAmount(), test.getDeposits().get(1).getAmount());

        deps= test.getDeposits();
        deps.set(2, new ResourceAmount(Color.YELLOW, 2));
        assertNotEquals(test.getDeposits().get(2).getColor(), deps.get(2).getColor());
        assertNotEquals(test.getDeposits().get(2).getAmount(), deps.get(2).getAmount());
        test.setDeposits(deps);
        assertEquals(deps.get(2).getColor(), test.getDeposits().get(2).getColor());
        assertEquals(deps.get(2).getAmount(), test.getDeposits().get(2).getAmount());

        deps= test.getDeposits();
        test.addSpecialDeposit(Color.PURPLE);
        test.addSpecialDeposit(Color.GREY);
        assertNotEquals(deps.size(), test.getDeposits().size());
        deps= test.getDeposits();
        assertEquals(test.getDeposits().size(), deps.size());
        assertEquals(deps.get(3).getColor(), test.getDeposits().get(3).getColor());
        assertEquals(deps.get(3).getAmount(), test.getDeposits().get(3).getAmount());
        assertEquals(deps.get(4).getColor(), test.getDeposits().get(4).getColor());
        assertEquals(deps.get(4).getAmount(), test.getDeposits().get(4).getAmount());

        try {
            test.swapDeposits("small", "mid");
            assertEquals(Color.GREY, test.getDeposits().get(0).getColor());
            assertEquals(1, test.getDeposits().get(0).getAmount());
            assertEquals(Color.BLUE, test.getDeposits().get(1).getColor());
            assertEquals(1, test.getDeposits().get(1).getAmount());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            test.swapDeposits("mid", "big");
            assertEquals(Color.YELLOW, test.getDeposits().get(1).getColor());
            assertEquals(2, test.getDeposits().get(1).getAmount());
            assertEquals(Color.BLUE, test.getDeposits().get(2).getColor());
            assertEquals(1, test.getDeposits().get(2).getAmount());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            test.swapDeposits("small", "big");
            assertEquals(Color.BLUE, test.getDeposits().get(0).getColor());
            assertEquals(1, test.getDeposits().get(0).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(2).getColor());
            assertEquals(1, test.getDeposits().get(2).getAmount());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            test.swapDeposits("small", "mid");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(Color.BLUE, test.getDeposits().get(0).getColor());
            assertEquals(1, test.getDeposits().get(0).getAmount());
            assertEquals(Color.YELLOW, test.getDeposits().get(1).getColor());
            assertEquals(2, test.getDeposits().get(1).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(2).getColor());
            assertEquals(1, test.getDeposits().get(2).getAmount());
        }

        try {
            test.swapDeposits("big", "sp2");
            assertEquals(Color.BLUE, test.getDeposits().get(0).getColor());
            assertEquals(1, test.getDeposits().get(0).getAmount());
            assertEquals(Color.YELLOW, test.getDeposits().get(1).getColor());
            assertEquals(2, test.getDeposits().get(1).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(2).getColor());
            assertEquals(0, test.getDeposits().get(2).getAmount());
            assertEquals(Color.PURPLE, test.getDeposits().get(3).getColor());
            assertEquals(0, test.getDeposits().get(3).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(4).getColor());
            assertEquals(1, test.getDeposits().get(4).getAmount());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            test.swapDeposits("small", "sp1");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(Color.BLUE, test.getDeposits().get(0).getColor());
            assertEquals(1, test.getDeposits().get(0).getAmount());
            assertEquals(Color.YELLOW, test.getDeposits().get(1).getColor());
            assertEquals(2, test.getDeposits().get(1).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(2).getColor());
            assertEquals(0, test.getDeposits().get(2).getAmount());
            assertEquals(Color.PURPLE, test.getDeposits().get(3).getColor());
            assertEquals(0, test.getDeposits().get(3).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(4).getColor());
            assertEquals(1, test.getDeposits().get(4).getAmount());
        }

        try {
            test.swapDeposits("sp1", "sp2");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(Color.BLUE, test.getDeposits().get(0).getColor());
            assertEquals(1, test.getDeposits().get(0).getAmount());
            assertEquals(Color.YELLOW, test.getDeposits().get(1).getColor());
            assertEquals(2, test.getDeposits().get(1).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(2).getColor());
            assertEquals(0, test.getDeposits().get(2).getAmount());
            assertEquals(Color.PURPLE, test.getDeposits().get(3).getColor());
            assertEquals(0, test.getDeposits().get(3).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(4).getColor());
            assertEquals(1, test.getDeposits().get(4).getAmount());
        }

        deps= test.getDeposits();
        deps.remove(4);
        deps.remove(3);
        assertNotEquals(deps.size(), test.getDeposits().size());
        test.setDeposits(deps);
        assertEquals(deps.size(), test.getDeposits().size());
        try {
            test.swapDeposits("small", "sp1");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(Color.BLUE, test.getDeposits().get(0).getColor());
            assertEquals(1, test.getDeposits().get(0).getAmount());
            assertEquals(Color.YELLOW, test.getDeposits().get(1).getColor());
            assertEquals(2, test.getDeposits().get(1).getAmount());
            assertEquals(Color.GREY, test.getDeposits().get(2).getColor());
            assertEquals(0, test.getDeposits().get(2).getAmount());
        }
    }

    /**
     * Develop test. We are testing if the methods regarding the DevelopCard slots (getSlot, getTopCard, addCard) work.
     */
    @Test
    public void developTest(){
        PersonalBoard test= new PersonalBoard();
        DevelopDeck lev1= new DevelopDeck(1, Color.PURPLE);
        DevelopDeck lev2= new DevelopDeck(2, Color.BLUE);
        DevelopDeck lev3= new DevelopDeck(3, Color.GREEN);
        DevelopCard[] slot=null;
        DevelopCard card=null;
        for (int i=0; i<3; i++){
            try {
                slot= test.getSlot(i);
                assertNull(slot[0]);
                assertNull(slot[1]);
                assertNull(slot[2]);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
            try {
                card= test.getTopCard(i);
                assertNull(card);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        }
        card= lev1.getCard();
        lev1.removeCard();
        test.addCard(0, card, false);
        for (int i=0; i<3; i++){
            try {
                slot= test.getSlot(i);
                if (i==0){
                    assertEquals(slot[0], card);
                    assertNull(slot[1]);
                    assertNull(slot[2]);
                }
                else {
                    assertNull(slot[0]);
                    assertNull(slot[1]);
                    assertNull(slot[2]);
                }
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
            try {
                card= test.getTopCard(i);
                if (i==0) {
                    assert slot != null;
                    assertEquals(slot[0], card);
                }
                else assertNull(card);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        }
        try {
            slot= test.getSlot(3);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            card= test.getTopCard(3);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }
}
