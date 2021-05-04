package it.polimi.ingsw.notifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Source {
    private List<SourceListener> listeners;

    public Source(){
        this.listeners= new ArrayList<>();
    }

    public void addListener(SourceListener x){
        listeners.add(x);
    }

    public void removeListener(SourceListener x){
        listeners.remove(x);
    }

    public void fireUpdates(String propertyName, Map<String, String> value){
        for (SourceListener x: listeners) {
            x.update(propertyName, value);
        }
    }
}
