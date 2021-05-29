package it.polimi.ingsw.messages;

import java.util.Map;

/**
 * @see Message
 */
public class ResourceMessage extends Message{

    public ResourceMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getResource(int ind) {
        return info.get("res"+ind);
    }

    @Override
    public String getPosition(int ind) {
        return info.get("pos"+ind);
    }
}
