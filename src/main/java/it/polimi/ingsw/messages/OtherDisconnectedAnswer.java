package it.polimi.ingsw.messages;

import java.util.Map;

/**
 * @see Message
 */
public class OtherDisconnectedAnswer extends Message{
    public OtherDisconnectedAnswer(Map<String, String> info) {
        super(info);
    }

    public String getContent(){
        return info.get("content");
    }
}
