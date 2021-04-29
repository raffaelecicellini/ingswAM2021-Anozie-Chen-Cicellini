package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class ControllerTest {


    /**
     * Testing Controller: START, CHOOSELEADERS & CHOOSERESOURCES
     */
    @Test
    public void ControllerTest1(){

        PropertyChangeListener gameHandlerListener = evt -> {
            Gson gson = new Gson();
            String message = gson.toJson(evt.getNewValue());
            System.out.println(message);
        };

        Game game = new Game();
        game.createPlayer("one");
        game.createPlayer("two");
        game.createPlayer("three");
        Controller controller = new Controller(game, gameHandlerListener);
        game.setListener(gameHandlerListener);

        Map<String, String> map = new HashMap<>();
        map.put("action", "start");
        map.put("player", "one");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        /*for (int i = 0; i < game.getActivePlayers().size(); i++){
            System.out.println("Player " + i + ": " + game.getActivePlayers().get(i).getName());
        }*/

        map.clear();
        map.put("action", "chooseleaders");
        map.put("player", game.getActivePlayers().get(0).getName());
        map.put("ind1", "1");
        map.put("ind2", "3");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        map.clear();
        map.put("action", "chooseleaders");
        map.put("player", game.getActivePlayers().get(1).getName());
        map.put("ind1", "2");
        map.put("ind2", "4");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        map.clear();
        map.put("action", "chooseleaders");
        map.put("player", game.getActivePlayers().get(2).getName());
        map.put("ind1", "1");
        map.put("ind2", "4");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        map.clear();
        map.put("action", "chooseResources");
        map.put("player", game.getActivePlayers().get(1).getName());
        map.put("res1", "blue");
        map.put("pos1", "small");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        map.clear();
        map.put("action", "chooseResources");
        map.put("player", game.getActivePlayers().get(2).getName());
        map.put("res1", "blue");
        map.put("pos1", "small");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

    }

    /**
     * Testing Controller: BUY, PRODUCE, FROMMAKET, SWAP, ENDTURN
     */
    @Test
    public void ControllerTest2(){

        PropertyChangeListener gameHandlerListener = evt -> {
            Gson gson = new Gson();
            String message = gson.toJson(evt.getNewValue());
            System.out.println(message);
        };

        Game game = new Game();
        game.createPlayer("one");
        game.createPlayer("two");
        game.createPlayer("three");
        Controller controller = new Controller(game, gameHandlerListener);
        game.setListener(gameHandlerListener);

        Map<String, String> map = new HashMap<>();
        map.put("action", "start");
        map.put("player", "one");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        for (int i = 0; i < game.getActivePlayers().size(); i++){
            System.out.println("Player " + i + ": " + game.getActivePlayers().get(i).getName());
        }

        game.setPhase(GamePhase.FULLGAME);

        map.clear();
        map.put("action", "buy");
        map.put("player", game.getCurrentPlayer().getName());
        map.put("row", "0");
        map.put("column", "1");
        map.put("ind", "1");
        map.put("res1", "strongbox");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        map.clear();
        map.put("action", "produce");
        map.put("player", game.getCurrentPlayer().getName());
        map.put("prod0", "no");
        map.put("prod1", "no");
        map.put("prod2", "no");
        map.put("prod3", "no");
        map.put("prod4", "no");
        map.put("prod5", "no");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        map.clear();
        map.put("action", "market");
        map.put("player", game.getCurrentPlayer().getName());
        map.put("col", "2");
        map.put("res1", "big");
        map.put("res2", "mid");
        map.put("res3", "small");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        map.clear();
        map.put("action", "swap");
        map.put("player", game.getCurrentPlayer().getName());
        map.put("source", "small");
        map.put("dest", "big");

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

        map.clear();
        map.put("action", "endturn");
        map.put("player", game.getCurrentPlayer().getName());

        controller.propertyChange(new PropertyChangeEvent(gameHandlerListener, map.get("action"), null, map));

    }

}