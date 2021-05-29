package it.polimi.ingsw.messages;

import java.util.Map;

/**
 * @see Message
 */
public class MarketMessage extends Message{

    public MarketMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getPos(int ind) {
        return info.get("pos"+ind);
    }

    @Override
    public String getRes(int ind) {
        return info.get("res"+ind);
    }

    @Override
    public boolean isRow() {
        return info.containsKey("row");
    }

    @Override
    public boolean isCol() {
        return info.containsKey("col");
    }

    @Override
    public int getMarblesIndex() {
        if (info.containsKey("row"))
            return Integer.parseInt(info.get("row"));
        else if (info.containsKey("col"))
            return (Integer.parseInt(info.get("col")));
        return -1;
    }
}
