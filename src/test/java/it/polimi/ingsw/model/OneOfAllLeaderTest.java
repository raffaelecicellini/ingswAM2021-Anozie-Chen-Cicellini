package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OneOfAllLeaderTest tests OneOfAllLeader class.
 */
class OneOfAllLeaderTest {

    /**
     * Tests if the method returns the expected result.
     */
    @Test
    void checkRequirements() {
        OneOfAllLeader a = new OneOfAllLeader(2,"OneAndOne",false,false);
        DevelopCard[] b = new DevelopCard[4];
        ArrayList<DevelopCard[]> slots= new ArrayList<>();
        slots.add(b);
        slots.add(b);
        slots.add(b);
        assertFalse(a.checkRequirements(null,slots,null,null));
        DevelopCard q = new DevelopCard(1,1,1, Color.GREEN,null,null,null);
        DevelopCard w = new DevelopCard(1,1,1, Color.BLUE,null,null,null);
        b[0] = q;
        b[1] = w;
        assertFalse(a.checkRequirements(null,slots,null,null));
        DevelopCard e = new DevelopCard(1,1,1, Color.YELLOW,null,null,null);
        b[2] = e;
        assertFalse(a.checkRequirements(null,slots,null,null));
        b[3] = new DevelopCard(1,1,1,Color.PURPLE,null,null,null);
        assertTrue(a.checkRequirements(null,slots,null,null));
    }
}