public class Weather {

    boolean isSunShines;
    double sunLevel;
    double temperature;

    public Weather(boolean isSunShines, double sunLevel, double temperature) {
        this.isSunShines = isSunShines;
        this.sunLevel = sunLevel;
        this.temperature = temperature;
    }

    public boolean isSunShines() {
        return isSunShines;
    }

    public double getSunLevel() {
        return sunLevel;
    }

    public double getTemperature() {
        return temperature;
    }

}
