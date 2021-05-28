package it.polimi.ingsw.messages;

import java.util.Map;

public class LeaderMessage extends Message{

    public LeaderMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public int getLeader(int ind) {
        return info.containsKey("ind"+ind) ? Integer.parseInt(info.get("ind"+ind)) : 0;
    }
}
