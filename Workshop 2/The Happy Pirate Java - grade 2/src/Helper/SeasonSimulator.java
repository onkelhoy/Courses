package Helper;

import Controller.YatchClub;
import sun.rmi.runtime.Log;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by henry on 2016-09-30.
 */
public class SeasonSimulator {
    public enum Time {
        off_season,
        pre_season,
        season
    }

    private Time currentSeason;
    private YatchClub club;

    public SeasonSimulator(Time season, YatchClub club){
        this.club = club;
        if(season == null) {
            Date d = new Date();
            int min = d.getMinutes();
            int dmin = 10 - min % 10;

            setSeason(min);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    deley();
                    timer.cancel();
                }
            }, 1000 * 60 * dmin, 1000 * 60 * dmin);
        }
        else {
            currentSeason = season;
            if(currentSeason.equals(Time.season)) club.newSeason();
            System.out.println(currentSeason.name());
        }
    }

    public Time GetSeason(){return currentSeason;}
    private void deley(){
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Date d = new Date();
                int min = d.getMinutes();

                setSeason(min);
            }
        },0,1000*60*10);
    }

    private void setSeason(int min){
        min /= 10;
        switch (min){
            case 3:
            case 0:
                currentSeason = Time.pre_season;
                break;
            case 4:
            case 1:
                club.newSeason();
                currentSeason = Time.season;
                break;
            case 5:
            case 2:
                currentSeason = Time.off_season;
                break;
        }

        //System.out.println("CURRENT SEASON: " + currentSeason.name().toUpperCase());
    }
}
