package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.Cards;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.messages.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class MarketController extends GUIController{
    private GUI gui;
    private final Map<String, String> info=new HashMap<>();
    private Stage stage;
    @FXML private Button row_1, row_2, row_3, col_1, col_2, col_3, col_4;
    @FXML private GridPane market_board;
    @FXML private ImageView out;

    /**
     * This method is called when a user presses one of the button row/col. It retrieves the information on the row/col
     * chosen, then for each resource in the row/col shows a ChoiceDialog asking the user the position where he wants to put
     * the resource.
     * @param event the event produced by the user when he pressed a button
     */
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
            System.out.println(selection.get(i-1));
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
        action.append("\nDo you confirm your action?");
        Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Market confirmation");
        confirm.setContentText(action.toString());
        Optional<ButtonType> ok=confirm.showAndWait();
        if (ok.isPresent() && ok.get()==ButtonType.OK){
            gui.getModelView().setActiveTurn(false);
            Message message= new MarketMessage(info);
            gui.getListeners().fireUpdates(message.getAction(), message);
            //gui.getListeners().fireUpdates(info.get("action"), message);
        }
        info.clear();
        stage.close();
    }

    /**
     * Method used to get the correct information from the ModelView based on the button clicked by the user
     * @param source the Button that the user pressed
     * @return the list of resources (as String) in the position specified by the user
     */
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

    /**
     * This method prepares a new stage for market.fxml, setting the modality to prevent the user to change his action
     * before closing this new stage
     */
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

    /**
     * This method is called when a player takes resources from the market. It updates the GridPane with the new images
     */
    public void updateMarket(){
        String[][] market=gui.getModelView().getMarket();
        String outMarble=gui.getModelView().getOutMarble();
        ObservableList<Node> list=market_board.getChildren();

        out.setImage(new Image(getPath(outMarble)));
        ImageView img;
        for (Node node:list){
            img= (ImageView) node;
            img.setImage(new Image(getPath(market[GridPane.getColumnIndex(node)][GridPane.getRowIndex(node)])));
        }
    }

    /**
     * @see GUIController
     * @param gui the gui to be set
     */
    @Override
    public void setGui(GUI gui) {
        this.gui=gui;
    }
}
