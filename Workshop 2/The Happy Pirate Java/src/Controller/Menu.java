package Controller;

import Helper.FileHandler;

import java.lang.reflect.Member;
import java.util.Scanner;

/**
 * Created by henry on 2016-09-20.
 */
public class Menu {
    private Scanner scan = null;
    public Menu(Scanner scan){
        AuthenticateMenu();
        this.scan = scan;
    }


    private void AuthenticateMenu(){

    }
    private void LoginMenu(){

    }
    private void RegistrationMenu(){

    }


    private void PreMenu(){
        System.out.println("\n\n----------------------------------------------------------------------------------------------------------------");
    }
    private void MSTmenu(String type){
        PreMenu();
        type = type.toLowerCase();
        System.out.print("0. Logout\n1. User info\n2. Boats\n3. Calendar\4. Payments\n 5. Show Members");
        switch (type){
            case "secretary":
                System.out.print("\n6. Club Calendar\n7. Berth Registrations");
                break;
            case "treasurer":
                System.out.print("\n6. Club Payments"); //change to better name later
                break;
        }

        MSTprompt(type);
    }
    private void MSTprompt(String type){

        System.out.print("\n?: ");
        int input = -1;
        try { input = scan.nextInt(); }
        catch (Exception e) {
            System.out.println("Wrong input (only numbers), press any key to proceed");
            scan.next();
            MSTmenu(type);
        }



        switch (input){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }

    }

    private void SeldinTrashMethod(){

    }
}
