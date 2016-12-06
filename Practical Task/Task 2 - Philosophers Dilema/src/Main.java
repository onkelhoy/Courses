import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by henry on 2016-11-22.
 */
public class Main {
    public static void main(String args[]){
        try {
            Log log = new Log();
            DiningPhilosopher dp = new DiningPhilosopher();
            dp.setSimulationTime(10000);
            dp.initialize(log);
            dp.start();
            ArrayList<Philosopher> philosophers = dp.getPhilosophers();
            for (Philosopher p : philosophers) {
                System.out.println(p.getId() + " eating   - " + p.getAverageEatingTime());
                System.out.println(p.getId() + " thinking - " + p.getAverageThinkingTime());
                System.out.println(p.getId() + " waiting  - " + p.getAverageHungryTime());
            }

            log.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(1);
    }
}
