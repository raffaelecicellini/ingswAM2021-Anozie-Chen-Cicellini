package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.scene.image.ImageView;

public abstract class GUIController {
    /**
     * Method used to set the gui for each type of Controller
     * @param gui the gui to be set
     */
    public abstract void setGui(GUI gui);

    /**
     * Utility method used to get the path to the corresponding image of a certain marble
     * @param marble a String representing the marble
     * @return the path to the image of the marble
     */
    public String getPath(String marble){
        switch (marble.toUpperCase()){
            case "BLUE": return "/PNG/marbles_bg/blue_ball.png";
            case "YELLOW": return "/PNG/marbles_bg/yellow_ball.png";
            case "PURPLE": return "/PNG/marbles_bg/purple_ball.png";
            case "GREY": return "/PNG/marbles_bg/grey_ball.png";
            case "RED": return "/PNG/marbles_bg/red_ball.png";
            case "WHITE": return "/PNG/marbles_bg/white_ball.png";
            default: return "";
        }
    }

    /**
     * This method sets the player's coordinates on the board.
     * @param player is the player
     * @param pos is the player's position.
     */
    public void setPosition(ImageView player, int pos) {
        switch (pos) {
            case 0:
                player.setLayoutX(200.0);
                player.setLayoutY(98.0);
                break;
            case 1:
                player.setLayoutX(230.0);
                player.setLayoutY(98.0);
                break;
            case 2:
                player.setLayoutX(261.0);
                player.setLayoutY(98.0);
                break;
            case 3:
                player.setLayoutX(261.0);
                player.setLayoutY(65.0);
                break;
            case 4:
                player.setLayoutX(261.0);
                player.setLayoutY(29.0);
                break;
            case 5:
                player.setLayoutX(296.0);
                player.setLayoutY(29.0);
                break;
            case 6:
                player.setLayoutX(328.0);
                player.setLayoutY(29.0);
                break;
            case 7:
                player.setLayoutX(362.0);
                player.setLayoutY(29.0);
                break;
            case 8:
                player.setLayoutX(392.0);
                player.setLayoutY(29.0);
                break;
            case 9:
                player.setLayoutX(427.0);
                player.setLayoutY(29.0);
                break;
            case 10:
                player.setLayoutX(427.0);
                player.setLayoutY(61.0);
                break;
            case 11:
                player.setLayoutX(427.0);
                player.setLayoutY(98.0);
                break;
            case 12:
                player.setLayoutX(457.0);
                player.setLayoutY(98.0);
                break;
            case 13:
                player.setLayoutX(492.0);
                player.setLayoutY(98.0);
                break;
            case 14:
                player.setLayoutX(524.0);
                player.setLayoutY(98.0);
                break;
            case 15:
                player.setLayoutX(556.0);
                player.setLayoutY(98.0);
                break;
            case 16:
                player.setLayoutX(589.0);
                player.setLayoutY(98.0);
                break;
            case 17:
                player.setLayoutX(589.0);
                player.setLayoutY(63.0);
                break;
            case 18:
                player.setLayoutX(589.0);
                player.setLayoutY(31.0);
                break;
            case 19:
                player.setLayoutX(623.0);
                player.setLayoutY(31.0);
                break;
            case 20:
                player.setLayoutX(654.0);
                player.setLayoutY(31.0);
                break;
            case 21:
                player.setLayoutX(687.0);
                player.setLayoutY(31.0);
                break;
            case 22:
                player.setLayoutX(720.0);
                player.setLayoutY(31.0);
                break;
            case 23:
                player.setLayoutX(753.0);
                player.setLayoutY(31.0);
                break;
            case 24:
                player.setLayoutX(785.0);
                player.setLayoutY(31.0);
                break;
            default:
                player.setLayoutX(0);
                player.setLayoutY(0);
                break;
        }
    }
}
