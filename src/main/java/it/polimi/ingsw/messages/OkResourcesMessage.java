package it.polimi.ingsw.messages;

import java.util.HashMap;
import java.util.Map;

public class OkResourcesMessage extends Message{

    public OkResourcesMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public Map<String,String> getDeposits() {
        Map<String,String> deposits = new HashMap<>(info);
        return deposits;
    }
}
