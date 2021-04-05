package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FaithLeaderTest tests FaithLeader class.
 */
class FaithLeaderTest {

    /**
     * Tests if the method returns the expected result.
     */
    @Test
    void checkRequirements() {
        FaithMarker f = new FaithMarker(2);
        FaithLeader test = new FaithLeader(14,"Faith",false,false,1);
        assertTrue(test.checkRequirements(null,null,f,null));
        f.setPosition(20);
        assertTrue(test.checkRequirements(null,null,f,null));
        FaithLeader test1 = new FaithLeader(14,"Faith",false,false,10);
        f.setPosition(2);
        assertFalse(test1.checkRequirements(null,null,f,null));
    }
}