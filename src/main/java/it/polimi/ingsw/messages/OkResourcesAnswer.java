package it.polimi.ingsw.messages;

import java.util.HashMap;
import java.util.Map;

/**
 * @see Message
 */
public class OkResourcesAnswer extends Message{

    public OkResourcesAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public Map<String,String> getDeposits() {
        Map<String,String> deposits = new HashMap<>(info);
        return deposits;
    }
}
