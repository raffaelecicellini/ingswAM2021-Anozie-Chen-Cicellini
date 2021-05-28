package it.polimi.ingsw.messages;

import java.util.Map;

public class DisconnectionMessage extends Message{
    public DisconnectionMessage(Map<String, String> info) {
        super(info);
    }
}
