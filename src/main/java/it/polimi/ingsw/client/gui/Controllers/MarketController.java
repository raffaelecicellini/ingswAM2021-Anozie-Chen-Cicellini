package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.Cards;
import it.polimi.ingsw.client.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class MarketController implements GUIController{
    private GUI gui;
    private Map<String, String> info=new HashMap<>();
    private Stage stage;
    @FXML private Button row_1, row_2, row_3, col_1, col_2, col_3, col_4;

    public void select(ActionEvent event){
        //Recupera info su bottone (target) che è stato schiacciato per capire row/col scelta. A partire da questo recupera
        //info da modelView. Ciclo per ogni risorsa che produce Alert.CONFIRMATION per chiedere dove salvare risorsa.
        //Una volta finite le risorse Alert.CONFIRMATION per chiedere conferma. Se ok manda pack, altrimenti svuota mappa,
        //chiude schermata e si ritorna a board.fxml

        info.put("action", "market");
        info.put("player", gui.getModelView().getName());
        List<String> selection=parseChoice((Button) event.getSource());
        List<String> deps= new ArrayList<>(Arrays.asList("small", "mid", "big"));
        if (gui.getModelView().getDeposits(gui.getModelView().getName()).containsKey("sp1res")) deps.add("sp1");
        if (gui.getModelView().getDeposits(gui.getModelView().getName()).containsKey("sp2res")) deps.add("sp2");
        deps.add("discard");

        Map<String, String> choices= new HashMap<>();
        int i=1;
        String curr;
        ChoiceDialog<String> market= new ChoiceDialog<>(deps.get(0), deps);
        Optional<String> result;
        market.initModality(Modality.APPLICATION_MODAL);
        market.setHeaderText("Make a choice");
        for (String x: selection) {
            curr="pos"+i;
            if (x.equalsIgnoreCase("red")) info.put(curr, "small");
            else if (x.equalsIgnoreCase("white")){
                //controlli white
                Map<String, String> leaders= gui.getModelView().getLeaders(gui.getModelView().getName());
                List<String> colors= new ArrayList<>();
                for (int j=0; j<2; j++){
                    String state= "state"+j;
                    String white= Cards.getWhiteById(Integer.parseInt(leaders.get("leader"+j)));

                    if (leaders.get(state).equalsIgnoreCase("active") && white!=null){
                        colors.add(white);
                    }
                }
                if (colors.isEmpty()) info.put(curr, "small");
                else if(colors.size()==1){
                    //Alert
                    market.setContentText("Current resource: "+colors.get(0)+". Where do you want to put it in?");
                    result=market.showAndWait();
                    if (result.isPresent()){
                        choices.put(curr, colors.get(0));
                        info.put(curr, result.get());
                    }
                    else {
                        info.clear();
                        stage.close();
                        return;
                    }
                }
                else if (colors.size()==2){
                    //Ask for color using Alert
                    ChoiceDialog<String> color=new ChoiceDialog<>(colors.get(0), colors);
                    color.setHeaderText("Pick a color!");
                    color.setContentText("You have two active leaders. Choose one color for the white marble!");
                    Optional<String> col=color.showAndWait();
                    if (col.isPresent()){
                        info.put("res"+i, col.get());
                    }
                    else {
                        info.clear();
                        stage.close();
                        return;
                    }

                    //Ask for position using Alert
                    market.setContentText("Current resource: "+col.get()+". Where do you want to put it in?");
                    result=market.showAndWait();

                    if (result.isPresent()){
                        choices.put(curr, col.get());
                        info.put(curr, result.get());
                    }
                    else {
                        info.clear();
                        stage.close();
                        return;
                    }
                }
            }
            else{
                //Ask for position using Alert
                market.setContentText("Current resource: "+x+". Where do you want to put it in?");
                result=market.showAndWait();

                if (result.isPresent()){
                    choices.put(curr, x);
                    info.put(curr, result.get());
                }
                else {
                    info.clear();
                    stage.close();
                    return;
                }
            }
            i++;
        }

        //Ask for confirmation using alert
        StringBuilder action= new StringBuilder();
        action.append("There are your selections:");
        i=1;
        curr="pos"+i;
        while (info.containsKey(curr)){
            if (choices.containsKey(curr)){
                action.append("\nResource: ").append(choices.get(curr)).append("; Position: ").append(info.get(curr));
            }
            i++;
            curr="pos"+i;
        }
        action.append("Do you confirm your action?");
        Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Market confirmation");
        confirm.setContentText(action.toString());
        Optional<ButtonType> ok=confirm.showAndWait();
        if (ok.isPresent() && ok.get()==ButtonType.OK){
            gui.getModelView().setActiveTurn(false);
            gui.getListeners().fireUpdates(info.get("action"), info);
            info.clear();
            stage.close();
        }
        else {
            info.clear();
            stage.close();
        }
    }

    private ArrayList<String> parseChoice(Button source){
        if (source==row_1){
            info.put("row", "1");
            return gui.getModelView().getResMarket("row", 0);
        }
        if(source==row_2){
            info.put("row", "2");
            return gui.getModelView().getResMarket("row", 1);
        }
        if(source==row_3){
            info.put("row", "3");
            return gui.getModelView().getResMarket("row", 2);
        }
        if (source==col_1){
            info.put("col", "1");
            return gui.getModelView().getResMarket("col", 0);
        }
        if (source==col_2){
            info.put("col", "2");
            return gui.getModelView().getResMarket("col", 1);
        }
        if(source==col_3){
            info.put("col", "3");
            return gui.getModelView().getResMarket("col", 2);
        }
        if(source==col_4){
            info.put("col", "4");
            return gui.getModelView().getResMarket("col", 3);
        }
        else return new ArrayList<>();
    }

    //metodo per creare un nuovo stage?
    public void market(){
        //Chiamato quando utente da board schiaccia su bottone corrispondente. Viene creato nuovo stage con scena market.fxml
        //da cui non si può uscire senza averlo chiuso o senza aver finito la mossa.
        Scene market= gui.getSceneFromName("market.fxml");
        stage= new Stage();
        stage.setScene(market);
        stage.setTitle("Market");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest((e)->{
            info.clear();
        });
        stage.show();
    }

    //Metodi set per cambiare la disposizione delle biglie?

    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
