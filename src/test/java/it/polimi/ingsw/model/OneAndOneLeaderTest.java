package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OneAndOneLeaderTest tests OneAndOneLeader class.
 */
class OneAndOneLeaderTest {

    /**
     * Tests if the method returns the expected result.
     */
    @Test
    void checkRequirements() {
        OneAndOneLeader a = new OneAndOneLeader(2, "OneAndOne", false, false, Color.GREEN, Color.GREY, Color.YELLOW, 0);
        DevelopCard[] b = new DevelopCard[3];
        ArrayList<DevelopCard[]> slots = new ArrayList<>();
        slots.add(b);
        slots.add(b);
        slots.add(b);
        assertFalse(a.checkRequirements(null, slots, null, null));
        DevelopCard q = new DevelopCard(1, 1, 1, Color.GREEN, null, null, null, 0);
        DevelopCard w = new DevelopCard(1, 1, 1, Color.BLUE, null, null, null, 0);
        b[0] = q;
        b[1] = w;
        assertFalse(a.checkRequirements(null, slots, null, null));
        DevelopCard e = new DevelopCard(1, 1, 1, Color.GREY, null, null, null, 0);
        b[2] = e;
        assertTrue(a.checkRequirements(null, slots, null, null));
    }

    /**
     * Tests if the method returns the expected discount color.
     */
    @Test
    void getDiscount() {
        OneAndOneLeader a = new OneAndOneLeader(2, "OneAndOne", false, false, Color.GREEN, Color.GREY, Color.YELLOW, 0);
        assertEquals(a.getDiscount(), Color.YELLOW);
    }
}