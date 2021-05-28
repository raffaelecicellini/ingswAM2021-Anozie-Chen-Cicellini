package it.polimi.ingsw.messages;

import java.util.Map;

public class EndAnswer extends Message{
    public EndAnswer(Map<String, String> info) {
        super(info);
    }

    public String getContent(){
        return info.get("content");
    }
}
