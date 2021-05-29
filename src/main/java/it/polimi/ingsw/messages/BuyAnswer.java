package it.polimi.ingsw.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * @see Message
 */
public class BuyAnswer extends Message{

    public BuyAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public int getCol() {
        return info.containsKey("col") ? Integer.parseInt(info.get("col")) : -1;
    }

    @Override
    public int getRow() {
        return info.containsKey("row") ? Integer.parseInt(info.get("row")) : -1;

    }

    @Override
    public int getIdNew() {
        return info.get("idNew").equalsIgnoreCase("empty") ? 0 : Integer.parseInt(info.get("idNew"));
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
    public Map<String,String> getStrongbox() {
        Map<String,String> strongbox = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            strongbox.put("strres" + i, info.get("strres" + i));
            strongbox.put("strqty" + i, info.get("strqty" + i));
        }
        return strongbox;
    }

    @Override
    public int getSlot() {
        return info.containsKey("slot") ?
                Integer.parseInt(info.get("slot")) : -1;
    }

    @Override
    public int getIdBought() {
        return info.containsKey("idBought") ?
                Integer.parseInt(info.get("idBought")) : -1;
    }
}
