package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FavorTileTest {

    @Test
    void setVictoryPoints() {
        FavorTile a = new FavorTile(4,true);
        assertEquals(a.getVictoryPoints(),4);
        a.setVictoryPoints(5);
        assertEquals(a.getVictoryPoints(),5);

    }

    @Test
    void getVictoryPoints() {
        FavorTile a = new FavorTile(99,true);
        assertEquals(a.getVictoryPoints(),99);

        FavorTile b = new FavorTile(0,true);
        assertEquals(b.getVictoryPoints(),0);
    }

    @Test
    void setActive() {
        FavorTile a = new FavorTile(2,true);
        a.setActive(true);
        assertTrue(a.isActive());
        a.setActive(false);
        assertFalse(a.isActive());

    }

    @Test
    void isActive() {
        FavorTile a = new FavorTile(2,true);
        assertTrue(a.isActive());

        FavorTile b = new FavorTile(4,false);
        assertFalse(b.isActive());
    }
}