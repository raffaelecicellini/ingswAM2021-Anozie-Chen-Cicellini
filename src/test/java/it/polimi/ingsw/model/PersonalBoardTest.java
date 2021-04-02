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

    /**
     * Tests if addToStrongbox works as expected.
     */
    @Test
    void addToStrongbox() {
        PersonalBoard pb = new PersonalBoard();
        ResourceAmount[] test1 = new ResourceAmount[4];
        test1[0] = new ResourceAmount(Color.GREY,9);
        test1[1] = new ResourceAmount(Color.GREY,9);
        test1[2] = new ResourceAmount(Color.GREY,9);
        test1[3] = new ResourceAmount(Color.GREY,9);
        pb.addToStrongbox(test1);
        assertEquals(pb.getStrongbox()[2].getAmount(),36);
        ResourceAmount[] test2 = new ResourceAmount[0];
        for (int i = 0; i < test2.length; i++)
            test2[i] = new ResourceAmount(null,99);
        pb.addToStrongbox(test2);
        assertEquals(pb.getStrongbox()[2].getAmount(),36);
        assertEquals(pb.getStrongbox()[0].getAmount(),0);
        assertEquals(pb.getStrongbox()[1].getAmount(),0);
        assertEquals(pb.getStrongbox()[3].getAmount(),0);
        ResourceAmount[] test3 = new ResourceAmount[8];
        test3[0] = new ResourceAmount(Color.YELLOW,1);
        test3[1] = new ResourceAmount(Color.GREY,2);
        test3[2] = new ResourceAmount(Color.PURPLE,3);
        test3[3] = new ResourceAmount(Color.BLUE,4);
        test3[4] = new ResourceAmount(Color.GREY,5);
        test3[5] = new ResourceAmount(Color.YELLOW,6);
        test3[6] = new ResourceAmount(Color.BLUE,7);
        test3[7] = new ResourceAmount(Color.PURPLE,8);
        pb.addToStrongbox(test3);
        assertEquals(pb.getStrongbox()[2].getAmount(),43);
        assertEquals(pb.getStrongbox()[0].getAmount(),11);
        assertEquals(pb.getStrongbox()[1].getAmount(),11);
        assertEquals(pb.getStrongbox()[3].getAmount(),7);
    }

    /**
     * Tests if getPosition works as expected.
     */
    @Test
    void getPosition() {
        PersonalBoard pb = new PersonalBoard();
        assertEquals(pb.getPosition(),0);
        pb.getFaithMarker().setPosition(4);
        assertEquals(pb.getPosition(),4);

    }

    /**
     * Tests if setPosition works as expected.
     */
    @Test
    void setPosition() {
        PersonalBoard pb = new PersonalBoard();
        assertEquals(pb.getPosition(),0);
        pb.setPosition(4);
        assertEquals(pb.getPosition(),4);

    }

    /**
     * Tests if getStrongbox works as expected.
     */
    @Test
    void getStrongbox() {
        PersonalBoard pb = new PersonalBoard();
        for (ResourceAmount x : pb.getStrongbox())
            assertEquals(x.getAmount(),0);
        assertEquals(pb.getStrongbox()[0].getColor(),Color.BLUE);
        assertEquals(pb.getStrongbox()[1].getColor(),Color.PURPLE);
        assertEquals(pb.getStrongbox()[2].getColor(),Color.GREY);
        assertEquals(pb.getStrongbox()[3].getColor(),Color.YELLOW);
    }

    /**
     * Tests if setStrongbox works as expected.
     */
    @Test
    void setStrongbox() {
        PersonalBoard pb = new PersonalBoard();
        ResourceAmount[] test = new ResourceAmount[4];
        for (ResourceAmount x : pb.getStrongbox())
            assertEquals(x.getAmount(),0);
        assertEquals(pb.getStrongbox()[0].getColor(),Color.BLUE);
        assertEquals(pb.getStrongbox()[1].getColor(),Color.PURPLE);
        assertEquals(pb.getStrongbox()[2].getColor(),Color.GREY);
        assertEquals(pb.getStrongbox()[3].getColor(),Color.YELLOW);
        test[0] = new ResourceAmount(Color.PURPLE,3);
        test[1] = new ResourceAmount(Color.BLUE,3);
        test[2] = new ResourceAmount(Color.GREY,3);
        test[3] = new ResourceAmount(Color.YELLOW,3);
        pb.setStrongbox(test);
        for (ResourceAmount x : pb.getStrongbox())
            assertEquals(x.getAmount(),3);
        assertEquals(pb.getStrongbox()[0].getColor(),Color.PURPLE);
        assertEquals(pb.getStrongbox()[1].getColor(),Color.BLUE);
        assertEquals(pb.getStrongbox()[2].getColor(),Color.GREY);
        assertEquals(pb.getStrongbox()[3].getColor(),Color.YELLOW);
    }

    /**
     * Tests if getCell works as expected.
     */
    @Test
    void getCell() {
        PersonalBoard pb = new PersonalBoard();
        for (int i = 0; i < 25; i++)
            if (i == 8 || i == 16 || i == 24)
                assertTrue(pb.getCell(i).isPopeSpace());
            else
                assertFalse(pb.getCell(i).isPopeSpace());
        for (int i = 0; i <=2; i++)
            assertEquals(pb.getCell(i).getVictoryPoints(),0);
        for (int i = 3; i <=5; i++)
            assertEquals(pb.getCell(i).getVictoryPoints(),1);
        for (int i = 6; i <=8; i++)
            assertEquals(pb.getCell(i).getVictoryPoints(),2);
        for (int i = 9; i <=11; i++)
            assertEquals(pb.getCell(i).getVictoryPoints(),4);
        for (int i = 12; i <=14; i++)
            assertEquals(pb.getCell(i).getVictoryPoints(),6);
        for (int i = 15; i <=17; i++)
            assertEquals(pb.getCell(i).getVictoryPoints(),9);
        for (int i = 18; i <=20; i++)
            assertEquals(pb.getCell(i).getVictoryPoints(),12);
        for (int i = 21; i <=23; i++)
            assertEquals(pb.getCell(i).getVictoryPoints(),16);
        assertEquals(pb.getCell(24).getVictoryPoints(),20);
    }

    /**
     * Test if getTile works as expected.
     */
    @Test
    void getTile() {
        PersonalBoard pb = new PersonalBoard();
        for (int i = 0; i < 3; i++)
            assertFalse(pb.getTile(i));
    }

    /**
     * Test if setTile works as expected.
     */
    @Test
    void setTile() {
        PersonalBoard pb = new PersonalBoard();
        for (int i = 0; i < 3; i++)
            assertFalse(pb.getTile(i));
        pb.setTile(0,true);
        pb.setTile(1,true);
        pb.setTile(2,true);
        for (int i = 0; i < 3; i++)
            assertTrue(pb.getTile(i));
    }

    /**
     * Tests if getFaithMarker works as expected.
     */
    @Test
    void getFaithMarker() {
        PersonalBoard pb = new PersonalBoard();
        assertNotNull(pb.getFaithMarker());
        assertEquals(pb.getFaithMarker().getPosition(),0);

    }
}
