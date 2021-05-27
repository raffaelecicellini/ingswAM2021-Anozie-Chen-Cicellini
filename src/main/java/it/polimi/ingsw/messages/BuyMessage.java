package it.polimi.ingsw.messages;

import java.util.HashMap;
import java.util.Map;

public class BuyMessage extends Message{

    public BuyMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public int getCol() {
        return info.containsKey("column") ? Integer.parseInt("column") : -1;
    }

    @Override
    public int getRow() {
        return info.containsKey("row") ? Integer.parseInt("row") : -1;
    }

    @Override
    public int getSlot() {
        return info.containsKey("ind") ? Integer.parseInt("ind") : -1;
    }

    @Override
    public Map<String,String> getCost() {
        Map<String,String> cost = new HashMap<>(info);
        cost.remove("ind");
        cost.remove("row");
        cost.remove("ind");
        return cost;
    }
}
