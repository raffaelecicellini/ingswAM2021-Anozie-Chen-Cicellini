package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    /**
     * Creation test. We are checking if the Game is correctly instantiated, the players are added to the Game and the Game
     * starts without problems.
     */
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

    /**
     * Leader action test. We are checking if the methods activateLeader() and discardLeader() works. We are trying the
     * case when a player wants to activate a leader but it's not his turn or he doesn't have the requirements to activate
     * it, and when he wants to discard a leader and the action goes fine, it is not his turn or he has already discarded
     * both leaders.
     */
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

    /**
     * Production test. We are testing if the method produce() works. We tests the case when the action goes well, when
     * the player already did a mandatory action, when it is not his turn, when he ends the turn correctly and the second
     * player does the same.
     */
    @Test
    public void productionTest(){
        Game test= new Game();
        test.createPlayer("test");
        test.createPlayer("second");
        test.start();
        Player curr= test.getCurrentPlayer();
        Player second= test.getActivePlayers().get(1);

        ArrayList<ResourceAmount> deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.BLUE, 1));
        deps.add(new ResourceAmount(Color.YELLOW, 1));
        deps.add(new ResourceAmount(Color.GREY, 3));
        curr.getPersonalBoard().setDeposits(deps);
        deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.YELLOW, 1));
        deps.add(new ResourceAmount(Color.BLUE, 0));
        deps.add(new ResourceAmount(Color.GREY, 2));
        second.getPersonalBoard().setDeposits(deps);

        Map<String, String> info= new HashMap<>();
        info.put("prod0", "Yes");
        info.put("in01", "blue");
        info.put("pos01", "small");
        info.put("in02", "grey");
        info.put("pos02", "big");
        info.put("out0", "yellow");
        try {
            test.produce(curr.getName(), info);
            assertTrue(test.isDoneMandatory());
            System.out.println(curr.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(curr.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            test.produce(curr.getName(), info);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertTrue(test.isDoneMandatory());
            assertEquals("it.polimi.ingsw.model.exceptions.InvalidActionException: You have already done a mandatory action!", e.toString());
        }

        try {
            test.produce(second.getName(), info);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals("it.polimi.ingsw.model.exceptions.InvalidActionException: It is not your turn!", e.toString());
            assertEquals(curr.getName(), test.getCurrentPlayer().getName());
        }

        try {
            test.endTurn(curr.getName());
            assertEquals(second.getName(), test.getCurrentPlayer().getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        info.clear();
        info.put("prod0", "Yes");
        info.put("in01", "Yellow");
        info.put("pos01", "small");
        info.put("in02", "grey");
        info.put("pos02", "big");
        info.put("out0", "yellow");
        try {
            test.produce(second.getName(), info);
            assertTrue(test.isDoneMandatory());
            System.out.println(second.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(second.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            test.produce(second.getName(), info);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertTrue(test.isDoneMandatory());
            assertEquals("it.polimi.ingsw.model.exceptions.InvalidActionException: You have already done a mandatory action!", e.toString());
        }
    }

    /**
     * Endgame test. We are testing if the game, once a player reaches the end of the FaithTrack, ends without problems
     * and declaring the right winner.
     */
    @Test
    public void endgameTest(){
        Game test= new Game();
        test.createPlayer("test");
        test.createPlayer("second");
        test.start();
        Player curr= test.getCurrentPlayer();
        Player second= test.getActivePlayers().get(1);

        curr.getPersonalBoard().setPosition(24);
        second.getPersonalBoard().setPosition(10);
        ArrayList<ResourceAmount> deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.BLUE, 1));
        deps.add(new ResourceAmount(Color.YELLOW, 1));
        deps.add(new ResourceAmount(Color.GREY, 3));
        curr.getPersonalBoard().setDeposits(deps);
        deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.YELLOW, 1));
        deps.add(new ResourceAmount(Color.BLUE, 0));
        deps.add(new ResourceAmount(Color.GREY, 2));
        second.getPersonalBoard().setDeposits(deps);
        Map<String, String> info= new HashMap<>();
        info.put("prod0", "Yes");
        info.put("in01", "blue");
        info.put("pos01", "small");
        info.put("in02", "grey");
        info.put("pos02", "big");
        info.put("out0", "yellow");
        try {
            test.produce(curr.getName(), info);
            assertTrue(test.isDoneMandatory());
            System.out.println(curr.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(curr.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            test.endTurn(curr.getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        info.clear();
        info.put("prod0", "Yes");
        info.put("in01", "Yellow");
        info.put("pos01", "small");
        info.put("in02", "grey");
        info.put("pos02", "big");
        info.put("out0", "yellow");
        try {
            test.produce(second.getName(), info);
            assertTrue(test.isDoneMandatory());
            System.out.println(second.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(second.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            test.endTurn(second.getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Endgame test with equal points. We are testing if the game, in case two players have the same amount of points,
     * correctly decides which is the winner by counting the resources left in their deposits and strongbox.
     */
    @Test
    public void winnerByResTest(){
        Game test= new Game();
        test.createPlayer("test");
        test.createPlayer("second");
        test.start();
        Player curr= test.getCurrentPlayer();
        Player second= test.getActivePlayers().get(1);
        System.out.println(curr.getName());
        System.out.println(second.getName());

        curr.getPersonalBoard().setPosition(24);
        second.getPersonalBoard().setPosition(24);
        ArrayList<ResourceAmount> deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.BLUE, 1));
        deps.add(new ResourceAmount(Color.YELLOW, 2));
        deps.add(new ResourceAmount(Color.GREY, 2));
        curr.getPersonalBoard().setDeposits(deps);
        deps= new ArrayList<>();
        deps.add(new ResourceAmount(Color.YELLOW, 1));
        deps.add(new ResourceAmount(Color.BLUE, 0));
        deps.add(new ResourceAmount(Color.GREY, 2));
        second.getPersonalBoard().setDeposits(deps);
        Map<String, String> info= new HashMap<>();
        info.put("prod0", "Yes");
        info.put("in01", "blue");
        info.put("pos01", "small");
        info.put("in02", "grey");
        info.put("pos02", "big");
        info.put("out0", "yellow");
        try {
            test.produce(curr.getName(), info);
            assertTrue(test.isDoneMandatory());
            System.out.println(curr.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(curr.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            test.endTurn(curr.getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        info.clear();
        info.put("prod0", "Yes");
        info.put("in01", "Yellow");
        info.put("pos01", "small");
        info.put("in02", "grey");
        info.put("pos02", "big");
        info.put("out0", "yellow");
        try {
            test.produce(second.getName(), info);
            assertTrue(test.isDoneMandatory());
            System.out.println(second.getPersonalBoard().getDeposits().toString());
            System.out.println(Arrays.toString(second.getPersonalBoard().getStrongbox()));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            test.endTurn(second.getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
    }

    /**
     * lowercasing a map
     */
    @Test
    public void fromMarket1(){

        Map<String, String> map = new HashMap<>();
        map.put("ROW", "4");
        map.put("CoL", "2");

        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(Locale.ROOT),
                e1 -> e1.getValue().toLowerCase(Locale.ROOT)));

        System.out.println(mapCopy);
    }
}
