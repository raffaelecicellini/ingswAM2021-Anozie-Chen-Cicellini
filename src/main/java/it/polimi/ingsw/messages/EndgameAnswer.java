package it.polimi.ingsw.messages;

import java.util.Map;

public class EndgameAnswer extends Message{

    public EndgameAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getWinner() {
        return  info.get("winner");
    }

    @Override
    public int getWinnerPoints() {
        return info.containsKey("winnerpoints") ?
                Integer.parseInt(info.get("winnerpoints")) : -1;
    }

    @Override
    public int getPoints() {
        return info.containsKey("points") ?
                Integer.parseInt(info.get("points")) : -1;
    }
}
