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
        assertEquals(GamePhase.NOTSTARTED, test.getPhase());
        test.createPlayer("test");
        assertEquals("test", test.getPlayers().get(0).getName());
        test.createPlayer("second");
        assertEquals("second", test.getPlayers().get(1).getName());
        test.start();
        assertEquals(GamePhase.LEADER, test.getPhase());
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
        assertEquals(GamePhase.LEADER, test.getPhase());
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
        assertEquals(GamePhase.NOTSTARTED, test.getPhase());
        test.createPlayer("test");
        test.createPlayer("second");
        test.start();
        assertEquals(GamePhase.LEADER, test.getPhase());
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
        assertEquals(GamePhase.ENDED, test.getPhase());
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
        assertEquals(GamePhase.ENDED, test.getPhase());
    }

    /**
     * lowercasing a map
     */
    @Test
    public void fromMarket1() {

        Game game = new Game();
        game.createPlayer("one");
        game.createPlayer("two");
        game.start();
        Player first = game.getCurrentPlayer();
        Player second = game.getActivePlayers().get(1);

        Map<String, String> res = new HashMap<>();
        res.put("res1", "blue");
        res.put("pos1", "small");
        try {
            second.chooseInitialResource(res);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        ArrayList<LeaderCard> leaders1 = new ArrayList<>();
        leaders1.add(new ResourceLeader(3, "Resource", false, false, new ResourceAmount(Color.YELLOW, 5), Color.GREY));
        leaders1.add(new ResourceLeader(3, "Resource", false, false, new ResourceAmount(Color.PURPLE, 5), Color.BLUE));
        leaders1.add(new TwoAndOneLeader(5, "TwoAndOne", false, false, Color.BLUE, Color.YELLOW, Color.GREY));
        leaders1.add(new TwoAndOneLeader(5, "TwoAndOne", false, false, Color.PURPLE, Color.GREEN, Color.YELLOW));

        ArrayList<LeaderCard> leaders2 = new ArrayList<>();
        leaders2.add(new TwoAndOneLeader(5, "TwoAndOne", false, false, Color.YELLOW, Color.BLUE, Color.PURPLE));
        leaders2.add(new TwoAndOneLeader(5, "TwoAndOne", false, false, Color.GREEN, Color.PURPLE, Color.BLUE));
        leaders2.add(new ResourceLeader(3, "Resource", false, false, new ResourceAmount(Color.YELLOW, 5), Color.GREY));
        leaders2.add(new ResourceLeader(3, "Resource", false, false, new ResourceAmount(Color.PURPLE, 5), Color.BLUE));

        first.clearLeaders();
        second.clearLeaders();
        first.receiveLeaders(leaders1);
        second.receiveLeaders(leaders2);

        try {
            first.chooseLeader(3, 4);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        try {
            second.chooseLeader(1, 2);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        ArrayList<Marble> marbles = new ArrayList<>();
        marbles.add(WhiteMarble.getInstance());
        marbles.add(BlueMarble.getInstance());
        marbles.add(GreyMarble.getInstance());
        marbles.add(YellowMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(RedMarble.getInstance());
        marbles.add(PurpleMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(GreyMarble.getInstance());
        marbles.add(YellowMarble.getInstance());
        marbles.add(PurpleMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(BlueMarble.getInstance());
        game.market.setMarket(marbles);

        // FIRST

        Map<String, String> map = new HashMap<>();
        map.put("row", "2");
        map.put("pos1", "small");
        map.put("pos2", "mid");
        map.put("pos3", "big");
        map.put("pos4", "big");


        try {
            game.fromMarket(first.getName(), map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, first.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(0).getAmount());
        assertNull(first.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(0, first.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.PURPLE, first.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(0, first.getPersonalBoard().getPosition());
        assertTrue(game.doneMandatory);

        /* first player tries to do the mandatory action again

        game.fromMarket(first.getName(), map);

        assertEquals(Color.BLUE, first.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(0).getAmount());
        assertNull(first.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(0, first.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.PURPLE, first.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(2).getAmount());
        assertTrue(game.doneMandatory);*/


        try {
            game.endTurn(first.getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        //SECOND

        map.clear();
        map.put("row", "3");
        map.put("pos1", "mid");
        map.put("pos2", "mid");
        map.put("pos3", "mid");
        map.put("pos4", "big");


        try {
            game.fromMarket(second.getName(), map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, second.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, second.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.GREY, second.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(2, second.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(null, second.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(0, second.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(1, second.getPersonalBoard().getPosition());
        assertTrue(game.doneMandatory);


        try {
            game.endTurn(second.getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        // SET ACTIVE LEADERS

        for (LeaderCard l : first.getLeaders()) {
            l.setActive(true);
        }
        for (LeaderCard l : second.getLeaders()) {
            l.setActive(true);
        }

        // FIRST

        map.clear();
        map.put("source", "small");
        map.put("dest", "big");

        try {
            first.swapDeposit(map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.PURPLE, first.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(0).getAmount());
        assertNull(first.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(0, first.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.BLUE, first.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(2).getAmount());
        assertFalse(game.doneMandatory);

        map.clear();
        map.put("col", "4");
        map.put("pos1", "mid");
        map.put("pos2", "big");
        map.put("pos3", "big");

        try {
            game.fromMarket(first.getName(), map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.PURPLE, first.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.YELLOW, first.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.BLUE, first.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(3, first.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(0, first.getPersonalBoard().getPosition());
        assertTrue(game.doneMandatory);

        try {
            game.endTurn(first.getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        // SECOND

        map.clear();
        map.put("source", "small");
        map.put("dest", "big");

        try {
            second.swapDeposit(map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        map.clear();
        map.put("row", "2");
        map.put("pos1", "big");
        map.put("res1", "BLUE");
        map.put("pos2", "big");
        map.put("res2", "BLUE");
        map.put("pos3", "small");
        map.put("pos4", "big");


        try {
            game.fromMarket(second.getName(), map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.PURPLE, second.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, second.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.GREY, second.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(2, second.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.BLUE, second.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(3, second.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(1, second.getPersonalBoard().getPosition());
        assertEquals(1, first.getPersonalBoard().getPosition());
        assertTrue(game.doneMandatory);

    }


    /**
     * Test shows that the first player can't get resources. The method will throw an exception and the player's deposit remain empty.
     */
    @Test
    public void chooseInitialResourceTest1() {
        Game test = new Game();
        test.createPlayer("one");
        test.createPlayer("two");
        test.start();
        Player one = test.getCurrentPlayer();
        Player two = test.getActivePlayers().get(1);
        Map<String,String> map = new HashMap<>();
        map.put("res1","yellow");
        map.put("pos1","mid");
        try {
            test.chooseInitialResource("one", map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(0).getColor(),null);
            assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(1).getAmount(),0);
            assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(1).getColor(),null);
            assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(2).getAmount(),0);
            assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(2).getColor(),null);
        }
    }

    /**
     * Test shows that the player can't get resources if it isn't his turn. His deposits remain unchanged.
     */
    @Test
    public void chooseInitialResourceTest2() {
        Game test = new Game();
        test.createPlayer("one");
        test.createPlayer("two");
        test.start();
        Player one = test.getCurrentPlayer();
        Player two = test.getActivePlayers().get(1);
        Map<String,String> map = new HashMap<>();
        map.put("res1","yellow");
        map.put("pos1","mid");
        try {
            test.chooseInitialResource("two", map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(0).getColor(),null);
            assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(1).getAmount(),0);
            assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(1).getColor(),null);
            assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(2).getAmount(),0);
            assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(2).getColor(),null);
        }
    }


    /**
     * Test shows that all players receive the right amount of resources.
     */
    @Test
    public void chooseInitialResourceTest3() {
        Game test = new Game();
        test.createPlayer("one");
        test.createPlayer("two");
        test.createPlayer("three");
        test.createPlayer("four");
        test.start();
        String one = test.getActivePlayers().get(0).getName();
        String two = test.getActivePlayers().get(1).getName();
        String three = test.getActivePlayers().get(2).getName();
        String four = test.getActivePlayers().get(3).getName();
        Map<String,String> map = new HashMap<>();
        map.put("res1","yellow");
        map.put("pos1","mid");

        try {
            test.chooseInitialResource(one, map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(0).getColor(),null);
        assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(1).getAmount(),0);
        assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(1).getColor(),null);
        assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(2).getAmount(),0);
        assertEquals(test.getActivePlayers().get(0).getPersonalBoard().getDeposits().get(2).getColor(),null);

        try {
            test.chooseInitialResource(two, map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(0).getColor(),null);
        assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(1).getAmount(),1);
        assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(1).getColor(),Color.YELLOW);
        assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(2).getAmount(),0);
        assertEquals(test.getActivePlayers().get(1).getPersonalBoard().getDeposits().get(2).getColor(),null);

        try {
            test.chooseInitialResource(three, map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        assertEquals(test.getActivePlayers().get(2).getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(test.getActivePlayers().get(2).getPersonalBoard().getDeposits().get(0).getColor(),null);
        assertEquals(test.getActivePlayers().get(2).getPersonalBoard().getDeposits().get(1).getAmount(),1);
        assertEquals(test.getActivePlayers().get(2).getPersonalBoard().getDeposits().get(1).getColor(),Color.YELLOW);
        assertEquals(test.getActivePlayers().get(2).getPersonalBoard().getDeposits().get(2).getAmount(),0);
        assertEquals(test.getActivePlayers().get(2).getPersonalBoard().getDeposits().get(2).getColor(),null);

        map.put("pos2","big");
        map.put("res2","blue");

        try {
            test.chooseInitialResource(four, map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }
        assertEquals(test.getActivePlayers().get(3).getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(test.getActivePlayers().get(3).getPersonalBoard().getDeposits().get(0).getColor(),null);
        assertEquals(test.getActivePlayers().get(3).getPersonalBoard().getDeposits().get(1).getAmount(),1);
        assertEquals(test.getActivePlayers().get(3).getPersonalBoard().getDeposits().get(1).getColor(),Color.YELLOW);
        assertEquals(test.getActivePlayers().get(3).getPersonalBoard().getDeposits().get(2).getAmount(),1);
        assertEquals(test.getActivePlayers().get(3).getPersonalBoard().getDeposits().get(2).getColor(),Color.BLUE);

    }

    /**
     * Tests that a player can successfully swap his deposits during his turn.
     */
    @Test
    public void swapDepositTest() {
        Game test = new Game();
        test.createPlayer("one");
        test.start();
        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        for (int i=0; i<3; i++){
            deposits.add(new ResourceAmount(null, 0));
        }
        deposits.get(1).setAmount(1);
        deposits.get(2).setAmount(1);
        deposits.get(1).setColor(Color.YELLOW);
        deposits.get(2).setColor(Color.BLUE);
        test.getCurrentPlayer().getPersonalBoard().setDeposits(deposits);
        Map<String,String> map = new HashMap<>();
        map.put("source","big");
        map.put("dest","mid");
        try {
            test.swapDeposit("one",map);
        }catch (InvalidActionException e) {
            e.printStackTrace();
        }
        assertEquals(test.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getColor(),Color.BLUE);
        assertEquals(test.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getColor(),Color.YELLOW);
        assertEquals(test.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getAmount(),1);
        assertEquals(test.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getAmount(),1);
        assertEquals(test.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getAmount(),0);
        assertEquals(test.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getColor(),null);
    }

    /**
     * Tests that a player can't successfully swap his deposits if is not is turn.
     */
    @Test
    public void swapDepositTest1() {
        Game test = new Game();
        test.createPlayer("one");
        test.createPlayer("two");
        test.start();
        Player two = test.getActivePlayers().get(1);
        ArrayList<ResourceAmount> deposits = new ArrayList<>();
        for (int i=0; i<3; i++){
            deposits.add(new ResourceAmount(null, 0));
        }
        deposits.get(1).setAmount(1);
        deposits.get(2).setAmount(1);
        deposits.get(1).setColor(Color.YELLOW);
        deposits.get(2).setColor(Color.BLUE);
        test.getCurrentPlayer().getPersonalBoard().setDeposits(deposits);
        Map<String,String> map = new HashMap<>();
        map.put("source","big");
        map.put("dest","mid");
        try {
            test.swapDeposit(test.getActivePlayers().get(1).getName(),map);
        }catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(two.getPersonalBoard().getDeposits().get(1).getColor(),null);
            assertEquals(two.getPersonalBoard().getDeposits().get(2).getColor(),null);
            assertEquals(two.getPersonalBoard().getDeposits().get(1).getAmount(),0);
            assertEquals(two.getPersonalBoard().getDeposits().get(2).getAmount(),0);
            assertEquals(two.getPersonalBoard().getDeposits().get(0).getAmount(),0);
            assertEquals(two.getPersonalBoard().getDeposits().get(0).getColor(),null);
        }
    }

    /**
     * Tests that the player can successfully buy a card in his turn.
     */
    @Test
    public void buyTest() {
        Game test = new Game();
        test.createPlayer("one");
        test.start();
        Player one = test.getActivePlayers().get(0);
        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.BLUE,100);
        strongbox[1] = new ResourceAmount(Color.PURPLE,100);
        strongbox[2] = new ResourceAmount(Color.GREY,100);
        strongbox[3] = new ResourceAmount(Color.YELLOW,100);
        Map<String,String> map = new HashMap<>();
        map.put("res1","strongbox");
        map.put("res2","strongbox");
        map.put("res3","strongbox");
        map.put("res4","strongbox");
        map.put("row","0");
        map.put("column","0");
        map.put("ind","0");
        one.getPersonalBoard().setStrongbox(strongbox);
        assertEquals(one.getNumberDevelopCards(),0);
        try {
            test.buy("one",map);
            System.out.println("yes1");
            assertEquals(one.getNumberDevelopCards(),1);
        }catch (InvalidActionException e) {
            System.out.println("no1");
            e.printStackTrace();
            assertEquals(one.getNumberDevelopCards(),0);
            map.remove("res4");
            try {
                test.buy("one",map);
                System.out.println("yes2");
                assertEquals(one.getNumberDevelopCards(),1);
            }catch (InvalidActionException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Tests that the player can't successfully buy a card in his turn if he doesn't have resources.
     */
    @Test
    public void buyTest1() {
        Game test = new Game();
        test.createPlayer("one");
        test.start();
        Player one = test.getActivePlayers().get(0);
        ResourceAmount[] strongbox = new ResourceAmount[4];
        strongbox[0] = new ResourceAmount(Color.BLUE,0);
        strongbox[1] = new ResourceAmount(Color.PURPLE,0);
        strongbox[2] = new ResourceAmount(Color.GREY,0);
        strongbox[3] = new ResourceAmount(Color.YELLOW,0);
        Map<String,String> map = new HashMap<>();
        map.put("res1","strongbox");
        map.put("res2","strongbox");
        map.put("res3","strongbox");
        map.put("res4","strongbox");
        map.put("row","0");
        map.put("column","0");
        map.put("ind","1");
        one.getPersonalBoard().setStrongbox(strongbox);
        assertEquals(one.getNumberDevelopCards(),0);
        try {
            test.buy("one",map);
        }catch (InvalidActionException e) {
            e.printStackTrace();
            assertEquals(one.getNumberDevelopCards(),0);
            map.remove("res4");
            try {
                test.buy("one",map);
            }catch (InvalidActionException e1) {
                e1.printStackTrace();
                assertEquals(one.getNumberDevelopCards(),0);
            }
        }
    }



}
