import java.util.Random;

/**
 * Created by henry on 2016-11-22.
 */
public class Philosopher implements Runnable {
    private final Log log;
    private int totalThinkingTime = 0,
                totalEatingTime = 0,
                numberOfEatingTurns = 0,
                totalTime = 0,
                id = -1;
    private State currentState;
    private volatile Chopstick left, right;
    private boolean leftPicked = false, rightPicked = false;

    private Random random;

    @Override
    public void run() {
        Think();
    }


    public void Think(){
        if(!Thread.currentThread().isInterrupted()) {//avoid outprints..
            try {
                log.msg("Philosopher" + id + " is THINKING");
                int time = setTimeout();
                totalThinkingTime += time;
                Thread.currentThread().sleep(time);
                currentState = State.HUNGRY;
                log.msg("Philosopher" + id + " is HUNGRY");

                HunryWait();
            } catch (InterruptedException e) {}
        }
    }
    public void Eat(){
        if(!Thread.currentThread().isInterrupted()) {
            try {
                log.msg("Philosopher" + id + " is EATING");
                numberOfEatingTurns++;
                int time = setTimeout();
                totalEatingTime += time;
                Thread.currentThread().sleep(time);

                currentState = State.THINKING;
                //put down chopsticks..
                leftPicked = false;
                rightPicked = false;
                left.release(id);
                right.release(id);

                Think();
            } catch (InterruptedException e) {}
        }
    }
    public void HunryWait(){
        while(!rightPicked){
            if(Thread.currentThread().isInterrupted()){
                return;
            }
            if(!leftPicked && !left.isUsed()) {
                left.take(id);
                leftPicked = true;
            }
            if(leftPicked && !rightPicked && !right.isUsed()) {
                right.take(id);
                rightPicked = true;
            }
        }

        //done waiting
        currentState = State.EATING;
        Eat();
    }

    public enum State {
            EATING, HUNGRY, THINKING
    }

    public Philosopher(int id, Chopstick left, Chopstick right, Log log, int time){
        this.id = id;
        currentState = State.THINKING;
        random = new Random();
        this.left = left;
        this.right = right;
        this.log = log;

        totalTime = time;
    }


    //the public average methods
    public int getAverageThinkingTime(){
        return totalThinkingTime / numberOfEatingTurns;
    }
    public int getAverageEatingTime(){
        return totalEatingTime / numberOfEatingTurns;
    }
    public int getAverageHungryTime(){
        return (totalTime-totalEatingTime-totalThinkingTime) / numberOfEatingTurns;
    }
    public int getNumberOfEatingTurns() { return numberOfEatingTurns; }
    public int getId(){ return id; }

    private int setTimeout() throws InterruptedException{
        return random.nextInt(10)+1;
    }

    public boolean isHungry() {
        return currentState == State.HUNGRY;
    }
    public boolean isEating() {
        return currentState == State.EATING;
    }
}
