import Controller.YatchClub;
import Helper.Pnr;
import Helper.SeasonSimulator;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by henry on 2016-09-20.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        SeasonSimulator.Time time = null; // can be set to season, pre_season and off_season

        YatchClub TPH = new YatchClub(new SeasonSimulator(time));

    }
}
