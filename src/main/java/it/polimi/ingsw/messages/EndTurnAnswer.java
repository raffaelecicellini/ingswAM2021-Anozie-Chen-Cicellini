package it.polimi.ingsw.messages;

import java.util.Map;

public class EndTurnAnswer extends Message{

    public EndTurnAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public String getTileState(int player, int index) {
        return info.get("tile"+player+index);
    }

    @Override
    public String getCurrentPlayer() {
        return info.get("currentPlayer");
    }

    @Override
    public String getEndedPlayer() {
        return info.get("endedTurnPlayer");
    }

    @Override
    public int getToken() {
        return info.containsKey("tokenActivated") ?
                Integer.parseInt(info.get("tokenActivated")) : -1;
    }

    @Override
    public int getBlackPos() {
        return info.containsKey("blackPos") ?
                Integer.parseInt(info.get("blackPos")) : -1;
    }

    @Override
    public int getCard(int col, int row) {
        return info.containsKey("card" + col + row) ?
                Integer.parseInt(info.get("card" + col + row)) : -1;
    }
}
