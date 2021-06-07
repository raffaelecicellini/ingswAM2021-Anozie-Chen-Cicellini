package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.MarketMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.ResourceMessage;
import it.polimi.ingsw.messages.SwapMessage;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SoloGameTest {

    /**
     * Testing a Solo Game
     */
    @Test
    public void soloGameTest1() {
        SoloGame soloGame = new SoloGame();
        soloGame.createPlayer("player");
        soloGame.start();

        // setting soloActions
        SoloActions soloActions = new SoloActions();
        ActionToken[] actionTokens = new ActionToken[7];
        actionTokens[0] = new DiscardToken(Color.BLUE, 2);
        actionTokens[1] = new DiscardToken(Color.GREEN, 2);
        actionTokens[2] = new DiscardToken(Color.PURPLE, 2);
        actionTokens[3] = new DiscardToken(Color.YELLOW, 2);
        actionTokens[4] = new MoveToken(2);
        actionTokens[5] = new MoveToken(2);
        actionTokens[6] = new MoveandShuffleToken(1);
        soloActions.setTokens(actionTokens);
        soloGame.setSoloActions(soloActions);

        //soloGame.printSoloActions();

        ArrayList<LeaderCard> leaders1 = new ArrayList<>();
        leaders1.add(new ResourceLeader(3, "Resource", false, false, new ResourceAmount(Color.YELLOW, 5), Color.GREY, 0));
        leaders1.add(new ResourceLeader(3, "Resource", false, false, new ResourceAmount(Color.PURPLE, 5), Color.BLUE, 0));
        leaders1.add(new TwoAndOneLeader(5, "TwoAndOne", false, false, Color.BLUE, Color.YELLOW, Color.GREY, 0));
        leaders1.add(new TwoAndOneLeader(5, "TwoAndOne", false, false, Color.PURPLE, Color.GREEN, Color.YELLOW, 0));

        soloGame.getCurrentPlayer().clearLeaders();
        soloGame.getCurrentPlayer().receiveLeaders(leaders1);

        Map<String, String> map = new HashMap<>();
        map.put("ind1", "2");
        map.put("ind2", "1");

        Message message = new ResourceMessage(map);
        try {
            soloGame.chooseLeaders("", message);
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
        soloGame.market.setMarket(marbles);


        map.clear();
        map.put("row", "2");
        map.put("pos1", "small");
        map.put("pos2", "mid");
        map.put("pos3", "big");
        map.put("pos4", "big");
        map.put("action", "market");
        map.put("player", "test");


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.BLUE, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getAmount());
        assertNull(soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(0, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.PURPLE, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getAmount());

        assertEquals(0, soloGame.getCurrentPlayer().getPersonalBoard().getPosition());
        assertEquals(0, soloGame.getBlackCross().getPosition());
        assertTrue(soloGame.doneMandatory);

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        // SECOND TURN

        map.clear();
        map.put("source", "small");
        map.put("dest", "big");
        map.put("action", "swap");
        map.put("player", "test");

        try {
            soloGame.swapDeposit("", new SwapMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.PURPLE, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getAmount());
        assertNull(soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(0, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.BLUE, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(1, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getAmount());
        assertFalse(soloGame.doneMandatory);

        map.clear();
        map.put("col", "4");
        map.put("pos1", "mid");
        map.put("pos2", "big");
        map.put("pos3", "big");
        map.put("action", "market");
        map.put("player", "test");

        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.PURPLE, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.YELLOW, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(1, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.BLUE, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(2, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getAmount());

        assertEquals(0, soloGame.getCurrentPlayer().getPersonalBoard().getPosition());
        assertEquals(0, soloGame.getBlackCross().getPosition());
        assertTrue(soloGame.doneMandatory);


        //first.getPersonalBoard().setPosition(24);

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        // THIRD TURN

        map.clear();
        map.put("col", "4");
        map.put("pos1", "big");
        map.put("pos2", "big");
        map.put("pos3", "big");
        map.put("action", "market");
        map.put("player", "test");

        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.PURPLE, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.YELLOW, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(1, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.BLUE, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(3, soloGame.getCurrentPlayer().getPersonalBoard().getDeposits().get(2).getAmount());

        assertEquals(0, soloGame.getCurrentPlayer().getPersonalBoard().getPosition());
        assertEquals(1, soloGame.getBlackCross().getPosition());
        assertTrue(soloGame.doneMandatory);

        try {
            soloGame.discardLeader("", 1);
            soloGame.discardLeader("", 0);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(2, soloGame.getCurrentPlayer().getPersonalBoard().getPosition());
    }

    /**
     * Testing endTurn in SoloGame - no more develop cards
     */
    @Test
    public void soloGameTest2() {
        SoloGame soloGame = new SoloGame();
        soloGame.createPlayer("player");
        soloGame.start();

        // setting soloActions
        SoloActions soloActions = new SoloActions();
        ActionToken[] actionTokens = new ActionToken[7];
        actionTokens[0] = new DiscardToken(Color.BLUE, 2);
        actionTokens[1] = new DiscardToken(Color.BLUE, 2);
        actionTokens[2] = new DiscardToken(Color.BLUE, 2);
        actionTokens[3] = new DiscardToken(Color.BLUE, 2);
        actionTokens[4] = new DiscardToken(Color.BLUE, 2);
        actionTokens[5] = new DiscardToken(Color.BLUE, 2);
        actionTokens[6] = new DiscardToken(Color.BLUE, 2);
        soloActions.setTokens(actionTokens);
        soloGame.setSoloActions(soloActions);

        ArrayList<Marble> marbles = new ArrayList<>();
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        soloGame.market.setMarket(marbles);


        Map<String, String> map = new HashMap<>();
        map.put("col", "4");
        map.put("pos1", "small");
        map.put("pos2", "mid");
        map.put("pos3", "big");
        map.put("action", "market");
        map.put("player", "test");

        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

    }

    /**
     * Testing endTurn in SoloGame - black cross at the end
     */
    @Test
    public void soloGameTest3() {
        SoloGame soloGame = new SoloGame();
        soloGame.createPlayer("player");
        soloGame.start();

        // setting soloActions
        SoloActions soloActions = new SoloActions();
        ActionToken[] actionTokens = new ActionToken[7];
        actionTokens[0] = new MoveToken(2);
        actionTokens[1] = new MoveToken(2);
        actionTokens[2] = new MoveToken(2);
        actionTokens[3] = new MoveToken(2);
        actionTokens[4] = new MoveToken(2);
        actionTokens[5] = new MoveToken(2);
        actionTokens[6] = new MoveToken(2);
        soloActions.setTokens(actionTokens);
        soloGame.setSoloActions(soloActions);

        ArrayList<Marble> marbles = new ArrayList<>();
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        marbles.add(WhiteMarble.getInstance());
        soloGame.market.setMarket(marbles);


        Map<String, String> map = new HashMap<>();
        map.put("col", "4");
        map.put("pos1", "small");
        map.put("pos2", "mid");
        map.put("pos3", "big");
        map.put("action", "market");
        map.put("player", "test");

        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


        try {
            soloGame.fromMarket("", new MarketMessage(map));
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }


    }


}
