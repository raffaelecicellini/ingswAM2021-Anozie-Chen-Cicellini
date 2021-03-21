package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void getVictoryPoints() {
        Cell a = new Cell(2,true);
        assertEquals(a.getVictoryPoints(),2);

        Cell b = new Cell(10,false);
        assertEquals(b.getVictoryPoints(),10);



    }

    @Test
    void setVictoryPoints() {
        Cell a = new Cell(0,true);
        a.setVictoryPoints(1);
        assertEquals(a.getVictoryPoints(),1);

        Cell b = new Cell(1,true);
        b.setVictoryPoints(99);
        assertEquals(b.getVictoryPoints(),99);
    }

    @Test
    void isPopeSpace() {
        Cell a = new Cell(0,true);
        assertTrue(a.isPopeSpace());

        Cell b = new Cell(0,false);
        assertFalse(b.isPopeSpace());
    }

    @Test
    void setPopeSpace() {
        Cell a = new Cell(0,true);
        assertTrue(a.isPopeSpace());
        a.setPopeSpace(false);
        assertFalse(a.isPopeSpace());

        Cell b = new Cell(0,false);
        assertFalse(b.isPopeSpace());
        b.setPopeSpace(true);
        assertTrue(b.isPopeSpace());
        b.setPopeSpace(true);
        assertTrue(b.isPopeSpace());

    }
}