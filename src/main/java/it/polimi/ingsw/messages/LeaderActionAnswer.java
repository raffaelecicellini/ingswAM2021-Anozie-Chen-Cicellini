package it.polimi.ingsw.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * @see Message
 */
public class LeaderActionAnswer extends Message{

    public LeaderActionAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public boolean isDep() {
        return info.containsKey("isDep");
    }

    @Override
    public int getIndex() {
        return info.containsKey("index") ?
                Integer.parseInt(info.get("index")) : -1;
    }

    @Override
    public Map<String,String> getDeposits() {
        Map<String,String> deposits = new HashMap<>();
        deposits.put("smallres", info.get("smallres"));
        deposits.put("smallqty", info.get("smallqty"));
        deposits.put("midres", info.get("midres"));
        deposits.put("midqty", info.get("midqty"));
        deposits.put("bigres", info.get("bigres"));
        deposits.put("bigqty", info.get("bigqty"));
        if (info.containsKey("sp1res"))
            deposits.put("sp1res",info.get("sp1res"));
        if (info.containsKey("sp1qty"))
            deposits.put("sp1qty",info.get("sp1qty"));
        if (info.containsKey("sp2res"))
            deposits.put("sp2res",info.get("sp2res"));
        if (info.containsKey("sp2qty"))
            deposits.put("sp2qty",info.get("sp2qty"));
        return deposits;
    }

    @Override
    public int getCountLeader() {
        return info.containsKey("countLeader") ?
                Integer.parseInt(info.get("countLeader")) : -1;
    }

    @Override
    public int getNewPos() {
        return info.containsKey("newPos") ? Integer.parseInt(info.get("newPos")) : -1;
    }
}
