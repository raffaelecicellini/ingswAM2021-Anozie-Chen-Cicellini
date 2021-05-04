package it.polimi.ingsw.client;

import java.util.ArrayList;


public class Cards {
    /**
     * Method used to retrieve a String representation of a LeaderCard based on its id
     * @param id the id of the card to get
     * @return the String representation of the card corresponding to the id
     */
    public static String getLeaderById(int id){
        switch (id){
            case 1:
                return "Requirements: One DevelopCard of each color; "+"Points: 8; "+"Effect: No special effect;";
            case 2:
                return "Requirements: Position>=14 on the Faith Track; "+"Points: 7; "+"Effect: No special effect;";
            case 3:
                return "Requirements: One Yellow card and One Green card; "+"Points: 2; "+"Effect: Discount (-1 PURPLE);";
            case 4:
                return "Requirements: One Blue card and One Purple card; "+"Points: 2; "+"Effect: Discount (-1 BLUE);";
            case 5:
                return "Requirements: One Green card and One Blue card; "+"Points: 2; "+"Effect: Discount (-1 GREY);";
            case 6:
                return "Requirements: One Yellow card and One Purple card; "+"Points: 2; "+"Effect: Discount (-1 YELLOW);";
            case 7:
                return "Requirements: 5 Yellow resources; "+"Points: 3; "+"Effect: Special Deposit (2 slots for GREY);";
            case 8:
                return "Requirements: 5 Purple resources; "+"Points: 3; "+"Effect: Special Deposit (2 slots for BLUE);";
            case 9:
                return "Requirements: 5 Blue resources; "+"Points: 3; "+"Effect: Special Deposit (2 slots for YELLOW);";
            case 10:
                return "Requirements: 5 Grey resources; "+"Points: 3; "+"Effect: Special Deposit (2 slots for PURPLE);";
            case 11:
                return "Requirements: Two Yellow cards and One Blue card; "+"Points: 5; "+"Effect: WHITE=PURPLE (in Market);";
            case 12:
                return "Requirements: Two Green cards and One Purple card; "+"Points: 5; "+"Effect: WHITE=BLUE (in Market);";
            case 13:
                return "Requirements: Two Blue cards and One Yellow card; "+"Points: 5; "+"Effect: WHITE=GREY (in Market);";
            case 14:
                return "Requirements: Two Purple cards and One Green card; "+"Points: 5; "+"Effect: WHITE=YELLOW (in Market);";
            case 15:
                return "Requirements: One Yellow card (at least Level 2); "+"Points: 4; "+"Effect: Production: input=1 BLUE, output=your choice +1 faith;";
            case 16:
                return "Requirements: One Blue card (at least Level 2); "+"Points: 4; "+"Effect: Production: input=1 PURPLE, output=your choice +1 faith;";
            case 17:
                return "Requirements: One Purple card (at least Level 2); "+"Points: 4; "+"Effect: Production: input=1 GREY, output=your choice +1 faith;";
            case 18:
                return "Requirements: One Green card (at least Level 2); "+"Points: 4; "+"Effect: Production: input=1 YELLOW, output=your choice +1 faith;";
        }
        return null;
    }

    /**
     * Method used to get the discounted resource given by a LeaderCard based on its id
     * @param id the id of the Leadercard
     * @return a String representing the resource discounted by the LeaderCard
     */
    public static String getDiscountById(int id){
        switch (id){
            case 3: return "PURPLE";
            case 4: return "BLUE";
            case 5: return "GREY";
            case 6: return "YELLOW";
            default: return null;
        }
    }

    /**
     * Method used to get the resource in which a white marble is transformed by a LeaderCard based on its id
     * @param id the id of the Leadercard
     * @return a String representing the resource in which the white marble is transformed by the LeaderCard
     */
    public static String getWhiteById(int id){
        switch (id){
            case 11: return "PURPLE";
            case 12: return "BLUE";
            case 13: return "GREY";
            case 14: return "YELLOW";
            default: return null;
        }
    }

