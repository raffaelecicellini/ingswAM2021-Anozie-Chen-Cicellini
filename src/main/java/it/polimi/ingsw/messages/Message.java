package it.polimi.ingsw.messages;

import java.util.Map;

public abstract class Message {
    protected Map<String,String> map;
    private String player;
    private String action;

    public Message (Map<String, String> map){
        player=map.remove("player");
        action=map.remove("action");
        this.map=map;
    }
}
