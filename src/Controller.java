import java.util.Random;

public class Controller {

    private static final double limit = 4.5 * 1.5;

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

        double additionalEnergy = 0;
        for (int i=0; i<6; i++) {
            additionalEnergy += r.nextDouble() * limit ;
        }

        energyCeiling = solarEnergy + additionalEnergy;

        System.out.println("Solar energy for 6 hours: " + solarEnergy);
        System.out.println("Additional energy for 6 hours: " + additionalEnergy);
        System.out.println("Total energy for 6 hours: " + energyCeiling);

    }

    void sellEnergy(Heater agentA, Heater agentB) {

        boolean isEnd = true;

        while (!isEnd)
        {


        }

    }


    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }


    void makeStep(Heater agentA, Heater agentB) {
        adjustCeiling();
        sellEnergy(agentA, agentB);

    }
}
