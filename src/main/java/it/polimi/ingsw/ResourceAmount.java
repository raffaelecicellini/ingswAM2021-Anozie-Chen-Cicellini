package it.polimi.ingsw;

public class ResourceAmount {
    private Resource resource;
    private int amount;

    public ResourceAmount(Resource resource, int amount) {
        this.resource = resource;
        this.amount = amount;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
