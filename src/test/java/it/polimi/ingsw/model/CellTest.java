package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Cell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CellTest class tests Cell class.
 */
class CellTest {

    /**
     * Tests if the correct value is returned by the getVictoryPoints method.
     */
    @Test
    void getVictoryPoints() {
        Cell a = new Cell(2,true);
        assertEquals(a.getVictoryPoints(),2);

        Cell b = new Cell(10,false);
        assertEquals(b.getVictoryPoints(),10);
    }

    /**
     * Tests if the method sets correctly the Cell's victory points.
     */
    @Test
    void setVictoryPoints() {
        Cell a = new Cell(0,true);
        a.setVictoryPoints(1);
        assertEquals(a.getVictoryPoints(),1);

        Cell b = new Cell(1,true);
        b.setVictoryPoints(99);
        assertEquals(b.getVictoryPoints(),99);
    }

    /**
     * Tests if the isPopespace method returns the correct value.
     */
    @Test
    void isPopeSpace() {
        Cell a = new Cell(0,true);
        assertTrue(a.isPopeSpace());

        Cell b = new Cell(0,false);
        assertFalse(b.isPopeSpace());
    }

    /**
     * Tests if the setPopeSpace method sets correctly the Cell.
     */
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