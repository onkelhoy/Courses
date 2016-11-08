import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by henry on 2016-11-08.
 */
public class MainFCFS {
    //this is just a temp class to quickly check if it is working or not.. (tests can take some time..)
    public static void main(String[] args){
        ArrayList<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 0, 20));
        processes.add(new Process(2, 2, 3));
        processes.add(new Process(3, 8, 7));

        FCFS myFcfs = new FCFS(processes);
        myFcfs.run();

        myFcfs.printGanttChart();
        System.out.println("");
        myFcfs.printTable();
    }
}
