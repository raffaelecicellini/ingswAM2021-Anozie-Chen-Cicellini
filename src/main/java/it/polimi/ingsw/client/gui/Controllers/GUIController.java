package it.polimi.ingsw.client.gui.Controllers;

import it.polimi.ingsw.client.gui.GUI;

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
        switch (marble){
            case "BLUE": return "/PNG/marbles_bg/blue_ball.png";
            case "YELLOW": return "/PNG/marbles_bg/yellow_ball.png";
            case "PURPLE": return "/PNG/marbles_bg/purple_ball.png";
            case "GREY": return "/PNG/marbles_bg/grey_ball.png";
            case "RED": return "/PNG/marbles_bg/red_ball.png";
            case "WHITE": return "/PNG/marbles_bg/white_ball.png";
            default: return "";
        }
    };
}
