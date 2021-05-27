package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.notifications.SourceListener;
import java.util.Map;

/**
 * This class represents the Action Parser. It takes the input from the View receiving the PropertyChangeEvent,
 * it serializes it and it sends it with the Connection Socket to the Server.
 */
public class ActionParser implements SourceListener {

    /**
     * This attribute represents the Connection Socket.
     */
    ConnectionSocket connection;

    /**
     * This attribute represents the ModelView.
     */
    ModelView modelView;

    /**
     * Action Parser's constructor.
     * @param connection is the the Connection Socket.
     */
    public ActionParser(ConnectionSocket connection) {
        this.connection = connection;
    }

    @Override
    public void update(String propertyName, Message action) {
        Map<String, String> value= action.getAll();
        Gson gson = new Gson();
        String message = gson.toJson(value);
        // sending serialized message
        connection.send(message);
    }
}
