/**
 * Created by henry on 2016-11-22.
 */
public class Philosopher {
    private int totalThinkingTime = 0,
                totalEatingTime = 0,
                totalHungryTime = 0,
                numberOfEatingTurns = 0;

    public Philosopher(){}


    //the public average methods
    public int getAverageThinkingTime(){
        return totalThinkingTime / numberOfEatingTurns;
    }
    public int getAverageEatingTime(){
        return totalEatingTime / numberOfEatingTurns;
    }
    public int getAverageHungryTime(){
        return totalHungryTime / numberOfEatingTurns;
    }
}
