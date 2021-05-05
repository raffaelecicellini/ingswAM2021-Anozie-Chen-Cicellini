package it.polimi.ingsw.notifications;

import java.util.Map;

public interface SourceListener {
    void update (String propertyName, Map<String, String> value);
}
