import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 * Created by henry on 2016-11-22.
 */
public class DiningPhilosopher {
    private ArrayList<Philosopher> philosophers;
    private ArrayList<Chopstick> chopsticks;
    private int miliseconds = 0;
    private boolean canRun = true;
    private Log log;

    public DiningPhilosopher(){}

    public ArrayList<Philosopher> getPhilosophers() { return philosophers; }
    public void setSimulationTime(int milis){
        miliseconds = milis;
    }

    public void initialize(Log log){
        chopsticks = new ArrayList<>();
        philosophers = new ArrayList<>();
        this.log = log;

        chopsticks.add(new Chopstick(0, log));
        for(int i = 0; i < 5; i++){
            if(i < 4) {
                chopsticks.add(new Chopstick(i+1, log));
                philosophers.add(new Philosopher(i, chopsticks.get(i), chopsticks.get(i+1), log));
            }
            else philosophers.add(new Philosopher(i, chopsticks.get(i), chopsticks.get(0), log));
        }
    }
    public void start(){
        ExecutorService exec = Executors.newFixedThreadPool(philosophers.size()+1);
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(miliseconds);
                    //simulation is done
                    System.out.println("Application reached its end");
                    log.msg("Application reached its end");
                    abort(exec);
                }
                catch(InterruptedException e){
                    //e.printStackTrace();
                }
            }
        });

        for(Philosopher p : philosophers){
            exec.submit(p);
        }

        exec.shutdown(); // no more threads

        while(canRun){
            int check = 0;
            int eating = 0;
            for(Philosopher p : philosophers){
                if(p.isHungry()) check++;
                if(p.isEating()) eating++;
            }

            if(check == 5 || eating > 2){
                //deadlock
                System.out.println("Deadlock detected");
                log.msg("Deadlock detected");
                abort(exec);
            }
        }
    }

    public void abort(ExecutorService exec){
        canRun = false;
        //abort all philosopher threads..
        exec.shutdownNow();
    }
}
