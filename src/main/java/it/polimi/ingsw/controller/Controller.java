package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.exceptions.InvalidActionException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This class is represents the Contoller of the game.
 */
public class Controller implements PropertyChangeListener {

    /**
     * This attribute refers to the model that the Controller makes actions on.
     */
    private Game model;

    /**
     * This attribute represents the GameHandler that is listening to the Controller.
     */
    private PropertyChangeSupport gameHandlerListener = new PropertyChangeSupport(this);


    /**
     * Controller constructor.
     * @param model is the model that the Controller makes actions on.
     * @param gameHandler is the gameHandler that listens to the Controller.
     */
    public Controller(Game model, PropertyChangeListener gameHandler) {
        this.model = model;
        gameHandlerListener.addPropertyChangeListener(gameHandler);
    }

    /**
     * Start method: the controller checks the inputs and calls start on the Game.
     * @param map is the map with all the information.
     */
    private void start(Map<String, String> map){

        if (model.getPhase() == GamePhase.NOTSTARTED) {
            model.start();
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! You can not do this action in this phase!");
            error.put("method", "start");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }

    }

    /**
     * Buy method: the controller checks the inputs and calls buy on the Game.
     * @param map is the map with all the information.
     */
    private void buy(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));

                // NOTA: CONTROLLO SOLO RES1 (DEVO CONTROLLARE ANCHE RES2?)
                if (mapCopy.containsKey("row") && mapCopy.get("row") != null &&
                        Integer.parseInt(mapCopy.get("row")) >= 0 && Integer.parseInt(mapCopy.get("row")) <= 2 &&
                        mapCopy.containsKey("column") && mapCopy.get("column") != null &&
                        Integer.parseInt(mapCopy.get("column")) >= 0 && Integer.parseInt(mapCopy.get("column")) <= 3 &&
                        mapCopy.containsKey("ind") && mapCopy.get("ind") != null &&
                        Integer.parseInt(mapCopy.get("ind")) >= 0 && Integer.parseInt(mapCopy.get("ind")) <= 2 &&
                        mapCopy.containsKey("res1") && mapCopy.get("res1") != null) {
                    try {
                        model.buy(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", map.get("player"));
                        error.put("content", "There is an error in Buy: " + e.getMessage());
                        error.put("method", "buy");
                        gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "buy");
                    gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "buy");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "buy");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }

    }

    /**
     * Produce method: the controller checks the inputs and calls produce on the Game.
     * @param map is the map with all the information.
     */
    private void produce(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));

                if (mapCopy.containsKey("prod0") && mapCopy.containsKey("prod1") && mapCopy.containsKey("prod2") &&
                        mapCopy.containsKey("prod3") /*&& mapCopy.containsKey("prod4") && mapCopy.containsKey("prod5")*/ &&
                        mapCopy.get("prod0") != null && mapCopy.get("prod1") != null && mapCopy.get("prod2") != null &&
                        mapCopy.get("prod3") != null /*&& mapCopy.get("prod4") != null && mapCopy.get("prod5") != null*/ ) {
                    try {
                        model.produce(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", map.get("player"));
                        error.put("content", "There is an error in Produce: " + e.getMessage());
                        error.put("method", "produce");
                        gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "produce");
                    Gson gson = new Gson();
                    gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "produce");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "produce");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }

    }

    /**
     * fromMarket method: the controller checks the inputs and calls fromMarket on the Game.
     * @param map is the map with all the information.
     */
    private void fromMarket(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));

                if (mapCopy.containsKey("row")) {
                    if (mapCopy.size() >= 5) {
                        try {
                            model.fromMarket(model.getCurrentPlayer().getName(), mapCopy);
                        } catch (InvalidActionException e) {
                            //e.printStackTrace();
                            Map<String, String> error = new HashMap<>();
                            error.put("action", "error");
                            error.put("player", map.get("player"));
                            error.put("content", "There is an error in fromMarket: " + e.getMessage());
                            error.put("method", "fromMarket");
                            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                        }
                    }
                } else if (mapCopy.containsKey("col")) {
                    if (mapCopy.size() >= 4) {
                        try {
                            model.fromMarket(model.getCurrentPlayer().getName(), mapCopy);
                        } catch (InvalidActionException e) {
                            //e.printStackTrace();
                            Map<String, String> error = new HashMap<>();
                            error.put("action", "error");
                            error.put("player", map.get("player"));
                            error.put("content", "There is an error in fromMarket: " + e.getMessage());
                            error.put("method", "fromMarket");
                            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                        }
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "fromMarket");
                    gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "fromMarket");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "fromMarket");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }

    }

    /**
     * swapDeposits method: the controller checks the inputs and calls swapDeposits on the Game.
     * @param map is the map with all the information.
     */
    private void swapDeposits(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));

                mapCopy.remove("player");

                if (mapCopy.size() == 2 &&
                        mapCopy.containsKey("source") && mapCopy.containsKey("dest") &&
                        mapCopy.get("source") != null && mapCopy.get("dest") != null) {
                    try {
                        model.swapDeposit(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", map.get("player"));
                        error.put("content", "There is an error in swapDeposits: " + e.getMessage());
                        error.put("method", "swapDeposits");
                        gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "swapDeposits");
                    gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "swapDeposits");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "swapDeposits");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }

    }

    /**
     * chooseLeaders method: the controller checks the inputs and calls chooseLeaders on the Game.
     * @param map is the map with all the information.
     */
    private void chooseLeaders(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.LEADER) {

                // to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));

                if (  /*mapCopy.size() == 2 && */
                        mapCopy.containsKey("ind1") && mapCopy.containsKey("ind2") &&
                                Integer.parseInt(mapCopy.get("ind1")) >= 1 && Integer.parseInt(mapCopy.get("ind1")) <= 4 &&
                                Integer.parseInt(mapCopy.get("ind2")) >= 1 && Integer.parseInt(mapCopy.get("ind2")) <= 4) {
                    try {
                        model.chooseLeaders(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", map.get("player"));
                        error.put("content", "There is an error in chooseLeaders: " + e.getMessage());
                        error.put("method", "chooseLeaders");
                        gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "chooseLeaders");
                    gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "chooseLeaders");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        }  else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "chooseLeaders");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }

    }

    /**
     * chooseResources method: the controller checks the inputs and calls chooseResources on the Game.
     * @param map is the map with all the information.
     */
    private void chooseResources(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.RESOURCE) {

                // to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));

                // CONTROLLO SOLO SE HA RES1 E POS1 (NON CONTROLLO RES2 E POS2)
                if (mapCopy.containsKey("res1") && mapCopy.containsKey("pos1") &&
                        mapCopy.get("res1") != null && mapCopy.get("pos1") != null) {
                    try {
                        mapCopy.remove("player");
                        model.chooseInitialResource(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", map.get("player").toLowerCase());
                        error.put("content", "There is an error in chooseResources: " + e.getMessage());
                        error.put("method", "chooseResources");
                        gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", map.get("player").toLowerCase());
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "chooseResources");
                    gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "chooseResources");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "chooseResources");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }
    }

    /**
     * ActivateLeader method: the controller checks the inputs and calls activateLeader on the Game.
     * @param map is the map with all the information.
     */
    private void activateLeader(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));

                if (mapCopy.size() >= 1 && mapCopy.get("pos") != null) {

                    int pos = Integer.parseInt(mapCopy.get("pos"));

                    if (pos >= 0 && pos <= 1) {
                        try {
                            model.activateLeader(model.getCurrentPlayer().getName(), pos);
                        } catch (InvalidActionException e) {
                            //e.printStackTrace();
                            Map<String, String> error = new HashMap<>();
                            error.put("action", "error");
                            error.put("player", map.get("player"));
                            error.put("content", "There is an error in activateLeader: " + e.getMessage());
                            error.put("method", "activateLeader");
                            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                        }
                    } else {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", map.get("player"));
                        error.put("content", "Attention! You did not type the index correctly! Try again!");
                        error.put("method", "activateLeader");
                        gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "activateLeader");
                    gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "activateLeader");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "activateLeader");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }
    }

    /**
     * ActivateLeader method: the controller checks the inputs and calls activateLeader on the Game.
     * @param map is the map with all the information.
     */
    private void discardLeader(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));

                if (mapCopy.size() >= 1 && mapCopy.get("pos") != null) {

                    int pos = Integer.parseInt(mapCopy.get("pos"));

                    if (pos >= 0 && pos <= 1) {
                        try {
                            model.discardLeader(model.getCurrentPlayer().getName(), pos);
                        } catch (InvalidActionException e) {
                            //e.printStackTrace();
                            Map<String, String> error = new HashMap<>();
                            error.put("action", "error");
                            error.put("player", map.get("player"));
                            error.put("content", "There is an error in discardLeader: " + e.getMessage());
                            error.put("method", "discardLeader");
                            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                        }
                    } else {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", map.get("player"));
                        error.put("content", "Attention! You did not type the index correctly! Try again!");
                        error.put("method", "discardLeader");
                        gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "discardLeader");
                    gameHandlerListener.firePropertyChange(error.get("action"), null, error);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "discardLeader");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        } else  {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "discardLeader");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }

    }

    /**
     * EndTurn method: the controller checks the inputs and calls endTurn on the Game.
     * @param map is the map with all the information.
     */
    public void endTurn(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                try {
                    model.endTurn(model.getCurrentPlayer().getName());
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "endturn");
                gameHandlerListener.firePropertyChange(error.get("action"), null, error);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "endturn");
            gameHandlerListener.firePropertyChange(error.get("action"), null, error);
        }

    }

    /**
     * @see PropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        Map<String, String> message = ( Map<String, String> ) evt.getNewValue();
        String actionName = evt.getPropertyName().toUpperCase();

        switch (actionName){
            case "START":
                start(message);
                break;
            case "BUY":
                message.remove("action");
                buy(message);
                break;
            case "PRODUCE":
                message.remove("action");
                produce(message);
                break;
            case "MARKET":
                message.remove("action");
                fromMarket(message);
                break;
            case "SWAP":
                message.remove("action");
                swapDeposits(message);
                break;
            case "CHOOSELEADERS":
                message.remove("action");
                chooseLeaders(message);
                break;
            case "CHOOSERESOURCES":
                message.remove("action");
                chooseResources(message);
                break;
            case "ACTIVATE":
                message.remove("action");
                activateLeader(message);
                break;
            case "DISCARD":
                message.remove("action");
                discardLeader(message);
                break;
            case "ENDTURN":
                //message.remove("action");
                endTurn(message);
                break;

            default:
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.get("player"));
                error.put("content", "Illegal action! Try typing again!");
                gameHandlerListener.firePropertyChange("error", null, error);
        }
    }

}
