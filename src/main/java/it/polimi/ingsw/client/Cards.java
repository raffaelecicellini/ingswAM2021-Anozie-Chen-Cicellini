package it.polimi.ingsw.client;

import java.util.ArrayList;


public class Cards {
    //formatted representation of a LeaderCard
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
    //representation of the color of the effect
    public static String getDiscountById(int id){
        switch (id){
            case 3: return "PURPLE";
            case 4: return "BLUE";
            case 5: return "GREY";
            case 6: return "YELLOW";
            default: return null;
        }
    }
    public static String getWhiteById(int id){
        switch (id){
            case 11: return "PURPLE";
            case 12: return "BLUE";
            case 13: return "GREY";
            case 14: return "YELLOW";
            default: return null;
        }
    }
    public static String getProductionById(int id){
        switch (id){
            case 15: return "BLUE";
            case 16: return "PURPLE";
            case 17: return "GREY";
            case 18: return "YELLOW";
            default: return null;
        }
    }
    //formatted representation of a DevelopCard
    public static String getDevelopById(int id){
        switch (id){
            case 1:
                return "Col:Purple; "+"Lev:1; "+"Cost:[2P]; "+"In:[1G]; "+"Out:[1F]; "+"VP:1;";
            case 2:
                return "Color: Purple; "+"Level: 1; "+"Cost: [2 PURPLE, 2 GREY]; "+"Input: [1 YELLOW, 1 BLUE]; "+"Output: [2 GREY, 1 Faith]; "+"Points: 4;";
            case 3:
                return "Color: Purple; "+"Level: 1; "+"Cost: [3 PURPLE]; "+"Input: [2 YELLOW]; "+"Output: [1 PURPLE, 1 BLUE, 1 GREY]; "+"Points: 3;";
            case 4:
                return "Color: Purple; "+"Level: 1; "+"Cost: [1 BLUE, 1 PURPLE, 1 YELLOW]; "+"Input: [1 YELLOW]; "+"Output: [1 BLUE]; "+"Points: 2;";
            case 5:
                return "Color: Blue; "+"Level: 1; "+"Cost: [1 YELLOW, 1 PURPLE, 1 GREY]; "+"Input: [1 PURPLE]; "+"Output: [1 GREY]; "+"Points: 2;";
            case 6:
                return "Color: Blue; "+"Level: 1; "+"Cost: [2 YELLOW 2 PURPLE]; "+"Input: [1 BLUE, 1 GREY]; "+"Output: [2 PURPLE, 1 Faith]; "+"Points: 4;";
            case 7:
                return "Col:Blue; "+"Lev:1; "+"Cost:[2Y]; "+"In:[1B]; "+"Out:[1F]; "+"VP:1;";
            case 8:
                return "Col:Blue; "+"Lev:1; "+"Cost:[3Y]; "+"In:[2G]; "+"Out:[1Y, 1P, 1B]; "+"VP:3;";
            case 9:
                return "Color: Green; "+"Level: 1; "+"Cost: [1 BLUE, 1 PURPLE, 1 GREY]; "+"Input: [1 GREY]; "+"Output: [1 PURPLE]; "+"Points: 2;";
            case 10:
                return "Col:Green; "+"Lev:1; "+"Cost:[3B]; "+"In:[2P]; "+"Out:[1Y, 1B, 1G]; "+"VP:3;";
            case 11:
                return "Color: Green; "+"Level: 1; "+"Cost: [2 BLUE]; "+"Input: [1 YELLOW]; "+"Output: [1 Faith]; "+"Points: 1;";
            case 12:
                return "Color: Green; "+"Level: 1; "+"Cost: [2 BLUE, 2 YELLOW]; "+"Input: [1 GREY, 1 PURPLE]; "+"Output: [2 YELLOW, 1 Faith]; "+"Points: 4;";
            case 13:
                return "Color: Yellow; "+"Level: 1; "+"Cost: [3 GREY]; "+"Input: [2 BLUE]; "+"Output: [1 YELLOW, 1 PURPLE, 1 GREY]; "+"Points: 3;";
            case 14:
                return "Color: Yellow; "+"Level: 1; "+"Cost: [2 GREY, 2 BLUE]; "+"Input: [1 YELLOW, 1 PURPLE]; "+"Output: [2 BLUE, 1 Faith]; "+"Points: 4;";
            case 15:
                return "Color: Yellow; "+"Level: 1; "+"Cost: [2 GREY]; "+"Input: [1 PURPLE]; "+"Output: [1 Faith]; "+"Points: 1;";
            case 16:
                return "Color: Yellow; "+"Level: 1; "+"Cost: [1 BLUE, 1 GREY, 1 YELLOW]; "+"Input: [1 BLUE]; "+"Output: [1 YELLOW]; "+"Points: 2;";
            case 17:
                return "Color: Purple; "+"Level: 2; "+"Cost: [4 PURPLE]; "+"Input: [1 YELLOW]; "+"Output: [2 Faith]; "+"Points: 5;";
            case 18:
                return "Color: Purple; "+"Level: 2; "+"Cost: [3 PURPLE, 3 BLUE]; "+"Input: [2 GREY]; "+"Output: [2 PURPLE, 1 Faith]; "+"Points: 8;";
            case 19:
                return "Color: Purple; "+"Level: 2; "+"Cost: [5 PURPLE]; "+"Input: [2 GREY]; "+"Output: [2 YELLOW, 2 Faith]; "+"Points: 7;";
            case 20:
                return "Color: Purple; "+"Level: 2; "+"Cost: [3 PURPLE, 2 YELLOW]; "+"Input: [1 YELLOW, 1 PURPLE]; "+"Output: [3 BLUE]; "+"Points: 6;";
            case 21:
                return "Color: Blue; "+"Level: 2; "+"Cost: [3 YELLOW, 2 GREY]; "+"Input: [1 YELLOW, 1 GREY]; "+"Output: [3 PURPLE]; "+"Points: 6;";
            case 22:
                return "Color: Blue; "+"Level: 2; "+"Cost: [5 YELLOW]; "+"Input: [2 PURPLE]; "+"Output: [2 BLUE, 2 Faith]; "+"Points: 7;";
            case 23:
                return "Color: Blue; "+"Level: 2; "+"Cost: [4 YELLOW]; "+"Input: [1 PURPLE]; "+"Output: [2 Faith]; "+"Points: 5;";
            case 24:
                return "Color: Blue; "+"Level: 2; "+"Cost: [3 YELLOW, 3 GREY]; "+"Input: [1 PURPLE]; "+"Output: [2 GREY, 1 Faith]; "+"Points: 8;";
            case 25:
                return "Color: Green; "+"Level: 2; "+"Cost: [3 BLUE, 2 PURPLE]; "+"Input: [1 BLUE, 1 PURPLE]; "+"Output: [3 GREY]; "+"Points: 6;";
            case 26:
                return "Color: Green; "+"Level: 2; "+"Cost: [4 BLUE]; "+"Input: [1 GREY]; "+"Output: [2 Faith]; "+"Points: 5;";
            case 27:
                return "Color: Green; "+"Level: 2; "+"Cost: [3 BLUE, 3 YELLOW]; "+"Input: [1 YELLOW]; "+"Output: [2 BLUE, 1 Faith]; "+"Points: 8;";
            case 28:
                return "Color: Green; "+"Level: 2; "+"Cost: [5 BLUE]; "+"Input: [2 YELLOW]; "+"Output: [2 GREY, 2 Faith]; "+"Points: 7;";
            case 29:
                return "Color: Yellow; "+"Level: 2; "+"Cost: [3 GREY, 2 BLUE]; "+"Input: [1 GREY, 1 BLUE]; "+"Output: [3 YELLOW]; "+"Points: 6;";
            case 30:
                return "Color: Yellow; "+"Level: 2; "+"Cost: [3 GREY, 3 PURPLE]; "+"Input: [1 BLUE]; "+"Output: [2 YELLOW, 1 Faith]; "+"Points: 8;";
            case 31:
                return "Color: Yellow; "+"Level: 2; "+"Cost: [5 GREY]; "+"Input: [2 BLUE]; "+"Output: [2 PURPLE, 2 Faith]; "+"Points: 7;";
            case 32:
                return "Color: Yellow; "+"Level: 2; "+"Cost: [4 GREY]; "+"Input: [1 BLUE]; "+"Output: [2 Faith]; "+"Points: 5;";
            case 33:
                return "Color: Purple; "+"Level: 3; "+"Cost: [4 PURPLE, 4 BLUE]; "+"Input: [1 YELLOW]; "+"Output: [3 GREY, 1 PURPLE]; "+"Points: 12;";
            case 34:
                return "Color: Purple; "+"Level: 3; "+"Cost: [5 PURPLE, 2 YELLOW]; "+"Input: [1 GREY, 1 BLUE]; "+"Output: [2 YELLOW, 2 PURPLE, 1 Faith]; "+"Points: 10;";
            case 35:
                return "Color: Purple; "+"Level: 3; "+"Cost: [7 PURPLE]; "+"Input: [1 YELLOW]; "+"Output: [1 GREY, 3 Faith]; "+"Points: 11;";
            case 36:
                return "Color: Purple; "+"Level: 3; "+"Cost: [6 PURPLE]; "+"Input: [2 GREY]; "+"Output: [3 YELLOW, 2 Faith]; "+"Points: 9;";
            case 37:
                return "Color: Blue; "+"Level: 3; "+"Cost: [4 YELLOW, 4 GREY]; "+"Input: [1 PURPLE]; "+"Output: [1 YELLOW, 3 BLUE]; "+"Points: 12;";
            case 38:
                return "Color: Blue; "+"Level: 3; "+"Cost: [6 YELLOW]; "+"Input: [2 PURPLE]; "+"Output: [3 BLUE, 2 Faith]; "+"Points: 9;";
            case 39:
                return "Color: Blue; "+"Level: 3; "+"Cost: [7 YELLOW]; "+"Input: [1 GREY]; "+"Output: [1 BLUE, 3 Faith]; "+"Points: 11;";
            case 40:
                return "Color: Blue; "+"Level: 3; "+"Cost: [5 YELLOW, 2 GREY]; "+"Input: [1 YELLOW, 1 BLUE]; "+"Output: [2 PURPLE, 2 GREY, 1 Faith]; "+"Points: 10;";
            case 41:
                return "Color: Green; "+"Level: 3; "+"Cost: [5 BLUE, 2 PURPLE]; "+"Input: [1 YELLOW, 1 PURPLE]; "+"Output: [2 BLUE, 2 GREY, 1 Faith]; "+"Points: 10;";
            case 42:
                return "Color: Green; "+"Level: 3; "+"Cost: [6 BLUE]; "+"Input: [2 PURPLE]; "+"Output: [3 GREY, 2 Faith]; "+"Points: 9;";
            case 43:
                return "Color: Green; "+"Level: 3; "+"Cost: [4 BLUE, 4 YELLOW]; "+"Input: [1 GREY]; "+"Output: [3 YELLOW, 1 BLUE]; "+"Points: 12;";
            case 44:
                return "Color: Green; "+"Level: 3; "+"Cost: [7 BLUE]; "+"Input: [1 PURPLE]; "+"Output: [1 YELLOW, 3 Faith]; "+"Points: 11;";
            case 45:
                return "Color: Yellow; "+"Level: 3; "+"Cost: [6 GREY]; "+"Input: [2 BLUE]; "+"Output: [3 PURPLE, 2 Faith]; "+"Points: 9;";
            case 46:
                return "Color: Yellow; "+"Level: 3; "+"Cost: [7 GREY]; "+"Input: [1 BLUE]; "+"Output: [1 PURPLE, 3 Faith]; "+"Points: 11;";
            case 47:
                return "Color: Yellow; "+"Level: 3; "+"Cost: [4 GREY, 4 PURPLE]; "+"Input: [1 BLUE]; "+"Output: [1 GREY, 3 PURPLE]; "+"Points: 12;";
            case 48:
                return "Color: Yellow; "+"Level: 3; "+"Cost: [5 GREY, 2 PURPLE]; "+"Input: [1 GREY, 1 PURPLE]; "+"Output: [2 YELLOW, 2 BLUE, 1 Faith]; "+"Points: 10;";
        }
        return null;
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

}
