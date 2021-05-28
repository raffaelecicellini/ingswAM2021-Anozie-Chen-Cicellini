package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.notifications.Source;
import it.polimi.ingsw.notifications.SourceListener;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * This class is represents the Contoller of the game.
 */
public class Controller implements SourceListener {

    /**
     * This attribute refers to the model that the Controller makes actions on.
     */
    private Game model;

    /**
     * This attribute represents the GameHandler that is listening to the Controller.
     */
    private Source gameHandlerListener = new Source();


    /**
     * Controller constructor.
     * @param model is the model that the Controller makes actions on.
     * @param gameHandler is the gameHandler that listens to the Controller.
     */
    public Controller(Game model, SourceListener gameHandler) {
        this.model = model;
        gameHandlerListener.addListener(gameHandler);
    }

    /**
     * Start method: the controller checks the inputs and calls start on the Game.
     */
    private void start(){
        if (model.getPhase() == GamePhase.NOTSTARTED) {
            model.start();
        }
    }

    /**
     * chooseLeaders method: the controller checks the inputs and calls chooseLeaders on the Game.
     * @param message is the message that contains all the info about the action.
     */
    private void chooseLeaders(LeaderMessage message) {
        //private void chooseLeaders(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {
            //if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.LEADER) {

                // to lowercase the entire map
                /*Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));*/

                if (message.getLeader(1) >= 1 && message.getLeader(1) <= 4 &&
                        message.getLeader(2) >= 1 && message.getLeader(2) <= 4) {
                    /*if (  *//*mapCopy.size() == 2 && *//*
                        mapCopy.containsKey("ind1") && mapCopy.containsKey("ind2") &&
                                Integer.parseInt(mapCopy.get("ind1")) >= 1 && Integer.parseInt(mapCopy.get("ind1")) <= 4 &&
                                Integer.parseInt(mapCopy.get("ind2")) >= 1 && Integer.parseInt(mapCopy.get("ind2")) <= 4) {*/
                    try {
                        //mapCopy.remove("player");
                        //mapCopy.remove("action");
                        model.chooseLeaders(model.getCurrentPlayer().getName(), message);
                        //model.chooseLeaders(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        //error.put("player", map.get("player"));
                        error.put("content", "There is an error in chooseLeaders: " + e.getMessage());
                        error.put("method", "chooseLeaders");
                        ErrorAnswer errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "chooseLeaders");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "chooseLeaders");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        }  else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            //error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "chooseLeaders");
            ErrorAnswer errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * chooseResources method: the controller checks the inputs and calls chooseResources on the Game.
     * @param message is the message that contains all the info about the action.
     */
    private void chooseResources(ResourceMessage message) {
        //private void chooseResources(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {
            //if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.RESOURCE) {

                // to lowercase the entire map
                /*Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));*/

                if (message.getResource(1) != null && message.getPosition(1) != null) {
                /*if (mapCopy.containsKey("res1") && mapCopy.containsKey("pos1") &&
                        mapCopy.get("res1") != null && mapCopy.get("pos1") != null) {*/
                    try {
                        //mapCopy.remove("player");
                        //mapCopy.remove("action");
                        model.chooseInitialResource(model.getCurrentPlayer().getName(), message);
                        //model.chooseInitialResource(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        //error.put("player", map.get("player"));
                        error.put("content", "There is an error in chooseResources: " + e.getMessage());
                        error.put("method", "chooseResources");
                        ErrorAnswer errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "chooseResources");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "chooseResources");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            //error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "chooseResources");
            ErrorAnswer errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }
    }

    /**
     * Buy method: the controller checks the inputs and calls buy on the Game.
     * @param message is the message that contains all the info about the action.
     */
    private void buy(BuyMessage message) {
    //private void buy(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {
            //if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                /*Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));*/

                // NOTA: CONTROLLO SOLO RES1
                if (message.isRow() && message.getRow() >= 0 && message.getRow() <= 2 &&
                        message.isCol() && message.getCol() >= 0 && message.getCol() <= 3 &&
                        message.getSlot() >= 0 && message.getSlot() <= 2) {
                /*if (mapCopy.containsKey("row") && mapCopy.get("row") != null &&
                        Integer.parseInt(mapCopy.get("row")) >= 0 && Integer.parseInt(mapCopy.get("row")) <= 2 &&
                        mapCopy.containsKey("column") && mapCopy.get("column") != null &&
                        Integer.parseInt(mapCopy.get("column")) >= 0 && Integer.parseInt(mapCopy.get("column")) <= 3 &&
                        mapCopy.containsKey("ind") && mapCopy.get("ind") != null &&
                        Integer.parseInt(mapCopy.get("ind")) >= 0 && Integer.parseInt(mapCopy.get("ind")) <= 2 &&
                        mapCopy.containsKey("res1") && mapCopy.get("res1") != null) {*/
                        try {
                            //mapCopy.remove("player");
                            //mapCopy.remove("action");
                            model.buy(model.getCurrentPlayer().getName(), message);
                            //model.buy(model.getCurrentPlayer().getName(), mapCopy);
                        } catch (InvalidActionException e) {
                            Map<String, String> error = new HashMap<>();
                            error.put("action", "error");
                            error.put("player", message.getPlayer());
                            //error.put("player", map.get("player"));
                            error.put("content", "There is an error in Buy: " + e.getMessage());
                            error.put("method", "buy");
                            ErrorAnswer errorAnswer = new ErrorAnswer(error);
                            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                        }
                    } else {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        //error.put("player", map.get("player"));
                        error.put("content", "Attention! You did not type correctly! Try again!");
                        error.put("method", "buy");
                        ErrorAnswer errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You can not do this action in this phase!");
                    error.put("method", "buy");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! It is not your turn!");
                error.put("method", "buy");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
    }

    /**
     * Produce method: the controller checks the inputs and calls produce on the Game.
     * @param message is the message that contains all the info about the action.
     */
    private void produce(ProductionMessage message) {
    //private void produce(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equalsIgnoreCase(message.getPlayer())) {
        //if (model.getCurrentPlayer().getName().equalsIgnoreCase(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                /*// to lowercase the entire map
                Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));*/

                if (message.isSelected(0) || message.isSelected(1) || message.isSelected(2) ||
                        message.isSelected(3)  || message.isSelected(4) || message.isSelected(5)) {
                /*if (mapCopy.containsKey("prod0") && mapCopy.containsKey("prod1") && mapCopy.containsKey("prod2") &&
                        mapCopy.containsKey("prod3") *//*&& mapCopy.containsKey("prod4") && mapCopy.containsKey("prod5")*//* &&
                        mapCopy.get("prod0") != null && mapCopy.get("prod1") != null && mapCopy.get("prod2") != null &&
                        mapCopy.get("prod3") != null *//*&& mapCopy.get("prod4") != null && mapCopy.get("prod5") != null*//* ) {*/
                    try {
                        //mapCopy.remove("player");
                        //mapCopy.remove("action");
                        model.produce(model.getCurrentPlayer().getName(), message);
                        //model.produce(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        //error.put("player", map.get("player"));
                        error.put("content", "There is an error in Produce: " + e.getMessage());
                        error.put("method", "produce");
                        ErrorAnswer errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "produce");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "produce");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            //error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "produce");
            ErrorAnswer errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * fromMarket method: the controller checks the inputs and calls fromMarket on the Game.
     * @param message is the message that contains all the info about the action.
     */
    private void fromMarket(MarketMessage message) {
    //private void fromMarket(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {
        //if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                /*Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));*/

                if (message.isRow() && message.size() >= 5) {
                //if (mapCopy.containsKey("row")) {
                    //if (mapCopy.size() >= 5) {
                        try {
                            //mapCopy.remove("player");
                            //mapCopy.remove("action");
                            model.fromMarket(model.getCurrentPlayer().getName(), message);
                            //model.fromMarket(model.getCurrentPlayer().getName(), mapCopy);
                        } catch (InvalidActionException e) {
                            //e.printStackTrace();
                            Map<String, String> error = new HashMap<>();
                            error.put("action", "error");
                            error.put("player", message.getPlayer());
                            //error.put("player", map.get("player"));
                            error.put("content", "There is an error in fromMarket: " + e.getMessage());
                            error.put("method", "fromMarket");
                            ErrorAnswer errorAnswer = new ErrorAnswer(error);
                            gameHandlerListener.fireUpdates(error.get("action"), errorAnswer);
                        }
                    //}
                } else
                    if (message.isCol() && message.size() >= 4) {
                        //if (mapCopy.containsKey("col")) {
                        //if (mapCopy.size() >= 4) {
                        try {
                            //mapCopy.remove("player");
                            //mapCopy.remove("action");
                            model.fromMarket(model.getCurrentPlayer().getName(), message);
                            //model.fromMarket(model.getCurrentPlayer().getName(), mapCopy);
                        } catch (InvalidActionException e) {
                            //e.printStackTrace();
                            Map<String, String> error = new HashMap<>();
                            error.put("action", "error");
                            error.put("player", message.getPlayer());
                            //error.put("player", map.get("player"));
                            error.put("content", "There is an error in fromMarket: " + e.getMessage());
                            error.put("method", "fromMarket");
                            ErrorAnswer errorAnswer = new ErrorAnswer(error);
                            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                        }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "fromMarket");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "fromMarket");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            //error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "fromMarket");
            ErrorAnswer errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * swapDeposits method: the controller checks the inputs and calls swapDeposits on the Game.
     * @param message is the message that contains all the info about the action.
     */
    private void swapDeposits(SwapMessage message) {
    //private void swapDeposits(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {
        //if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                /*Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));*/

                //mapCopy.remove("player");

                if (message.size() == 2 &&
                        message.getSource() != null && message.getDest() != null) {
                //if (mapCopy.size() == 2 &&
                        //mapCopy.containsKey("source") && mapCopy.containsKey("dest") &&
                        //mapCopy.get("source") != null && mapCopy.get("dest") != null) {
                    try {
                        //mapCopy.remove("player");
                        //mapCopy.remove("action");
                        model.swapDeposit(model.getCurrentPlayer().getName(), message);
                        //model.swapDeposit(model.getCurrentPlayer().getName(), mapCopy);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        //error.put("player", map.get("player"));
                        error.put("content", "There is an error in swapDeposits: " + e.getMessage());
                        error.put("method", "swapDeposits");
                        ErrorAnswer errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "swapDeposits");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "swapDeposits");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            //error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "swapDeposits");
            ErrorAnswer errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * ActivateLeader method: the controller checks the inputs and calls activateLeader on the Game.
     * @param message is the message that contains all the info about the action.
     */
    private void activateLeader(LeaderActionAnswer message) {
    //private void activateLeader(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {
        // if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                /*Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));*/

                if (message.size() >= 1 && message.getIndex() >= 0 && message.getIndex() <= 1) {
                    //if (mapCopy.size() >= 1 && mapCopy.get("pos") != null) {

                    //int pos = Integer.parseInt(mapCopy.get("pos"));

                    //if (pos >= 0 && pos <= 1) {
                    try {
                        //mapCopy.remove("player");
                        //mapCopy.remove("action");
                        model.activateLeader(model.getCurrentPlayer().getName(), message.getIndex());
                        //model.activateLeader(model.getCurrentPlayer().getName(), pos);
                    } catch (InvalidActionException e) {
                        //e.printStackTrace();
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        //error.put("player", map.get("player"));
                        error.put("content", "There is an error in activateLeader: " + e.getMessage());
                        error.put("method", "activateLeader");
                        ErrorAnswer errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type the index correctly! Try again!");
                    error.put("method", "activateLeader");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
                /*} else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "activateLeader");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    //gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer); }*/
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "activateLeader");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            //error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "activateLeader");
            ErrorAnswer errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }
    }

    /**
     * ActivateLeader method: the controller checks the inputs and calls activateLeader on the Game.
     * @param message is the message that contains all the info about the action.
     */
    private void discardLeader(LeaderActionMessage message) {
    //private void discardLeader(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {
        //if (model.getCurrentPlayer().getName().equals(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // to lowercase the entire map
                /*Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                        e1 -> e1.getKey().toLowerCase(),
                        e1 -> e1.getValue().toLowerCase()));*/

                if (message.size() >= 1 && message.getIndex() >= 0 && message.getIndex() <= 1) {
                //if (mapCopy.size() >= 1 && mapCopy.get("pos") != null) {

                    //int pos = Integer.parseInt(mapCopy.get("pos"));

                    //if (pos >= 0 && pos <= 1) {
                        try {
                            //mapCopy.remove("player");
                            //mapCopy.remove("action");
                            model.discardLeader(model.getCurrentPlayer().getName(), message.getIndex());
                            //model.discardLeader(model.getCurrentPlayer().getName(), pos);
                        } catch (InvalidActionException e) {
                            //e.printStackTrace();
                            Map<String, String> error = new HashMap<>();
                            error.put("action", "error");
                            error.put("player", message.getPlayer());
                            //error.put("player", map.get("player"));
                            error.put("content", "There is an error in discardLeader: " + e.getMessage());
                            error.put("method", "discardLeader");
                            ErrorAnswer errorAnswer = new ErrorAnswer(error);
                            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                        }
                    } else {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        //error.put("player", map.get("player"));
                        error.put("content", "Attention! You did not type the index correctly! Try again!");
                        error.put("method", "discardLeader");
                        ErrorAnswer errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                /*} else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    //error.put("player", map.get("player"));
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "discardLeader");
                    ErrorAnswer errorAnswer = new ErrorAnswer(error);
                    //gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }*/
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "discardLeader");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else  {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            //error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "discardLeader");
            ErrorAnswer errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * EndTurn method: the controller checks the inputs and calls endTurn on the Game.
     * @param message is the message that contains all the info about the action.
     */
     public void endTurn(EndTurnMessage message) {
     //public void endTurn(Map<String, String> map) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {
        //if (model.getCurrentPlayer().getName().equalsIgnoreCase(map.get("player"))) {

            if (model.getPhase() == GamePhase.FULLGAME) {
                try {
                    //System.out.println("Passing control to model...");
                    model.endTurn(message.getPlayer());
                    //model.endTurn(map.get("player"));
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Wrong phase!");
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                //error.put("player", map.get("player"));
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "endturn");
                ErrorAnswer errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            System.out.println("Not your turn!");
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            //error.put("player", map.get("player"));
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "endturn");
            ErrorAnswer errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }



    @Override
    //public void update(String propertyName, Map<String, String> value) {
    public void update(String propertyName, Message message) {
        switch (propertyName.toUpperCase()){
            case "START":
                start();
                break;
            case "BUY":
                buy((BuyMessage) message);
                //value.remove("action");
                //buy(value);
                break;
            case "PRODUCE":
                produce((ProductionMessage) message);
                //value.remove("action");
                //produce(value);
                break;
            case "MARKET":
                fromMarket((MarketMessage) message);
                //value.remove("action");
                //fromMarket(value);
                break;
            case "SWAP":
                swapDeposits((SwapMessage) message);
                //value.remove("action");
                //swapDeposits(value);
                break;
            case "CHOOSELEADERS":
                chooseLeaders((LeaderMessage) message);
                //value.remove("action");
                //chooseLeaders(value);
                break;
            case "CHOOSERESOURCES":
                chooseResources((ResourceMessage) message);
                //value.remove("action");
                //chooseResources(value);
                break;
            case "ACTIVATE":
                activateLeader((LeaderActionAnswer) message);
                //value.remove("action");
                //activateLeader(value);
                break;
            case "DISCARD":
                discardLeader((LeaderActionMessage) message);
                //value.remove("action");
                //discardLeader(value);
                break;
            case "ENDTURN":
                endTurn((EndTurnMessage) message);
                //message.remove("action");
                //endTurn(value);
                break;
            case "DISCONNECT":
                model.setPhase(GamePhase.ENDED);
                break;
            default:
                Map<String, String> map = new HashMap<>();
                map.put("action", "error");
                map.put("player", message.getPlayer());
                //map.put("player", value.get("player"));
                map.put("content", "Illegal action! Try typing again!");
                Message error = new ErrorAnswer(map);
                gameHandlerListener.fireUpdates(map.get("action"), error);
        }
    }
}
