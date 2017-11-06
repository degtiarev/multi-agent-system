import java.util.ArrayList;

public class Heater {

    String name;

    double amoutOfCredits = 500;
    int lowLimit;
    int upLimit;
    double consumptionCoefficient;

    Weather currentWeather;
    ArrayList<Double> energies = new ArrayList<Double>();


    public Heater(String name, double consumptionCoefficient, int lowLimit, int upLimit) {
        this.name = name;
        this.lowLimit = lowLimit;
        this.upLimit = upLimit;
        this.consumptionCoefficient = consumptionCoefficient;
    }


    public void computeEnergyDemand() {

        System.out.print(name + " " + "demands: ");
        for (int i = lowLimit; i <= upLimit; i++) {

            double energy = consumptionCoefficient + 0.5 * (i - currentWeather.getTemperature());
            energies.add(energy);

            System.out.print(energy + " ");

        }
        System.out.println();
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }


    public void makeStep() {
        computeEnergyDemand();
    }


}
