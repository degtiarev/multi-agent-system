import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class Heater {

    String name;
    Random random;
    double consumptionCoefficient;
    Weather currentWeather;
    private static final double gamma = 0.5;

    double amoutOfCredits = 500;
    double currentDemand = 0;
    int currentRequredTemperature = 0;

    int currentTemperature;
    int lowLimit;
    int upLimit;

    double qMatrix[][];

    double rMatrix[][];
    double pMatrix[][];
    int matrixSize;


    public Heater(String name, double consumptionCoefficient, int lowLimit, int upLimit) {
        this.name = name;
        this.lowLimit = lowLimit;
        this.upLimit = upLimit;
        this.consumptionCoefficient = consumptionCoefficient;
        random = new Random();
        currentTemperature = lowLimit + random.nextInt(upLimit - lowLimit + 1);

        matrixSize = upLimit - lowLimit + 3;

        qMatrix = new double[matrixSize][matrixSize];
        rMatrix = new double[matrixSize][matrixSize];
        pMatrix = new double[matrixSize][matrixSize];


        for (int i = 0; i < matrixSize; i++) {

            double reward = 50;
            double penalty = -50;
            double penaltyForStaying = -20;

            for (int j = 0; j < matrixSize; j++) {
                qMatrix[i][j] = 0;
                pMatrix[i][j] = random.nextDouble();


                if (j == 0 || j == matrixSize - 1) {
                    rMatrix[i][j] = penalty;
                } else {
                    rMatrix[i][j] = reward;
                    reward += 10;
                } // else

                if (i == j) {
                    rMatrix[i][j] = rMatrix[i][j] + penaltyForStaying;
                } // if

                if ((i == j && j == 0) || (i == j && j == matrixSize - 1)) {
                    rMatrix[i][j] = penalty + penaltyForStaying;
                } // if

            } //for j

        } // for i

        DecimalFormat df = new DecimalFormat("0.0");

        System.out.println("Creating " + name + "...");
        System.out.println("Low limit: " + lowLimit);
        System.out.println("Up limit: " + upLimit);
        System.out.println("Initial temperature: " + currentTemperature);
        System.out.println("Initial Q matrix: ");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(df.format(qMatrix[i][j]) + " ");
            }
            System.out.println();
        }


        System.out.println("R matrix: ");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(df.format(rMatrix[i][j]) + " ");
            }
            System.out.println();
        }

        System.out.println("P matrix: ");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(df.format(pMatrix[i][j]) + " ");
            }
            System.out.println();
        }

    }


    public void computeEnergyDemand() {

        if (currentTemperature > lowLimit)
            currentTemperature--;

        int currentStage = currentTemperature - lowLimit + 1;

        double maxMaxTU = -100000000;
        int stageWithMaxTU = 0;
        for (int i = 0; i < matrixSize; i++) {
            double currentTU = pMatrix[currentStage][i] * qMatrix[currentStage][i];

            if (currentTU > maxMaxTU) {
                maxMaxTU = currentTU;
                stageWithMaxTU = i;
            }
        }

        double utility = rMatrix[currentStage][stageWithMaxTU] * gamma + maxMaxTU;
        qMatrix[currentStage][stageWithMaxTU] = utility;

        currentRequredTemperature = lowLimit + stageWithMaxTU - 1;

        currentDemand = 6 * consumptionCoefficient + 0.5 * (currentRequredTemperature - currentWeather.getTemperature());

        System.out.println(name + ": ************************************************");
        System.out.println("Demands for 6 ours: " + currentDemand);
        System.out.println("Amount of credits: " + amoutOfCredits);
        System.out.println("Current temperature: " + currentTemperature);
        System.out.println("Requred temperature: " + currentRequredTemperature);
        System.out.println("*********************************************************");

    }

    public double getCurrentDemand() {
        return currentDemand;
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public double getAmoutOfCredits() {
        return amoutOfCredits;
    }

    public void setAmoutOfCredits(double amoutOfCredits) {
        this.amoutOfCredits = amoutOfCredits;
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public double[][] getrMatrix() {
        return rMatrix;
    }

    public int getCurrentStage() {
        return currentTemperature - lowLimit + 1;
    }

    public int getCurrentRequredTemperature() {
        return currentRequredTemperature;
    }

    public int getCurrentRequredStage() {
        return currentRequredTemperature - lowLimit + 1;
    }

    public void showQmatrix() {
        DecimalFormat df = new DecimalFormat("0.0");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(df.format(qMatrix[i][j]) + " ");
            }
            System.out.println();
        }
    }

}
