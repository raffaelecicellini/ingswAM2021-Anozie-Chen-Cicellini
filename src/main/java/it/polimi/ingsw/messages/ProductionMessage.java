package it.polimi.ingsw.messages;

import java.util.Locale;
import java.util.Map;

/**
 * @see Message
 */
public class ProductionMessage extends Message{

    public ProductionMessage(Map<String, String> info) {
        super(info);
    }

    @Override
    public boolean isSelected(int ind) {
        if (!info.containsKey("prod"+ind))
            return false;
        String selected = info.get("prod"+ind);
        return selected.equalsIgnoreCase("yes");
    }

    @Override
    public String getRes(int prod, int res) {
        return info.get("in"+ prod + res);
    }

    @Override
    public String getPos(int prod, int res) {
        return info.get("pos"+ prod + res);
    }

    @Override
    public String getOut(int prod) {
        return info.get("out"+prod);
    }
}
