package it.polimi.ingsw.model;

import it.polimi.ingsw.model.FavorTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * FavorTileTest class tests FavorTile class.
 */
class FavorTileTest {

    /**
     * Tests if the method sets correctly the FavorTile's victory points.
     */
    @Test
    void setVictoryPoints() {
        FavorTile a = new FavorTile(4,true);
        assertEquals(a.getVictoryPoints(),4);
        a.setVictoryPoints(5);
        assertEquals(a.getVictoryPoints(),5);

    }

    /**
     * Tests if the method returns the correct amount of the FavorTile's victory points.
     */
    @Test
    void getVictoryPoints() {
        FavorTile a = new FavorTile(99,true);
        assertEquals(a.getVictoryPoints(),99);

        FavorTile b = new FavorTile(0,true);
        assertEquals(b.getVictoryPoints(),0);
    }

    /**
     * Tests if the method sets correctly the state of the FavorTile.
     */
    @Test
    void setActive() {
        FavorTile a = new FavorTile(2,true);
        a.setActive(true);
        assertTrue(a.isActive());
        a.setActive(false);
        assertFalse(a.isActive());

    }

    /**
     * Tests if the correct value of the FavorTile's state is returned.
     */
    @Test
    void isActive() {
        FavorTile a = new FavorTile(2,true);
        assertTrue(a.isActive());

        FavorTile b = new FavorTile(4,false);
        assertFalse(b.isActive());
    }
}