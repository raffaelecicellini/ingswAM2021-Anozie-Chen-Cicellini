package it.polimi.ingsw.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * @see Message
 */
public class BuyMessage extends Message{

    public BuyMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public int getCol() {
        return info.containsKey("column") ? Integer.parseInt(info.get("column")) : -1;
    }

    @Override
    public int getRow() {
        return info.containsKey("row") ? Integer.parseInt(info.get("row")) : -1;
    }

    @Override
    public int getSlot() {
        return (info.containsKey("ind")) ?
                Integer.parseInt(info.get("ind")) : -1;
    }

    @Override
    public Map<String,String> getCost() {
        Map<String,String> cost = new HashMap<>(info);
        System.out.println(cost);
        cost.remove("ind");
        cost.remove("row");
        cost.remove("column");
        return cost;
    }
}
