package it.polimi.ingsw;

import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;

public class ResourceTest {

    @Test
    public void test1() {
        Resource resource = new Resource(Color.RED);
        System.out.println(resource.getType());
    }
}
