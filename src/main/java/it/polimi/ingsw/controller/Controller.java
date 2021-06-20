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
     *
     * @param model       is the model that the Controller makes actions on.
     * @param gameHandler is the gameHandler that listens to the Controller.
     */
    public Controller(Game model, SourceListener gameHandler) {
        this.model = model;
        gameHandlerListener.addListener(gameHandler);
    }

    /**
     * Start method: the controller checks the inputs and calls start on the Game.
     */
    private void start() {
        if (model.getPhase() == GamePhase.NOTSTARTED) {
            model.start();
        }
    }

    /**
     * chooseLeaders method: the controller checks the inputs and calls chooseLeaders on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    private void chooseLeaders(Message message) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {

            if (model.getPhase() == GamePhase.LEADER) {

                if (message.getLeader(1) >= 1 && message.getLeader(1) <= 4 &&
                        message.getLeader(2) >= 1 && message.getLeader(2) <= 4) {
                    try {
                        model.chooseLeaders(model.getCurrentPlayer().getName(), message);
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in chooseLeaders: " +*/ e.getMessage());
                        error.put("method", "chooseLeaders");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "chooseLeaders");
                    Message errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "chooseLeaders");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "chooseLeaders");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * chooseResources method: the controller checks the inputs and calls chooseResources on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    private void chooseResources(Message message) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {

            if (model.getPhase() == GamePhase.RESOURCE) {

                if (message.getResource(1) != null && message.getPosition(1) != null) {

                    try {
                        model.chooseInitialResource(model.getCurrentPlayer().getName(), message);
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in chooseResources: " +*/ e.getMessage());
                        error.put("method", "chooseResources");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "chooseResources");
                    Message errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "chooseResources");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "chooseResources");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }
    }

    /**
     * Buy method: the controller checks the inputs and calls buy on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    private void buy(Message message) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                // NOTA: CONTROLLO SOLO RES1
                if (message.getRow() >= 0 && message.getRow() <= 2 &&
                        message.getCol() >= 0 && message.getCol() <= 3 &&
                        message.getSlot() >= 0 && message.getSlot() <= 2) {
                    try {
                        model.buy(model.getCurrentPlayer().getName(), message);
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in Buy: " +*/ e.getMessage());
                        error.put("method", "buy");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "buy");
                    Message errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "buy");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "buy");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }
    }

    /**
     * Produce method: the controller checks the inputs and calls produce on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    private void produce(Message message) {

        if (model.getCurrentPlayer().getName().equalsIgnoreCase(message.getPlayer())) {

            if (model.getPhase() == GamePhase.FULLGAME) {


                if (message.isSelected(0) || message.isSelected(1) || message.isSelected(2) ||
                        message.isSelected(3) || message.isSelected(4) || message.isSelected(5)) {
                    try {
                        model.produce(model.getCurrentPlayer().getName(), message);
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in Produce: " +*/ e.getMessage());
                        error.put("method", "produce");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "produce");
                    Message errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "produce");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "produce");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * fromMarket method: the controller checks the inputs and calls fromMarket on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    private void fromMarket(Message message) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                if (message.isRow() && message.size() >= 5) {
                    try {
                        model.fromMarket(model.getCurrentPlayer().getName(), message);
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in fromMarket: " +*/ e.getMessage());
                        error.put("method", "fromMarket");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else if (message.isCol() && message.size() >= 4) {
                    try {
                        model.fromMarket(model.getCurrentPlayer().getName(), message);
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in fromMarket: " +*/ e.getMessage());
                        error.put("method", "fromMarket");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "fromMarket");
                    Message errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "fromMarket");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "fromMarket");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * swapDeposits method: the controller checks the inputs and calls swapDeposits on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    private void swapDeposits(Message message) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {

            if (model.getPhase() == GamePhase.FULLGAME) {
                if (message.size() == 2 &&
                        message.getSource() != null && message.getDest() != null) {
                    try {
                        model.swapDeposit(model.getCurrentPlayer().getName(), message);
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in swapDeposits: " +*/ e.getMessage());
                        error.put("method", "swapDeposits");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    error.put("content", "Attention! You did not type correctly! Try again!");
                    error.put("method", "swapDeposits");
                    Message errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "swapDeposits");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "swapDeposits");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * ActivateLeader method: the controller checks the inputs and calls activateLeader on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    private void activateLeader(Message message) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                if (message.size() >= 1 && message.getIndex() >= 0 && message.getIndex() <= 1) {
                    try {
                        model.activateLeader(model.getCurrentPlayer().getName(), message.getIndex());
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in activateLeader: " +*/ e.getMessage());
                        error.put("method", "activateLeader");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    error.put("content", "Attention! You did not type the index correctly! Try again!");
                    error.put("method", "activateLeader");
                    Message errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "activateLeader");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "activateLeader");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }
    }

    /**
     * ActivateLeader method: the controller checks the inputs and calls activateLeader on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    private void discardLeader(Message message) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {

            if (model.getPhase() == GamePhase.FULLGAME) {

                if (message.size() >= 1 && message.getIndex() >= 0 && message.getIndex() <= 1) {
                    try {
                        model.discardLeader(model.getCurrentPlayer().getName(), message.getIndex());
                    } catch (InvalidActionException e) {
                        Map<String, String> error = new HashMap<>();
                        error.put("action", "error");
                        error.put("player", message.getPlayer());
                        error.put("content", /*"There is an error in discardLeader: " +*/ e.getMessage());
                        error.put("method", "discardLeader");
                        Message errorAnswer = new ErrorAnswer(error);
                        gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                    }
                } else {
                    Map<String, String> error = new HashMap<>();
                    error.put("action", "error");
                    error.put("player", message.getPlayer());
                    error.put("content", "Attention! You did not type the index correctly! Try again!");
                    error.put("method", "discardLeader");
                    Message errorAnswer = new ErrorAnswer(error);
                    gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
                }
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "discardLeader");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "discardLeader");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }

    /**
     * EndTurn method: the controller checks the inputs and calls endTurn on the Game.
     *
     * @param message is the message that contains all the info about the action.
     */
    public void endTurn(Message message) {

        if (model.getCurrentPlayer().getName().equals(message.getPlayer())) {

            if (model.getPhase() == GamePhase.FULLGAME) {
                try {
                    model.endTurn(message.getPlayer());
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
            } else {
                //System.out.println("Wrong phase!");
                Map<String, String> error = new HashMap<>();
                error.put("action", "error");
                error.put("player", message.getPlayer());
                error.put("content", "Attention! You can not do this action in this phase!");
                error.put("method", "endturn");
                Message errorAnswer = new ErrorAnswer(error);
                gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
            }
        } else {
            //System.out.println("Not your turn!");
            Map<String, String> error = new HashMap<>();
            error.put("action", "error");
            error.put("player", message.getPlayer());
            error.put("content", "Attention! It is not your turn!");
            error.put("method", "endturn");
            Message errorAnswer = new ErrorAnswer(error);
            gameHandlerListener.fireUpdates(errorAnswer.getAction(), errorAnswer);
        }

    }


    @Override
    public void update(String propertyName, Message message) {
        switch (propertyName.toUpperCase()) {
            case "START":
                start();
                break;
            case "BUY":
                buy(message);
                break;
            case "PRODUCE":
                produce(message);
                break;
            case "MARKET":
                fromMarket(message);
                break;
            case "SWAP":
                swapDeposits(message);
                break;
            case "CHOOSELEADERS":
                chooseLeaders(message);
                break;
            case "CHOOSERESOURCES":
                chooseResources(message);
                break;
            case "ACTIVATE":
                activateLeader(message);
                break;
            case "DISCARD":
                discardLeader(message);
                break;
            case "ENDTURN":
                endTurn(message);
                break;
            case "DISCONNECT":
                model.setPhase(GamePhase.ENDED);
                break;
            default:
                Map<String, String> map = new HashMap<>();
                map.put("action", "error");
                map.put("player", message.getPlayer());
                map.put("content", "Illegal action! Try typing again!");
                Message error = new ErrorAnswer(map);
                gameHandlerListener.fireUpdates(map.get("action"), error);
                break;
        }
    }
}