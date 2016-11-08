import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by henry on 2016-11-08.
 */
public class MainFCFS {
    //this is just a temp class to quickly check if it is working or not.. (tests can take some time..)
    public static void main(String[] args){
        ArrayList<Process> listOfProcesses = new ArrayList<>();
        listOfProcesses.add(new Process(1, 0, 18));
        listOfProcesses.add(new Process(2, 3, 2));
        listOfProcesses.add(new Process(3, 25, 5));
        listOfProcesses.add(new Process(4, 29, 2));
        listOfProcesses.add(new Process(5, 33, 7));


        int[] processIds = new int[]{1, 2, 3, 4, 5};
        int[] processCT  = new int[]{18, 20, 30, 32, 40};
        int[] processTAT = new int[]{18, 17, 5, 3, 7};
        int[] processWT  = new int[]{0, 15, 0, 1, 0};


        FCFS myFcfs = new FCFS(listOfProcesses);
        myFcfs.run();

        myFcfs.printGanttChart();
        System.out.println("");
        myFcfs.printTable();
    }
}
