public class Auction {

    double price = 3;
    boolean isParetoEfficient = false;

    String action(double bidA, double bidB) {
        if (bidA > bidB) return "AgentA";
        else return "AgentB";
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
