import java.util.ArrayList;

public class Main {

    // Agent A
    private static final double consumtionCoeficintA = 0.2;
    private static final int lowLimitA = 23;
    private static final int upLimitA = 25;

    // Agent B
    private static final double consumtionCoeficintB = 0.25;
    private static final int lowLimitB = 20;
    private static final int upLimitB = 23;

    private static Heater agentA = new Heater("Agent A", consumtionCoeficintA, lowLimitA, upLimitA);
    private static Heater agentB = new Heater("Agent B", consumtionCoeficintB, lowLimitB, upLimitB);
    private static Controller agentC = new Controller();
    private static ArrayList<Weather> weatherSet = new ArrayList<Weather>();

    public static void main(String[] args) {

        agentA.setAuction(agentC.getAuction());
        agentB.setAuction(agentC.getAuction());
        populateWeatherSet();

        int i = 0;

        for (Weather weatherItem : weatherSet) {
            System.out.println("Step: " + i);

            agentA.setCurrentWeather(weatherItem);
            agentB.setCurrentWeather(weatherItem);
            agentC.setCurrentWeather(weatherItem);

            agentA.makeStep();
            agentB.makeStep();
            agentC.makeStep(agentA, agentB);

            i++;

            System.out.println("*********************************************************");
            System.out.println();
        }

    }

    static void populateWeatherSet() {
        // 1st day Monday 06//11/2017
        Weather weather1 = new Weather(false, 0.1, 2);
        Weather weather2 = new Weather(true, 0.1, 2);
        Weather weather3 = new Weather(true, 0.3, 4);
        Weather weather4 = new Weather(false, 0.5, 2);

        // 2nd day Tuesday 07/11/2017
        Weather weather5 = new Weather(false, 0.5, 2);
        Weather weather6 = new Weather(true, 0.1, 3);
        Weather weather7 = new Weather(true, 0.3, 4);
        Weather weather8 = new Weather(false, 0.1, 3);

        // 3rd day Wednesday, 08/11/2017
        Weather weather9 = new Weather(false, 0.3, 5);
        Weather weather10 = new Weather(true, 0.1, 5);
        Weather weather11 = new Weather(true, 0.1, 4);
        Weather weather12 = new Weather(false, 0.3, 4);

        // 4th day Thursday 09/11/2017
        Weather weather13 = new Weather(false, 0.5, 5);
        Weather weather14 = new Weather(true, 0.3, 3);
        Weather weather15 = new Weather(true, 0.3, 2);
        Weather weather16 = new Weather(false, 0.5, 2);

        // 5th day Friday 10/11/2017
        Weather weather17 = new Weather(false, 0.5, 2);
        Weather weather18 = new Weather(true, 0.3, 2);
        Weather weather19 = new Weather(true, 0.5, 3);
        Weather weather20 = new Weather(false, 0.5, 2);

        // 6th day Saturday 11/11/2017
        Weather weather21 = new Weather(false, 0.3, 1);
        Weather weather22 = new Weather(true, 0.3, 0);
        Weather weather23 = new Weather(true, 0.5, 1);
        Weather weather24 = new Weather(false, 0.5, 0);

        // 7th day Sunday 12/11/2017
        Weather weather25 = new Weather(false, 0.3, 0);
        Weather weather26 = new Weather(true, 0.3, 1);
        Weather weather27 = new Weather(true, 0.3, 0);
        Weather weather28 = new Weather(false, 0.5, 0);

        weatherSet.add(weather1);
        weatherSet.add(weather2);
        weatherSet.add(weather3);
        weatherSet.add(weather4);
        weatherSet.add(weather5);
        weatherSet.add(weather6);
        weatherSet.add(weather7);
        weatherSet.add(weather8);
        weatherSet.add(weather9);
        weatherSet.add(weather10);
        weatherSet.add(weather11);
        weatherSet.add(weather12);
        weatherSet.add(weather13);
        weatherSet.add(weather14);
        weatherSet.add(weather15);
        weatherSet.add(weather16);
        weatherSet.add(weather17);
        weatherSet.add(weather18);
        weatherSet.add(weather19);
        weatherSet.add(weather20);
        weatherSet.add(weather21);
        weatherSet.add(weather22);
        weatherSet.add(weather23);
        weatherSet.add(weather24);
        weatherSet.add(weather25);
        weatherSet.add(weather26);
        weatherSet.add(weather27);
        weatherSet.add(weather28);
    }

}
