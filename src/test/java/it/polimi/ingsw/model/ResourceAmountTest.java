package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.ResourceAmount;
import org.junit.jupiter.api.Test;

/**
 * ResourceAmountTest class tests ResourceAmount class.
 */
public class ResourceAmountTest {

    /**
     * Random value setting and getting test
     */
    @Test
    public void test2() {
        Color color1 = Color.BLUE;
        ResourceAmount resourceAmount = new ResourceAmount(color1, 7);

        System.out.println(resourceAmount.getColor() + " " + resourceAmount.getAmount());

        resourceAmount.setColor(Color.PURPLE);

        resourceAmount.setAmount(resourceAmount.getAmount() + 2);

        System.out.println(resourceAmount.getColor() + " " + resourceAmount.getAmount());
    }
}
