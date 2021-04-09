package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void creationTest(){
        Game test= new Game();
        test.createPlayer("test");
        assertEquals("test", test.getPlayers().get(0).getName());
        test.createPlayer("second");
        assertEquals("second", test.getPlayers().get(1).getName());
        test.start();
        assertEquals(4, test.getActivePlayers().get(0).getLeaders().size());
        assertEquals(4, test.getActivePlayers().get(1).getLeaders().size());
        System.out.println(test.getActivePlayers().get(0).getLeaders().toString());
        System.out.println(test.getActivePlayers().get(1).getLeaders().toString());
        assertEquals(1, test.getActivePlayers().get(1).getNumberInitialResource());

        Player curr= test.getCurrentPlayer();
        try {
            test.endTurn("second");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(curr.getName(), test.getCurrentPlayer().getName());
        }
        try {
            test.endTurn("test");
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(curr.getName(), test.getCurrentPlayer().getName());
        }
    }
    @Test
    public void leaderActionTest(){
        Game test= new Game();
        test.createPlayer("test");
        test.createPlayer("second");
        test.start();
        Player curr= test.getCurrentPlayer();
        Player second= test.getActivePlayers().get(1);

        try {
            test.activateLeader(curr.getName(), 0);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertFalse(test.getCurrentPlayer().getLeaders().get(0).isActive());
            assertFalse(test.getCurrentPlayer().getLeaders().get(0).isDiscarded());
        }
        try {
            test.discardLeader(curr.getName(), 0);
            assertFalse(test.getCurrentPlayer().getLeaders().get(0).isActive());
            assertTrue(test.getCurrentPlayer().getLeaders().get(0).isDiscarded());
            assertEquals(1, test.getCurrentPlayer().getPersonalBoard().getPosition());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            test.endTurn(curr.getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(curr.getName(), test.getCurrentPlayer().getName());
        }
        try {
            test.discardLeader(curr.getName(), 1);
            assertFalse(test.getCurrentPlayer().getLeaders().get(1).isActive());
            assertTrue(test.getCurrentPlayer().getLeaders().get(1).isDiscarded());
            assertEquals(2, test.getCurrentPlayer().getPersonalBoard().getPosition());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            test.activateLeader(curr.getName(), 0);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertFalse(test.getCurrentPlayer().getLeaders().get(0).isActive());
            assertTrue(test.getCurrentPlayer().getLeaders().get(0).isDiscarded());
        }
        try {
            test.discardLeader(curr.getName(), 1);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals("it.polimi.ingsw.model.exceptions.InvalidActionException: You can't discard another leader", e.toString());
            assertFalse(test.getCurrentPlayer().getLeaders().get(1).isActive());
            assertTrue(test.getCurrentPlayer().getLeaders().get(1).isDiscarded());
        }
        try {
            test.activateLeader(second.getName(), 0);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals("it.polimi.ingsw.model.exceptions.InvalidActionException: It is not your turn!", e.toString());
            assertEquals(curr.getName(), test.getCurrentPlayer().getName());
        }
        try {
            test.discardLeader(second.getName(), 1);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals("it.polimi.ingsw.model.exceptions.InvalidActionException: It is not your turn!", e.toString());
            assertEquals(curr.getName(), test.getCurrentPlayer().getName());
        }
    }
    @Test
    public void productionTest(){

    }
}
