package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.ModelView;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

public class ChooseLeadersController {

    ModelView modelView = new ModelView();

    private Stage stage;
    private Scene scene;
    private Parent root;

    private int leaderCount = 0;
    private Map<String, String> leaders;

    public void chosenLeader(MouseEvent event) {

        // IDCARTA = event.getSource();

        if (leaders.containsValue("IDCARTA")) {
            // deselezionala
            if (leaders.get("leader0").equalsIgnoreCase("IDCARTA")) {
                if (leaders.containsKey("leader1")) {
                    leaders.put("leader0", leaders.get("leader1"));
                    leaders.put("state0", leaders.get("state1"));
                } else {
                    leaders.remove("leader0");
                    leaders.remove("state0");
                }
                leaderCount--;
            } else
                if (leaders.containsKey("leader1") &&
                    leaders.get("leader1").equalsIgnoreCase("IDCARTA")) {
                    leaders.remove("leader1");
                    leaders.remove("state1");
                    leaderCount--;
            }
        } else {
            if (leaderCount < 2) {
                leaders.put("leader" + leaderCount, "IDCARTA");
                leaders.put("state" + leaderCount, "AVAILABLE");
                leaderCount++;
            }
            else {
                // avverti
            }
        }
    }

    public void confirm(ActionEvent event) throws IOException {
        modelView.setLeaders(leaders);

        root = FXMLLoader.load(getClass().getResource("/board.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
