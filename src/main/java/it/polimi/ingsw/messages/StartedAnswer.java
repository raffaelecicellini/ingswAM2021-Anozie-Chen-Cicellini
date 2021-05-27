package it.polimi.ingsw.messages;

import java.util.Map;

public class StartedAnswer extends Message{

    public StartedAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public int getCard(int col, int row) {
        return info.containsKey("card"+col+row) ? Integer.parseInt(info.get("card"+col+row)) : -1;
    }

    @Override
    public String getMarble(int col, int row) {
        return info.get("marble"+col+row);
    }

    @Override
    public String getPlayer(int ind) {
        return info.get("player"+ind);
    }

    @Override
    public String getOutMarble() {
        return info.get("outMarble");
    }
}
