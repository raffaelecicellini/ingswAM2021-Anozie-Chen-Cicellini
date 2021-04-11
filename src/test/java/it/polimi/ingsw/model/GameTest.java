package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

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
}
