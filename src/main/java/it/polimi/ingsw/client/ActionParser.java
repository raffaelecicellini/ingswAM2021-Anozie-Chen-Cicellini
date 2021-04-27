package it.polimi.ingsw.client;

import com.google.gson.Gson;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * This class represents the Action Parser. It takes the input from the View receiving the PropertyChangeEvent,
 * it serializes it and it sends it with the Connection Socket to the Server.
 */
public class ActionParser implements PropertyChangeListener {

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
     * @param modelView is the ModelView.
     */
    public ActionParser(ConnectionSocket connection, ModelView modelView) {
        this.connection = connection;
        this.modelView = modelView;
    }

    /**
     * @see PropertyChangeListener#propertyChange(PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        Gson gson = new Gson();
        String message = gson.toJson(evt.getNewValue());
        // sending serialized message
        connection.send(message);

    }
}
