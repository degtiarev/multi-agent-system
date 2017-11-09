import java.util.ArrayList;
import java.util.Random;

public class Heater {

    String name;
    Random random;

    double amoutOfCredits = 500;
    double upCostLimit;
    int currentDemand = 0;

    int currentTemperature;
    int lowLimit;
    int upLimit;
    double consumptionCoefficient;

    Weather currentWeather;

    Auction auction;
    ArrayList<Double> energies = new ArrayList<Double>();

    double qMatrix[][];
    double rMatrix[][];
    double pMatrix[][];

    boolean isStepMaid = false;

    public Heater(String name, double consumptionCoefficient, int lowLimit, int upLimit) {
        this.name = name;
        this.lowLimit = lowLimit;
        this.upLimit = upLimit;
        this.consumptionCoefficient = consumptionCoefficient;
        random = new Random();
        currentTemperature = lowLimit + random.nextInt(upLimit - lowLimit + 1);

        int matrixSize = upLimit - lowLimit + 3;

        qMatrix = new double[matrixSize][matrixSize];
        rMatrix = new double[matrixSize][matrixSize];
        pMatrix = new double[matrixSize][matrixSize];


        for (int i=0; i<matrixSize; i++)
        {

            double reward = 50;
            double penalty = -50;
            double penaltyForStaying = -20;

            for (int j=0; j<matrixSize; j++)
            {
                qMatrix[i][j] = 0;
                pMatrix[i][j] = random.nextDouble();


                if (j==0 || j==matrixSize-1)
                {
                    rMatrix[i][j] = penalty;
                }

                else
                {
                    rMatrix[i][j] = reward;
                    reward += 10;
                }

                if (i==j)
                {
                    rMatrix[i][j] = penaltyForStaying;

                }

                if ((i==j && j==0 ) || (i==j && j==matrixSize-1))
                {
                    rMatrix[i][j] = penalty + penaltyForStaying;
                }

            }


        }

    }


    public void computeEnergyDemand() {

        System.out.print(name + " " + "demands for 6 ours: ");
        for (int i = lowLimit; i <= upLimit; i++) {

            double energy = 6 * consumptionCoefficient + 0.5 * (i - currentWeather.getTemperature());

            if ( i > lowLimit) energy = energy - energies.get(0);
            energies.add(energy);

            System.out.print(energy + " ");
        }
        System.out.println();
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public void makeStep() {
        computeEnergyDemand();

        int stage = currentTemperature - lowLimit;
        upCostLimit = qMatrix[stage][stage+1] - auction.getPrice() * energies.get(currentDemand);
        double currentPenalty = qMatrix[stage][stage];

        double currentPrice = auction.getPrice();
        double cost = currentPrice * currentDemand;



        if (cost < amoutOfCredits)
            if (!isStepMaid && currentPenalty < upCostLimit )
        {



        }


    }



}
