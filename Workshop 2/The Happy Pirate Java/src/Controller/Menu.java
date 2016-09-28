package Controller;

import Helper.FileHandler;
import Model.Member;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by henry on 2016-09-20.
 */
public class Menu {
    private Scanner scan = null;
    private YatchClub yatchclub;

    public Menu(YatchClub yatchclub, Scanner scan){
        this.scan = scan;
        this.yatchclub = yatchclub;
        SplashScreen();
        AuthenticateMenu();
    }


    private void AuthenticateMenu(){
        PreMenu();
        System.out.print("--- Authenticate Menu ---\n1). login.\n2). register\n0). Exit\n # ");
        String choise = scan.nextLine();
        switch (choise){
            case "1":
                LoginMenu();
                break;
            case "2":
                RegistrationMenu();
                break;
            case "0":
                System.out.println("Have a nice day sir, bye bye!!!");
                scan.close();
                break;
            default:
                System.out.println("Wrong input...\nTry again!!!");
                AuthenticateMenu();
        }

    }
    private void LoginMenu(){
        PreMenu();
        System.out.print("--- Login Menu ---\nUsername: ");
        String uName = scan.nextLine();
        System.out.print("Password: ");
        String pass = scan.nextLine();

        if(yatchclub.login(uName, pass)){
            MSTmenu();
        }

        else{
            PreMenu();
            System.out.println("failed logged in");
            AuthenticateMenu();
        }

    }

    private void RegistrationMenu(){
        PreMenu();

        System.out.println("--- Register Menu ---");
        System.out.print("Username: ");
        String userName = scan.nextLine();

        System.out.print("eMail: ");
        String eMail = scan.nextLine();

        System.out.print("Identity nr: ");
        String id = scan.nextLine();

        System.out.print("Password: ");
        String password = scan.nextLine();
        System.out.print("Password again: ");
        String password1 = scan.nextLine();

        if(password.equals(password1)){
            if(yatchclub.register(userName, password, eMail, id)){
                System.out.println("Successful registration");
                LoginMenu();
            }
            else {
                showError("\n\nThis user information is already in use");
                RegistrationMenu();
            }
        } else{
            System.out.println("Passwords don't match!!!\n try again!!!");
            RegistrationMenu();
        }

    }

    private void SplashScreen() {
        System.out.print("                                                  Welcome To:                                                  \n" +
                "     __________________________________________________________________________________________________________\n" +
                "       ______                   _     _                                   ____                                 \n" +
                "         /      /               /    /                                    /    )   ,                           \n" +
                "     ---/------/__----__-------/___ /-----__------__------__-------------/____/-------)__----__--_/_----__-----\n" +
                "       /      /   ) /___)     /    /    /   )   /   )   /   ) /   /     /        /   /   ) /   ) /    /___)    \n" +
                "     _/______/___/_(___ _____/____/____(___(___/___/___/___/_(___/_____/________/___/_____(___(_(_ __(___ _____\n" +
                "                                              /       /         /                                              \n" +
                "                                             /       /      (_ /                                               \n" +
                "               ____________________________________________________________________________________            \n" +
                "                   __                                                                                          \n" +
                "                   / |        /          ,         ,                              ,                            \n" +
                "               ---/__|----__-/---_--_--------__-------__--_/_---)__----__--_/_--------__----__-----            \n" +
                "                 /   |  /   /   / /  ) /   /   ) /   (_ ` /    /   ) /   ) /    /   /   ) /   )                \n" +
                "               _/____|_(___/___/_/__/_/___/___/_/___(__)_(_ __/_____(___(_(_ __/___(___/_/___/_____            \n" +
                "                                                                                                               \n" +
                "                                                                                                               \n" +
                "                                   _______________________________________                                     \n" +
                "                                                                                                               \n" +
                "                                                                                                               \n" +
                "                                   ---__---------__--_/_----__---_--_-----                                     \n" +
                "                                     (_ ` /   / (_ ` /    /___) / /  )                                         \n" +
                "                                   _(__)_(___/_(__)_(_ __(___ _/_/__/_____                                     \n" +
                "                                            /                                                                  \n" +
                "                                        (_ /                                                                   \n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
    }


    private void PreMenu(){
        System.out.println("\n\n\n----------------------------------------------------------------------------------------------------------------\n\n");
    }
    private void MSTmenu(){
        PreMenu();
        System.out.println(String.format("--- %s Menu ---", yatchclub.getMember().getType()));
        System.out.print("0. Logout\n1. User info\n2. Boats\n3. Calendar\n4. Payments\n5. Show Members");
        switch (yatchclub.getMember().getType()){
            case "secretary":
                System.out.print("\n6. Club Calendar\n7. Berth Registrations");
                break;
            case "treasurer":
                System.out.print("\n6. Club Payments"); //change to better name later
                break;
        }

        MSTprompt();
    }
    private void MSTprompt(){

        System.out.print("\n?: ");
        int input = -1;
        try { input = scan.nextInt(); }
        catch (Exception e) {
            showError("Wrong input (only numbers)");
            MSTmenu();
        }



        switch (input){
            case 0:
                yatchclub.setMember(); //logout
                AuthenticateMenu();
                break;
            case 1:
                // show user info menu
                break;
            case 2:
                // show boats menu
                break;
            case 3:
                // show calendar menu
                break;
            case 4:
                // show payments menu
                break;
        }

        if(yatchclub.getMember().getType().equals("secretary")){
            if(input == 5) {} // show members meny (more info)
            else if(input == 6) {} // show club calendar meny
            else if(input == 7) {} // show berth registrations meny
            else {
                showError("only values 0 - 7 are accepted");
                MSTmenu();
            }
        }
        else if(yatchclub.getMember().getType().equals("treasurer")) {
            if(input == 5) {} // show members meny (more info)
            else if(input == 6) {} // show club payments meny
            else {
                showError("only values 0 - 6 are accepted");
                MSTmenu();
            }
        }
        else {
            if(input == 5) {} // show members meny!
            else {
                showError("only values 0 - 5 are accepted");
                MSTmenu();
            }
        }
    }

    private void showError(String error){
        System.out.println(error);
        System.out.print("Press enter to continue.. ");
        try {
            System.in.read();
        } catch (IOException e) { }
    }
}
