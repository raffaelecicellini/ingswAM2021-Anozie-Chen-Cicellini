package it.polimi.ingsw.messages;

import java.util.Map;

/**
 * @see Message
 */
public class LeaderActionMessage extends Message{

    public LeaderActionMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public int getIndex() {
        return Integer.parseInt(info.get("pos"));
    }
}
