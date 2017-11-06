import java.util.ArrayList;

public class Heater {

    double amoutOfCredits = 500;
    int lowLimit;
    int upLimit;
    double consumptionCoefficient;
    double currentTemperature;


    Weather currentWeather;


    public Heater(double consumptionCoefficient, int lowLimit, int upLimit) {
        this.lowLimit = lowLimit;
        this.upLimit = upLimit;
        this.consumptionCoefficient = consumptionCoefficient;
    }


    public ArrayList<Double> computeEnergyDemand(double currentTemperature) {
        ArrayList<Double> energies = new ArrayList<Double>();
        for (int i = lowLimit; i >= upLimit; i++) {

            double energy = consumptionCoefficient + 0.5 * (i - currentTemperature);
            energies.add(energy);
        }
        return energies;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }


    public void heat(double currentTemperature) {


    }

    public void getCredits() {

    }

    public void getEnergy() {

    }


}
