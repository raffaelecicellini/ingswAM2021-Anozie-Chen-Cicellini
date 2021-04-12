package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SoloGameTest {
    /**
     * Testing a Solo Game
     */
    @Test
    public void soloGameTest(){
        Game soloGame = new SoloGame();
        soloGame.createPlayer("player");
        soloGame.start();
        soloGame.printSoloActions();
        Player first = soloGame.getCurrentPlayer();

        ArrayList<LeaderCard> leaders1 = new ArrayList<>();
        leaders1.add(new ResourceLeader(3, "Resource", false, false, new ResourceAmount(Color.YELLOW, 5), Color.GREY));
        leaders1.add(new ResourceLeader(3, "Resource", false, false, new ResourceAmount(Color.PURPLE, 5), Color.BLUE));
        leaders1.add(new TwoAndOneLeader(5, "TwoAndOne", false, false, Color.BLUE, Color.YELLOW, Color.GREY));
        leaders1.add(new TwoAndOneLeader(5, "TwoAndOne", false, false, Color.PURPLE, Color.GREEN, Color.YELLOW));

        first.clearLeaders();
        first.receiveLeaders(leaders1);
        try {
            first.chooseLeader(3, 4);
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


        Map<String, String> map = new HashMap<>();
        map.put("row", "2");
        map.put("pos1", "small");
        map.put("pos2", "mid");
        map.put("pos3", "big");
        map.put("pos4", "big");


        try {
            soloGame.fromMarket("", map);
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
        assertFalse(soloGame.doneMandatory);

        map.clear();
        map.put("col", "4");
        map.put("pos1", "mid");
        map.put("pos2", "big");
        map.put("pos3", "big");

        try {
            soloGame.fromMarket(first.getName(), map);
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

        assertEquals(Color.PURPLE, first.getPersonalBoard().getDeposits().get(0).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(0).getAmount());
        assertEquals(Color.YELLOW, first.getPersonalBoard().getDeposits().get(1).getColor());
        assertEquals(1, first.getPersonalBoard().getDeposits().get(1).getAmount());
        assertEquals(Color.BLUE, first.getPersonalBoard().getDeposits().get(2).getColor());
        assertEquals(2, first.getPersonalBoard().getDeposits().get(2).getAmount());
        assertEquals(0, first.getPersonalBoard().getPosition());
        assertTrue(soloGame.doneMandatory);

        for (LeaderCard l : first.getLeaders()) {
            l.setActive(true);
        }


        first.getPersonalBoard().setPosition(24);

        try {
            soloGame.endTurn("");
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

    }
}
