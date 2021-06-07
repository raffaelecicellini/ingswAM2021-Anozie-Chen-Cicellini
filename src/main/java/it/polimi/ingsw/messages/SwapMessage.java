package it.polimi.ingsw.messages;

import java.util.Map;

/**
 * @see Message
 */
public class SwapMessage extends Message {

    public SwapMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getSource() {
        return info.get("source");
    }

    @Override
    public String getDest() {
        return info.get("dest");
    }

}
