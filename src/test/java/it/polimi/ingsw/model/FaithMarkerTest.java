package it.polimi.ingsw.model;

import it.polimi.ingsw.model.FaithMarker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FaithMarkerTest class tests FaithMarker class.
 */
class FaithMarkerTest {

    /**
     * Tests if the correct value is returned by the getPosition method.
     */
    @Test
    void getPosition() {
        FaithMarker a = new FaithMarker(2);
        assertEquals(a.getPosition(), 2);

        FaithMarker b = new FaithMarker(40);
        assertEquals(b.getPosition(), 40);

    }

    /**
     * Tests if the method sets correctly the FaithMarker's position.
     */
    @Test
    void setPosition() {
        FaithMarker a = new FaithMarker(2);
        a.setPosition(21);
        assertEquals(a.getPosition(), 21);

        FaithMarker b = new FaithMarker(40);
        b.setPosition(1);
        assertEquals(b.getPosition(), 1);
    }
}