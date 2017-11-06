import java.util.Random;

public class Controller {

    private static final double limit = 4.5;

    SolarPanel solarPanel = new SolarPanel();
    Auction auction = new Auction();
    private Weather currentWeather;

    double energyCeiling;
    double creditAmount = 10000;


    Random r = new Random();

    void adjustCeiling() {
        double solarEnergy = 0;

        if (currentWeather.isSunShines) {
            solarPanel.setSunLevel(currentWeather.getSunLevel());
            solarPanel.generateEnergy();
            solarEnergy = solarPanel.getAmountOfGeneratedEnergy();
        }

        double additionalEnergy = r.nextDouble() * limit;

        energyCeiling = solarEnergy + additionalEnergy;

        System.out.println("Solar energy:" + solarEnergy);
        System.out.println("Additional energy:" + additionalEnergy);
        System.out.println("Total energy:" + energyCeiling);

    }


    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }


    void makeStep() {
        adjustCeiling();


    }
}
