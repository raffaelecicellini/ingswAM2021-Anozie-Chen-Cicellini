package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

public class ResourceAmountTest {
    @Test
    public void test2(){
        Resource resource = new Resource(Color.RED);
        ResourceAmount resourceAmount = new ResourceAmount(resource, 7);

        System.out.println(resourceAmount.getResource().getType() + " " + resourceAmount.getAmount());

        resourceAmount.setResource(new Resource(Color.PURPLE));

        resourceAmount.setAmount(resourceAmount.getAmount()+2);

        System.out.println(resourceAmount.getResource().getType() + " " + resourceAmount.getAmount());
    }
}
