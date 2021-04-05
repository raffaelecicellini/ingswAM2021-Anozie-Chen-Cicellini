package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LevTwoLeaderTest tests LevTwoLeader class.
 */
class LevTwoLeaderTest {

    /**
     * Tests if the method returns the expected result.
     */
    @Test
    void checkRequirements() {
        LevTwoLeader a = new LevTwoLeader(2,"LevTwo",false,false,Color.GREEN,Color.GREY);
        DevelopCard[] b = new DevelopCard[3];
        DevelopCard q = new DevelopCard(1,1,1, Color.GREY,null,null,null);
        DevelopCard w = new DevelopCard(1,1,1, Color.GREY,null,null,null);
        b[0] = q;
        b[1] = w;
        ArrayList<DevelopCard[]> slots= new ArrayList<>();
        slots.add(b);
        slots.add(b);
        slots.add(b);
        assertFalse(a.checkRequirements(null,slots,null,null));
        DevelopCard e = new DevelopCard(1,1,1, Color.GREY,null,null,null);
        b[2] = e;
        assertFalse(a.checkRequirements(null,slots,null,null));
        e.setLevel(2);
        e.setColor(Color.GREEN);
        assertTrue(a.checkRequirements(null,slots,null,null));
    }

    /**
     * Tests if the method returns the expected color.
     */
    @Test
    void getProduction() {
        LevTwoLeader a = new LevTwoLeader(2,"LevTwo",false,false,Color.GREEN,Color.GREY);
        assertEquals(a.getProduction(),Color.GREY);
    }
}