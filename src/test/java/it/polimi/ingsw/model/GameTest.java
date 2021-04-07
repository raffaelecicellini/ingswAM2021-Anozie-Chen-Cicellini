package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.InvalidActionException;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {


    /**
     * lowercasing a map
     */
    @Test
    public void fromMarket1(){

        Map<String, String> map = new HashMap<>();
        map.put("ROW", "4");
        map.put("CoL", "2");

        Map<String, String> mapCopy = map.entrySet().stream().collect(Collectors.toMap(
                e1 -> e1.getKey().toLowerCase(Locale.ROOT),
                e1 -> e1.getValue().toLowerCase(Locale.ROOT)));

        System.out.println(mapCopy);
    }
}
