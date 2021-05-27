package it.polimi.ingsw.messages;

import java.util.Map;

public class ErrorAnswer extends Message{

    public ErrorAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getContent() {
        return info.get("content");
    }

    @Override
    public String getMethod() {
        return info.get("method");
    }
}
