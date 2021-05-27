package it.polimi.ingsw.messages;

import java.util.Map;

public class ChooseLeadersAnswer extends Message{

    public ChooseLeadersAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getLeaders(int ind) {
        return info.get("leader"+ind);
    }
}
