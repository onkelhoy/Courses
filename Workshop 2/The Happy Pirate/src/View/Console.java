package View;

import java.util.Scanner;

/**
 * Created by henry on 2016-09-20.
 */
abstract class Console {
    protected Scanner scan;

    public Console(){
        scan = new Scanner(System.in);
    }

    protected String GetAns(String question){
        // should clear window.. but java has no correct way (as usual)
        System.out.print(question); //prints question
        return scan.next().toLowerCase();   //returns ans
    }
}
