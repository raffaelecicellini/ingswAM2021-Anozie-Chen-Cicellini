package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitController implements GUIController{
    private GUI gui;
    @FXML
    private Label status;

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }

    public void setText(String s) {
        status.setText(s);
    }
}
