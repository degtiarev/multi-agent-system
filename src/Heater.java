import java.util.ArrayList;

public class Heater {

    String name;

    double amoutOfCredits = 500;
    int lowLimit;
    int upLimit;
    double consumptionCoefficient;

    Weather currentWeather;
    ArrayList<Double> energies = new ArrayList<Double>();

    double qMatrix[][];
    double rMatrix[];


    public Heater(String name, double consumptionCoefficient, int lowLimit, int upLimit) {
        this.name = name;
        this.lowLimit = lowLimit;
        this.upLimit = upLimit;
        this.consumptionCoefficient = consumptionCoefficient;

        int matrixSize = upLimit - lowLimit + 3;

        qMatrix = new double[matrixSize][2];
        rMatrix = new double[matrixSize];

        double reward=50;
        for (int i=0; i<matrixSize; i++)
        {

            // populating Q matrix
            for (int j=0; j<2; j++)
            {
                qMatrix[i][j] = 0;
            }

            // populating R matrix
            if ((i==0) || (i==matrixSize-1) )
            {
                rMatrix[i]=-50;
            }

            else
            {
                rMatrix[i]=reward;
                reward += 10;
            }

        }

    }


    public void computeEnergyDemand() {

        System.out.print(name + " " + "demands for 6 ours: ");
        for (int i = lowLimit; i <= upLimit; i++) {

            double energy = 6 * consumptionCoefficient + 0.5 * (i - currentWeather.getTemperature());
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

    public void buyEnergy()
    {

    }

}
