package it.polimi.ingsw.messages;

import java.util.Map;

public class OtherConnectedAnswer extends Message{
    public OtherConnectedAnswer(Map<String, String> info) {
        super(info);
    }

    public String getContent(){
        return info.get("content");
    }
}