    /**
     * Method used to get the input of the production given by a LeaderCard based on its id
     * @param id the id of the Leadercard
     * @return a String representing the resource requested as input for the production of the LeaderCard
     */
    public static String getProductionById(int id){
        switch (id) {
            case 15: return "BLUE";
            case 16: return "PURPLE";
            case 17: return "GREY";
            case 18: return "YELLOW";
            default: return null;
        }
    }

    /**
     * Method used to retrieve a formatted representation of a DevelopCard based on its id
     * @param id the id of a DevelopCard
     * @return a String representation of a DevelopCard
     */
    public static String[] getDevelopById(int id){
        String[] card;
        card= new String[4];
        switch (id){
            case 1:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:1; "+"Cost:[2P];"+"           |";
                card[2]="|In:[1G]; "+"Out:[1F]; "+"VP:1;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 2:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:1; "+"Cost:[2P, 2G];"+"       |";
                card[2]="|In: [1Y, 1B]; "+"Out: [2G, 1F]; "+"VP4;"+"       |";
                card[3]="+----------------------------------------+";
                return card;
            case 3:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:1; "+"Cost:[3P];"+"           |";
                card[2]="|In:[2Y]; "+"Out:[1P, 1B, 1G]; "+"VP:3;"+"        |";
                card[3]="+----------------------------------------+";
                return card;
            case 4:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:1; "+"Cost:[1B, 1P, 1Y];"+"   |";
                card[2]="|In:[1Y]; "+"Out:[1B]; "+"VP:2;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 5:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:1; "+"Cost:[1Y, 1P, 1G];"+"     |";
                card[2]="|In:[1P]; "+"Out:[1G]; "+"VP:2;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 6:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:1; "+"Cost:[2Y, 2P];"+"         |";
                card[2]="|In:[1B, 1G]; "+"Out:[2P, 1F]; "+"VP:4;"+"        |";
                card[3]="+----------------------------------------+";
                return card;
            case 7:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:1; "+"Cost:[2Y];"+"             |";
                card[2]="|In:[1B]; "+"Out:[1F]; "+"VP:1;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 8:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:1; "+"Cost:[3Y];"+"             |";
                card[2]="|In:[2G]; "+"Out:[1Y, 1P, 1B]; "+"VP:3;"+"        |";
                card[3]="+----------------------------------------+";
                return card;
            case 9:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:1; "+"Cost:[1B, 1P, 1G];"+"    |";
                card[2]="|In:[1G]; "+"Out:[1P]; "+"VP:2;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 10:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:1; "+"Cost:[3B];"+"            |";
                card[2]="|In:[2P]; "+"Out:[1Y, 1B, 1G]; "+"VP:3;"+"        |";
                card[3]="+----------------------------------------+";
                return card;
            case 11:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:1; "+"Cost:[2B];"+"            |";
                card[2]="|In:[1Y]; "+"Out:[1F]; "+"VP:1;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 12:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:1; "+"Cost:[2B, 2Y];"+"        |";
                card[2]="|In:[1G, 1P]; "+"Out:[2Y, 1F]; "+"VP:4;"+"        |";
                card[3]="+----------------------------------------+";
                return card;
            case 13:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:1; "+"Cost:[3G];"+"           |";
                card[2]="|In:[2B]; "+"Out:[1Y, 1P, 1G]; "+"VP:3;"+"        |";
                card[3]="+----------------------------------------+";
                return card;
            case 14:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:1; "+"Cost:[2G, 2B];"+"       |";
                card[2]="|In:[1Y, 1P]; "+"Out:[2B, 1F]; "+"VP:4;"+"        |";
                card[3]="+----------------------------------------+";
                return card;
            case 15:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:1; "+"Cost:[2G];"+"           |";
                card[2]="|In:[1P]; "+"Out:[1F]; "+"VP:1;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 16:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:1; "+"Cost:[1B, 1G, 1Y];"+"   |";
                card[2]="|In:[1B]; "+"Out:[1Y]; "+"VP:2;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 17:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:2; "+"Cost:[4P];"+"           |";
                card[2]="|In:[1Y]; "+"Out:[2F]; "+"VP:5;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 18:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:2; "+"Cost:[3P, 3B];"+"       |";
                card[2]="|In:[2G]; "+"Out:[2P, 1F]; "+"VP:8;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 19:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:2; "+"Cost:[5P];"+"           |";
                card[2]="|In:[2G]; "+"Out:[2Y, 2F]; "+"VP:7;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 20:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:2; "+"Cost:[3P, 2Y];"+"       |";
                card[2]="|In:[1Y, 1P]; "+"Out:[3B]; "+"VP:6;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 21:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:2; "+"Cost:[3Y, 2G];"+"         |";
                card[2]="|In:[1Y, 1G]; "+"Out:[3P]; "+"VP:6;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 22:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:2; "+"Cost:[5Y];"+"             |";
                card[2]="|In:[2P]; "+"Out:[2B, 2F]; "+"VP:7;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 23:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:2; "+"Cost:[4Y];"+"             |";
                card[2]="|In:[1P]; "+"Out:[2F]; "+"VP:5;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 24:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:2; "+"Cost:[3Y, 3G];"+"         |";
                card[2]="|In:[1P]; "+"Out:[2G, 1F]; "+"VP:8;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 25:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:2; "+"Cost:[3B, 2P];"+"        |";
                card[2]="|In:[1B, 1P]; "+"Out:[3G]; "+"VP:6;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 26:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:2; "+"Cost:[4B];"+"            |";
                card[2]="|In:[1G]; "+"Out:[2F]; "+"VP:5;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 27:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:2; "+"Cost:[3B, 3Y];"+"        |";
                card[2]="|In:[1Y]; "+"Out:[2B, 1F]; "+"VP:8;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 28:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:2; "+"Cost:[5B];"+"            |";
                card[2]="|In:[2Y]; "+"Out:[2G, 2F]; "+"VP:7;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 29:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:2; "+"Cost:[3G, 2B];"+"       |";
                card[2]="|In:[1G, 1B]; "+"Out:[3Y]; "+"VP:6;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 30:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:2; "+"Cost:[3G, 3P];"+"       |";
                card[2]="|In:[1B]; "+"Out:[2Y, 1F]; "+"VP:8;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 31:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:2; "+"Cost:[5G];"+"           |";
                card[2]="|In:[2B]; "+"Out:[2P, 2F]; "+"VP:7;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 32:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:2; "+"Cost:[4G];"+"           |";
                card[2]="|In:[1B]; "+"Out:[2F]; "+"VP:5;"+"                |";
                card[3]="+----------------------------------------+";
                return card;
            case 33:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:3; "+"Cost:[4P, 4B];"+"       |";
                card[2]="|In:[1Y]; "+"Out:[3G, 1P]; "+"VP:12;"+"           |";
                card[3]="+----------------------------------------+";
                return card;
            case 34:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:3; "+"Cost:[5P, 2Y];"+"       |";
                card[2]="|In:[1G, 1B]; "+"Out:[2Y, 2P, 1F]; "+"VP:10;"+"   |";
                card[3]="+----------------------------------------+";
                return card;
            case 35:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:3; "+"Cost:[7P];"+"           |";
                card[2]="In:[1Y]; "+"Out:[1G, 3F]; "+"VP:11;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 36:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Purple; "+"Lev:3; "+"Cost:[6P];"+"           |";
                card[2]="|In:[2G]; "+"Out:[3Y, 2F]; "+"VP:9;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 37:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:3; "+"Cost:[4Y, 4G];"+"         |";
                card[2]="|In:[1P]; "+"Out:[1Y, 3B]; "+"VP:12;"+"           |";
                card[3]="+----------------------------------------+";
                return card;
            case 38:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:3; "+"Cost:[6Y];"+"             |";
                card[2]="|In:[2P]; "+"Out:[3B, 2F]; "+"VP:9;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 39:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:3; "+"Cost:[7Y];"+"             |";
                card[2]="|In:[1G]; "+"Out:[1B, 3F]; "+"VP:11;"+"           |";
                card[3]="+----------------------------------------+";
                return card;
            case 40:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Blue; "+"Lev:3; "+"Cost:[5Y, 2G];"+"         |";
                card[2]="|In:[1Y, 1B]; "+"Out:[2P, 2G, 1F]; "+"VP:10;"+"   |";
                card[3]="+----------------------------------------+";
                return card;
            case 41:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:3; "+"Cost:[5B, 2P];"+"        |";
                card[2]="|In:[1Y, 1P]; "+"Out:[2B, 2G, 1F]; "+"VP:10;"+"   |";
                card[3]="+----------------------------------------+";
                return card;
            case 42:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:3; "+"Cost:[6B];"+"            |";
                card[2]="|In:[2P]; "+"Out:[3G, 2F]; "+"VP:9;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 43:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:3; "+"Cost:[4B, 4Y];"+"        |";
                card[2]="|In:[1G]; "+"Out:[3Y, 1B]; "+"VP:12;"+"           |";
                card[3]="+----------------------------------------+";
                return card;
            case 44:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Green; "+"Lev:3; "+"Cost:[7B];"+"            |";
                card[2]="|In:[1P]; "+"Out:[1Y, 3F]; "+"VP:11;"+"           |";
                card[3]="+----------------------------------------+";
                return card;
            case 45:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:3; "+"Cost:[6G];"+"           |";
                card[2]="|In:[2B]; "+"Out:[3P, 2F]; "+"VP:9;"+"            |";
                card[3]="+----------------------------------------+";
                return card;
            case 46:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:3; "+"Cost:[7G];"+"           |";
                card[2]="|In:[1B]; "+"Out:[1P, 3F]; "+"VP:11;"+"           |";
                card[3]="+----------------------------------------+";
                return card;
            case 47:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:3; "+"Cost:[4G, 4P];"+"       |";
                card[2]="|In:[1B]; "+"Out:[1G, 3P]; "+"VP:12;"+"           |";
                card[3]="+----------------------------------------+";
                return card;
            case 48:
                card[0]="+----------------------------------------+";
                card[1]="|Col:Yellow; "+"Lev:3; "+"Cost: [5G, 2P];"+"      |";
                card[2]="|In:[1G, 1P]; "+"Out:[2Y, 2B, 1F]; "+"VP:10;"+"   |";
                card[3]="+----------------------------------------+";
                return card;
        }
        return new String[]{"","","",""};
    }

