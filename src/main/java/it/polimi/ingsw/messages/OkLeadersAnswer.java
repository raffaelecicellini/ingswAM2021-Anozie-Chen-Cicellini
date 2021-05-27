package it.polimi.ingsw.messages;

import java.util.Map;

public class OkLeadersAnswer extends Message{

    public OkLeadersAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getLeaders(int ind) {
        return info.get("leader"+ind);
    }
}
