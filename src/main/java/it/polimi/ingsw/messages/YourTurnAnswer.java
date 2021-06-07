package it.polimi.ingsw.messages;

import java.util.Map;

/**
 * @see Message
 */
public class YourTurnAnswer extends Message {

    public YourTurnAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getContent() {
        return info.get("content");
    }
}
