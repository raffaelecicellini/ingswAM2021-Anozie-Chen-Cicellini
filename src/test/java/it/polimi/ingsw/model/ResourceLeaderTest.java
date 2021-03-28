package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ResourceLeaderTest tests ResourceLeader class.
 */
class ResourceLeaderTest {

    /**
     * Tests if the method returns the expected result.
     */
    @Test
    void checkRequirements() {
        ResourceAmount req = new ResourceAmount(Color.YELLOW,5);
        LeaderCard a = new ResourceLeader(3,"Deposit",false,false,req,Color.GREY);
        ArrayList<ResourceAmount> deposit = new ArrayList<>();
        ResourceAmount q = new ResourceAmount(Color.YELLOW,3);
        ResourceAmount w = new ResourceAmount(Color.BLUE,2);
        ResourceAmount e = new ResourceAmount(Color.YELLOW,1);
        ResourceAmount r = new ResourceAmount(Color.GREEN,2);
        ResourceAmount t = new ResourceAmount(Color.YELLOW,2);
        deposit.add(q);
        deposit.add(w);
        deposit.add(e);
        deposit.add(r);
        deposit.add(t);
        assertTrue(a.checkRequirements(deposit,null,null,null,null));
        deposit.remove(0);
        assertFalse(a.checkRequirements(deposit,null,null,null,null));
    }

    /**
     * Tests if the method returns the expected color.
     */
    @Test
    void getDeposit() {
        ResourceAmount req = new ResourceAmount(Color.YELLOW,5);
        LeaderCard a = new ResourceLeader(3,"Deposit",false,false,req,Color.GREY);
        assertEquals(a.getDeposit(),Color.GREY);
    }
}