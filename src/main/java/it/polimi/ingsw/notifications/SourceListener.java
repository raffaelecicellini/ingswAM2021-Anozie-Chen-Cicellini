package it.polimi.ingsw.notifications;

import it.polimi.ingsw.messages.Message;

import java.util.Map;

public interface SourceListener {
    void update (String propertyName, Message value);
}
