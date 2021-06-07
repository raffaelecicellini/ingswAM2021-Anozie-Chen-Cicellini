package it.polimi.ingsw.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * @see Message
 */
public class MarketAnswer extends Message {

    public MarketAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getRes(int ind) {
        return info.get("res" + ind);
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

    @Override
    public String getOutMarble() {
        return info.get("out");
    }

    @Override
    public int getBlackPos() {
        return info.containsKey("blackPos") ? Integer.parseInt(info.get("blackPos")) : -1;
    }

    @Override
    public Map<String, String> getDeposits() {
        Map<String, String> deposits = new HashMap<>();
        deposits.put("smallres", info.get("smallres"));
        deposits.put("smallqty", info.get("smallqty"));
        deposits.put("midres", info.get("midres"));
        deposits.put("midqty", info.get("midqty"));
        deposits.put("bigres", info.get("bigres"));
        deposits.put("bigqty", info.get("bigqty"));
        if (info.containsKey("sp1res"))
            deposits.put("sp1res", info.get("sp1res"));
        if (info.containsKey("sp1qty"))
            deposits.put("sp1qty", info.get("sp1qty"));
        if (info.containsKey("sp2res"))
            deposits.put("sp2res", info.get("sp2res"));
        if (info.containsKey("sp2qty"))
            deposits.put("sp2qty", info.get("sp2qty"));
        return deposits;
    }

    @Override
    public int getNewPos() {
        return info.containsKey("newPos") ? Integer.parseInt(info.get("newPos")) : -1;
    }

    @Override
    public int getDiscarded() {
        return info.containsKey("discarded") ? Integer.parseInt(info.get("discarded")) : -1;
    }
}
