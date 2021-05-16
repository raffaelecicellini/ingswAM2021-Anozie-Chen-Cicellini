package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;

public class ShowController implements GUIController{
    private GUI gui;

    public void prepareShow(String name){
        //Metodo chiamato quando utente vuole vedere situazione del player name. Prepara il file show.fxml con le info di
        //tale player, poi lo mostra in un nuovo stage (da cui non si esce se non chiudendolo)
    }

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
