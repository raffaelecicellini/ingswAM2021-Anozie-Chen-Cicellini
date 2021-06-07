package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WaitController extends GUIController {
    private GUI gui;
    @FXML
    private Label status;

    /**
     * @param gui the gui to be set
     * @see GUIController
     */
    @Override
    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * This method updates the label.
     *
     * @param s is the string that will replace the current label.
     */
    public void setText(String s) {
        status.setText(s);
    }
}