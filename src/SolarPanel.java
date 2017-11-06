public class SolarPanel {

    private static final double peakPower  = 3;

    double sunLevel;
    double amountOfGeneratedEnergy;


    public void setSunLevel(double sunLevel)
    {
        this.sunLevel = sunLevel;

    }

    public void generateEnergy()
    {
        amountOfGeneratedEnergy = peakPower * sunLevel;
    }


    public double getAmountOfGeneratedEnergy() {
        return amountOfGeneratedEnergy;
    }

}
