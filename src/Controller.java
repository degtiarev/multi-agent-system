import java.util.Random;

public class Controller {

    private static final double limit = 4.5 * 1.5;
    private static final double bidStep = 0.5;

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
        for (int i = 0; i < 6; i++) {
            additionalEnergy += r.nextDouble() * limit;
        }

        energyCeiling = solarEnergy + additionalEnergy;
        System.out.println("Agent C: ************************************************");
        System.out.println("Solar energy for 6 hours: " + solarEnergy);
        System.out.println("Additional energy for 6 hours: " + additionalEnergy);
        System.out.println("Total energy for 6 hours: " + energyCeiling);
        System.out.println("Amount of credits: " + creditAmount);
        System.out.println("*********************************************************");

    }

    void sellEnergy(Heater agentA, Heater agentB) {

        boolean isEnd = false;

        // Agent A
        double creditsA = agentA.getAmoutOfCredits();
        double demandA = agentA.getCurrentDemand();
        double[][] rMatrixA = agentA.getrMatrix();
        int currentTemperatureA = agentA.getCurrentTemperature();
        int currentRequredTemperatureA = agentA.getCurrentRequredTemperature();
        double maxPriceA = creditsA / demandA;
        int currentStageA = agentA.getCurrentStage();
        int currentRequiredStageA = agentA.getCurrentRequredStage();
        System.out.println("Agent A MaxPrice: " + maxPriceA);


        // Agent B
        double creditsB = agentB.getAmoutOfCredits();
        double demandB = agentB.getCurrentDemand();
        double[][] rMatrixB = agentB.getrMatrix();
        int currentTemperatureB = agentB.getCurrentTemperature();
        int currentRequredTemperatureB = agentB.getCurrentRequredTemperature();
        double maxPriceB = creditsB / demandB;
        int currentStageB = agentB.getCurrentStage();
        int currentRequiredStageB = agentB.getCurrentRequredStage();
        System.out.println("Agent B MaxPrice: " + maxPriceB);

        double currentPrice = auction.getPrice();
        double totalDemand = demandA + demandB;
        System.out.println("Current auction price: " + currentPrice);


        double paymentA = demandA * currentPrice;
        double paymentB = demandB * currentPrice;

        boolean isAboughtEnergy = false;
        boolean isBboughtEnergy = false;

        // try to sell energy (case without auction)
        if (totalDemand < energyCeiling && (maxPriceA > currentPrice || maxPriceB > currentPrice)) {
            System.out.println("Selling without auction...");

            if (paymentA < creditsA) {
                agentA.setAmoutOfCredits(agentA.getAmoutOfCredits() - paymentA); // withdraw payment for energy from heater
                this.setCreditAmount(creditAmount + paymentA); // take payment for energy to controller
                agentA.setCurrentTemperature(currentRequredTemperatureA); // set new temperature to heater

                // exhanging credits for reward/penalty according R matrix
                agentA.setAmoutOfCredits(agentA.getAmoutOfCredits() + rMatrixA[currentStageA][currentRequiredStageA]); // get reward to heater for being in curent state
                this.creditAmount -= rMatrixA[currentStageA][currentRequiredStageA]; // withdraw reward from controller

                isAboughtEnergy = true;
                System.out.println("Agent A bought all need energy");
            }

            if (paymentB < creditsB) {
                agentB.setAmoutOfCredits(agentB.getAmoutOfCredits() - paymentB); // withdraw payment for energy
                this.setCreditAmount(creditAmount + paymentB); // take payment for energy
                agentB.setCurrentTemperature(currentRequredTemperatureB); // set new temperature to agent

                // exhanging credits for reward/penalty according R matrix
                agentB.setAmoutOfCredits(agentB.getAmoutOfCredits() + rMatrixB[currentStageB][currentRequiredStageB]); // get reward to heater for being in curent state
                this.creditAmount -= rMatrixB[currentStageB][currentRequiredStageB]; // withdraw reward from controller


                isBboughtEnergy = true;
                System.out.println("Agent B bought all need energy");

            }

        }

        // penalty if somebody did not bought energy
        if (isAboughtEnergy || isBboughtEnergy) {

            // somebody bought energy, we don't need to proceed and provide auction
            isEnd = true;

            if (!isAboughtEnergy) {
                agentA.setAmoutOfCredits(agentA.getAmoutOfCredits() + rMatrixA[currentStageA][currentStageA]); // get penalty to heater for being in curent state
                System.out.println("Agent A did not bought energy, got penalty");

                // exhanging credits for reward/penalty according R matrix
                agentA.setAmoutOfCredits(agentA.getAmoutOfCredits() + rMatrixA[currentStageA][currentStageA]); // get reward to heater for being in curent state
                this.creditAmount -= rMatrixA[currentStageA][currentStageA]; // withdraw reward from controller


            }

            if (!isBboughtEnergy) {
                agentB.setAmoutOfCredits(agentB.getAmoutOfCredits() + rMatrixB[currentStageB][currentStageB]); // get penalty to heater for being in curent state
                System.out.println("Agent B did not bought energy, got penalty");

                // exhanging credits for reward/penalty according R matrix
                agentB.setAmoutOfCredits(agentB.getAmoutOfCredits() + rMatrixB[currentStageB][currentStageB]); // get reward to heater for being in curent state
                this.creditAmount -= rMatrixB[currentStageB][currentStageB]; // withdraw reward from controller


            }
        }


        // if nobody already bought energy
        if (!isEnd) {

            // define which type of auction provide
            boolean isEnglishAuction = true;
            if (paymentA > creditsA && paymentB > creditsB)
                isEnglishAuction = false;

            boolean isAstarts = false;
            double random = r.nextDouble();
            if (random > 0.5) {
                isAstarts = true;
                System.out.println("Agent A starts auction");

            } else {
                System.out.println("Agent B starts auction");
            }

            int bidNum = 0;
            double currentBid = auction.getPrice();


            // English Auction
            if (isEnglishAuction) {
                System.out.println("Starting English Auction...");

                while (!isEnd) {

                    bidNum++;
                    currentBid = currentBid + bidStep;
                    System.out.println("Current bidnum: " + bidNum + " Current bid: " + currentBid);

                    if (currentBid > maxPriceA || currentBid > maxPriceB) {
                        isEnd = true;
                    }

                } // while
            }  // if (isEnglishAuction)


            // Dutch auction
            else {
                System.out.println("Starting Dutch Auction...");
                while (!isEnd) {

                    bidNum++;
                    currentBid = currentBid - bidStep;
                    System.out.println("Current bidnum: " + bidNum + " Current bid: " + currentBid);

                    if ((currentBid < maxPriceA) || (currentBid < maxPriceB)) {
                        isEnd = true;
                    }

                } // while
            } // if


            // define who's won by knowing who's started and numBid
            boolean isAwon = false;
            if (isAstarts && bidNum % 2 != 0) {
                System.out.println("Won A");
                isAwon = true;
            }

            if (isAstarts && bidNum % 2 == 0) {
                System.out.println("Won B");
                isAwon = false;
            }

            if (!isAstarts && bidNum % 2 != 0) {
                System.out.println("Won B");
                isAwon = false;
            }

            if (!isAstarts && bidNum % 2 == 0) {
                System.out.println("Won A");
                isAwon = true;
            }


            // update price
            auction.setPrice(currentBid);

            // set reward/penalty for agents
            if (isAwon) {
                System.out.println("Agent A got reward");
                agentA.setAmoutOfCredits(agentA.getAmoutOfCredits() - paymentA); // withdraw payment for energy
                this.setCreditAmount(creditAmount + paymentA); // take payment for energy
                agentA.setCurrentTemperature(currentRequredTemperatureA); // set new temperature to agent

                // exhanging credits for reward/penalty according R matrix
                agentA.setAmoutOfCredits(agentA.getAmoutOfCredits() + rMatrixA[currentStageA][currentRequiredStageA]); // get reward to heater for being in curent state
                this.creditAmount -= rMatrixA[currentStageA][currentRequiredStageA]; // withdraw reward from controller


                System.out.println("Agent B got penalty");
                // exhanging credits for reward/penalty according R matrix
                agentB.setAmoutOfCredits(agentB.getAmoutOfCredits() + rMatrixB[currentStageB][currentStageB]); // get reward to heater for being in curent state
                this.creditAmount -= rMatrixB[currentStageB][currentStageB]; // withdraw reward from controller


            } else {
                System.out.println("Agent B got reward");
                agentB.setAmoutOfCredits(agentB.getAmoutOfCredits() - paymentB); // withdraw payment for energy
                this.setCreditAmount(creditAmount + paymentB); // take payment for energy
                agentB.setCurrentTemperature(currentRequredTemperatureB); // set new temperature to agent

                // exhanging credits for reward/penalty according R matrix
                agentB.setAmoutOfCredits(agentB.getAmoutOfCredits() + rMatrixB[currentStageB][currentRequiredStageB]); // get reward to heater for being in curent state
                this.creditAmount -= rMatrixB[currentStageB][currentRequiredStageB]; // withdraw reward from controller

                System.out.println("Agent A got penalty");

                // exhanging credits for reward/penalty according R matrix
                agentA.setAmoutOfCredits(agentA.getAmoutOfCredits() + rMatrixA[currentStageA][currentStageA]); // get reward to heater for being in curent state
                this.creditAmount -= rMatrixA[currentStageA][currentStageA]; // withdraw reward from controller

            }

        } // !isEnd

        System.out.println("Updated Q matrix of agent A");
        agentA.showQmatrix();

        System.out.println("Updated Q matrix of agent B");
        agentB.showQmatrix();
    }


    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    void makeStep(Heater agentA, Heater agentB) {
        adjustCeiling();
        sellEnergy(agentA, agentB);
    }
}
