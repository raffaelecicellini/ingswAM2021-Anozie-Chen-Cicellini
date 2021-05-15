package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.ModelView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ChooseNameController {

    @FXML
    private Button confirmButton;
    @FXML
    private TextField textField;

    String name;

    private ModelView modelView = new ModelView();

    public void toNextStep(ActionEvent actionEvent) throws IOException {

        name = textField.getText();
        //send confimation
        modelView.setName(name);

        Parent root;
        if (modelView.isSoloGame()) {
            root = FXMLLoader.load(getClass().getResource("/fxml/soloBoard.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("/fxml/board.fxml"));
        }
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
