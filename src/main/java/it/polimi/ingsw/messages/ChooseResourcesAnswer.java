package it.polimi.ingsw.messages;

import java.util.Map;

public class ChooseResourcesAnswer extends Message{

    public ChooseResourcesAnswer(Map<String, String> info) {
        super(info);
    }

    @Override
    public int getAddPos() {
        return info.containsKey("addpos") ? Integer.parseInt(info.get("addpos")) : -1;
    }

    @Override
    public int getResQty() {
        return info.containsKey("qty") ? Integer.parseInt(info.get("qty")) : -1;
    }
}