    /**
     * This method returns the discounted cost of a certain develop card.
     * @param id is the ID of the develop card.
     * @param colors is a list of discounts.
     * @return
     */
    public static ArrayList<String> getCostById(int id, ArrayList<String> colors){
        ArrayList<String> cost =  new ArrayList<>();
        switch(id) {
            case 1:
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 2:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 3:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 4:
                cost.add("BLUE");
                cost.add("PURPLE");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 5:
                cost.add("YELLOW");
                cost.add("PURPLE");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 6:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 7:
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 8:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 9:
                cost.add("BLUE");
                cost.add("PURPLE");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 10:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 11:
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 12:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 13:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 14:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 15:
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 16:
                cost.add("BLUE");
                cost.add("GREY");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 17:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 18:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 19:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 20:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 21:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 22:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 23:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 24:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 25:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 26:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 27:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 28:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 29:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 30:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 31:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 32:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 33:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 34:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 35:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 36:
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 37:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 38:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 39:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 40:
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 41:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 42:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 43:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                cost.add("YELLOW");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 44:
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                cost.add("BLUE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 45:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 46:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 47:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            case 48:
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("GREY");
                cost.add("PURPLE");
                cost.add("PURPLE");
                if(colors.get(0) != null)
                    cost.remove(colors.get(0));
                if(colors.get(1) != null)
                    cost.remove(colors.get(1));
                return cost;
            default: return null;
        }
    }

    /**
     * This method returns the input production of a certain develop card.
     * @param id is the ID of the develop card.
     * @return the input production of a certain develop card.
     */
    public static ArrayList<String> getInputById(int id){
        ArrayList<String> input = new ArrayList<>();
        switch (id) {
            case 1:
                input.add("GREY");
                return input;
            case 2:
                input.add("YELLOW");
                input.add("BLUE");
                return input;
            case 3:
                input.add("YELLOW");
                input.add("YELLOW");
                return input;
            case 4:
                input.add("YELLOW");
                return input;
            case 5:
                input.add("PURPLE");
                return input;
            case 6:
                input.add("BLUE");
                input.add("GREY");
                return input;
            case 7:
                input.add("BLUE");
                return input;
            case 8:
                input.add("GREY");
                input.add("GREY");
                return input;
            case 9:
                input.add("GREY");
                return input;
            case 10:
                input.add("PURPLE");
                input.add("PURPLE");
                return input;
            case 11:
                input.add("YELLOW");
                return input;
            case 12:
                input.add("GREY");
                input.add("PURPLE");
                return input;
            case 13:
                input.add("BLUE");
                input.add("BLUE");
                return input;
            case 14:
                input.add("YELLOW");
                input.add("PURPLE");
                return input;
            case 15:
                input.add("PURPLE");
                return input;
            case 16:
                input.add("BLUE");
                return input;
            case 17:
                input.add("YELLOW");
                return input;
            case 18:
                input.add("GREY");
                input.add("GREY");
                return input;
            case 19:
                input.add("GREY");
                input.add("GREY");
                return input;
            case 20:
                input.add("YELLOW");
                input.add("PURPLE");
                return input;
            case 21:
                input.add("YELLOW");
                input.add("GREY");
                return input;
            case 22:
                input.add("PURPLE");
                input.add("PURPLE");
                return input;
            case 23:
                input.add("PURPLE");
                return input;
            case 24:
                input.add("PURPLE");
                return input;
            case 25:
                input.add("BLUE");
                input.add("PURPLE");
                return input;
            case 26:
                input.add("GREY");
                return input;
            case 27:
                input.add("YELLOW");
                return input;
            case 28:
                input.add("YELLOW");
                input.add("YELLOW");
                return input;
            case 29:
                input.add("GREY");
                input.add("BLUE");
                return input;
            case 30:
                input.add("BLUE");
                return input;
            case 31:
                input.add("BLUE");
                input.add("BLUE");
                return input;
            case 32:
                input.add("BLUE");
                return input;
            case 33:
                input.add("YELLOW");
                return input;
            case 34:
                input.add("GREY");
                input.add("BLUE");
                return input;
            case 35:
                input.add("YELLOW");
                return input;
            case 36:
                input.add("GREY");
                input.add("GREY");
                return input;
            case 37:
                input.add("PURPLE");
                return input;
            case 38:
                input.add("PURPLE");
                input.add("PURPLE");
                return input;
            case 39:
                input.add("GREY");
                return input;
            case 40:
                input.add("YELLOW");
                input.add("BLUE");
                return input;
            case 41:
                input.add("YELLOW");
                input.add("PURPLE");
                return input;
            case 42:
                input.add("PURPLE");
                input.add("PURPLE");
                return input;
            case 43:
                input.add("GREY");
                return input;
            case 44:
                input.add("PURPLE");
                return input;
            case 45:
                input.add("BLUE");
                input.add("BLUE");
                return input;
            case 46:
                input.add("BLUE");
                return input;
            case 47:
                input.add("BLUE");
                return input;
            case 48:
                input.add("GREY");
                input.add("PURPLE");
                return input;
            default: return null;
        }
    }

    /**
     * Method used to get a String representation of the action done by Lorenzo (SoloGame) based on the id of the activated
     * token.
     * @param id the id of the activated token
     * @return a String representation of the action done by Lorenzo
     */
    public static String getTokenById(int id){
        switch (id){
            case 1: return "Lorenzo discarded 2 Blue DevelopCard!";
            case 2: return "Lorenzo discarded 2 Green DevelopCard!";
            case 3: return "Lorenzo discarded 2 Purple DevelopCard!";
            case 4: return "Lorenzo discarded 2 Yellow DevelopCard!";
            case 5: return "Lorenzo did 2 steps on the FaithTrack!";
            case 6: return "Lorenzo did 1 step on the FaithTrack and shuffled the tokens!";
            default: return "";
        }
    }
}
