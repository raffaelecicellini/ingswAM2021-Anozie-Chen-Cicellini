package it.polimi.ingsw.model;

import it.polimi.ingsw.model.FaithMarker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FaithMarkerTest {

    @Test
    void getPosition() {
        FaithMarker a = new FaithMarker(2);
        assertEquals(a.getPosition(),2);

        FaithMarker b = new FaithMarker(40);
        assertEquals(b.getPosition(),40);

    }

    @Test
    void setPosition() {
        FaithMarker a = new FaithMarker(2);
        a.setPosition(21);
        assertEquals(a.getPosition(),21);

        FaithMarker b = new FaithMarker(40);
        b.setPosition(1);
        assertEquals(b.getPosition(),1);
    }
}