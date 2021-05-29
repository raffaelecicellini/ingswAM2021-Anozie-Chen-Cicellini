package it.polimi.ingsw.messages;

import java.util.Map;

/**
 * @see Message
 */
public class EndTurnMessage extends Message{

    public EndTurnMessage(Map<String, String> info) {
        super(info);
    }
}
