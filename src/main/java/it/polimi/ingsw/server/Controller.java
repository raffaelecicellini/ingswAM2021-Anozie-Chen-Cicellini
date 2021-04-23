package it.polimi.ingsw.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.InvalidActionException;
import it.polimi.ingsw.server.GameHandler;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Map;
import java.util.stream.Collectors;


public class Controller implements PropertyChangeListener {

    private Game model;
    private GameHandler handler;
    private PropertyChangeSupport listener = new PropertyChangeSupport(this);

    public Controller(Game model, PropertyChangeListener handler) {
        this.model = model;
        listener.addPropertyChangeListener(handler);

    }

    private void buy(Map<String, String> map) {

        // to lowercase the entire map
        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(),
                e1 -> e1.getValue().toLowerCase()));

        // NOTA: CONTROLLO SOLO RES1 (DEVO CONTROLLARE ANCHE RES2?)
        if (mapCopy.containsKey("row") && mapCopy.get("row") != null &&
                mapCopy.containsKey("column") && mapCopy.get("column") != null &&
                    mapCopy.containsKey("ind") && mapCopy.get("ind") != null &&
                        Integer.parseInt(mapCopy.get("ind")) >= 0 && Integer.parseInt(mapCopy.get("ind")) <= 2 &&
                            mapCopy.containsKey("res1") && mapCopy.get("res1") != null) {
            try {
                model.buy(model.getCurrentPlayer().getName(), map);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        }
    }

    private void produce(Map<String, String> map) {

        // to lowercase the entire map
        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(),
                e1 -> e1.getValue().toLowerCase()));

        if (mapCopy.containsKey("prod0") && mapCopy.containsKey("prod1") && mapCopy.containsKey("prod2") &&
                mapCopy.containsKey("prod3") && mapCopy.containsKey("prod4") && mapCopy.containsKey("prod5")) {
            try {
                model.produce(model.getCurrentPlayer().getName(), map);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        }

    }

    private void fromMarket(Map<String, String> map) {

        // to lowercase the entire map
        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(),
                e1 -> e1.getValue().toLowerCase()));

        if (mapCopy.containsKey("row")) {
            if (mapCopy.size() >= 5) {
                try {
                    model.fromMarket(model.getCurrentPlayer().getName(), map);
                } catch (InvalidActionException e) {
                    e.printStackTrace();
                }
            }
        } else
            if (mapCopy.containsKey("col")) {
                if (mapCopy.size() >= 4) {
                    try {
                        model.fromMarket(model.getCurrentPlayer().getName(), map);
                    } catch (InvalidActionException e) {
                        e.printStackTrace();
                    }
                }
            }

    }

    private void swapDeposits(Map<String, String> map) {

        // to lowercase the entire map
        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(),
                e1 -> e1.getValue().toLowerCase()));

        if (mapCopy.size() != 2 &&
                mapCopy.containsKey("source") && mapCopy.containsKey("dest") &&
                    mapCopy.get("source") != null && mapCopy.get("dest") != null) {
            try {
                model.swapDeposit(model.getCurrentPlayer().getName(), map);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        } 

    }

    private void chooseLeaders(Map<String, String> map) {

        // to lowercase the entire map
        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(),
                e1 -> e1.getValue().toLowerCase()));

        if (mapCopy.size() == 2 &&
                mapCopy.containsKey("ind1") && mapCopy.containsKey("ind2") &&
                Integer.parseInt(mapCopy.get("ind1")) >= 1 && Integer.parseInt(mapCopy.get("ind1")) <=2 &&
                Integer.parseInt(mapCopy.get("ind2")) >= 1 && Integer.parseInt(mapCopy.get("ind2")) <=2) {
            try {
                model.chooseLeaders(model.getCurrentPlayer().getName(), map);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        }

    }

    private void chooseResources(Map<String, String> map) {

        // to lowercase the entire map
        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(),
                e1 -> e1.getValue().toLowerCase()));

        // CONTROLLO SOLO SE HA RES1 E POS1 (NON CONTROLLO RES2 E POS2)
        if (mapCopy.containsKey("res1") && mapCopy.containsKey("pos1") &&
                mapCopy.get("res1") != null && mapCopy.get("res2") != null) {
            try {
                model.chooseInitialResource(model.getCurrentPlayer().getName(), map);
            } catch (InvalidActionException e) {
                e.printStackTrace();

            }
        }

    }

    private void activateLeader(int pos) {

        if (pos >= 0 && pos <=1) {
            try {
                model.activateLeader(model.getCurrentPlayer().getName(), pos);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        }

    }

    private void discardLeader(int pos) {

        if (pos >= 0 && pos <= 1) {
            try {
                model.discardLeader(model.getCurrentPlayer().getName(), pos);
            } catch (InvalidActionException e) {
                e.printStackTrace();
            }
        }

    }

    public void endTurn() {

        try {
            model.endTurn(model.getCurrentPlayer().getName());
        } catch (InvalidActionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        Object newValue = evt.getNewValue();
        String propertyName = evt.getPropertyName().toUpperCase();

        switch (propertyName){
            case "BUY":
                buy((Map<String, String>) newValue);
                break;
            case "PRODUCE":
                produce((Map<String, String>) newValue);
                break;
            case "MARKET":
                fromMarket((Map<String, String>) newValue);
                break;
            case "SWAP":
                swapDeposits((Map<String, String>) newValue);
                break;
            case "CHOOSELEADERS":
                chooseLeaders((Map<String, String>) newValue);
                break;
            case "CHOOSERESOURCES":
                chooseResources((Map<String, String>) newValue);
                break;
            case "ACTIVATE":
                activateLeader((Integer) newValue);
                break;
            case "DISCARD":
                discardLeader((Integer) newValue);
                break;
            case "ENDTURN":
                endTurn();
                break;

            default:
                throw new IllegalArgumentException("Invalid action!");
        }
    }

}
