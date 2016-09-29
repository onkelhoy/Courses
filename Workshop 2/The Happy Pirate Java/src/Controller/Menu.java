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
        System.out.print("--- Authenticate Menu ---\n1). Login\n2). Register\n0). Exit\n # ");
        String choise = scan.next();
        switch (choise){
            case "1":
                LoginMenu();
                break;
            case "2":
                RegistrationMenu();
                break;
            case "0":
                System.out.println("Thank you for visiting The Happy Pirate yacht club.");
                scan.close();
                break;
            default:
                System.out.println("That command in not valid.\nTry again!");
                AuthenticateMenu();
        }

    }
    private void LoginMenu(){
        PreMenu();
        System.out.print("--- Login Menu ---\nUsername: ");
        String uName = scan.next();
        System.out.print("Password: ");
        String pass = scan.next();

        if(yatchclub.login(uName, pass)){
            MSTmenu();
        }

        else{
            PreMenu();
            System.out.println("That username and password is not valid\nTry again!");
            AuthenticateMenu();
        }

    }

    private void RegistrationMenu(){
        PreMenu();

        System.out.println("--- Register Menu ---");
        System.out.print("Username: ");
        String userName = scan.next();

        System.out.print("Email: ");
        String eMail = scan.next();

        System.out.print("Identity nr: ");
        String id = scan.next();

        System.out.print("Password: ");
        String password = scan.next();
        System.out.print("Password again: ");
        String password1 = scan.next();

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
            System.out.println("Passwords does not match!\nTry again!!!");
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
        System.out.println("\n\n\n===============================================================================================================\n===============================================================================================================\n");
    }
    private void MSTmenu(){
        PreMenu();
        String type = yatchclub.getMember().getType();
        type = type.substring(0, 1).toUpperCase() + type.substring(1);

        System.out.println(String.format("--- %s Menu ---", type));
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

    private int getInput(){
        int input = -1;
        try { input = scan.nextInt(); }
        catch (Exception e) {
            showError("Wrong input (only numbers)");
        }
        return input;
    }


    private void MSTprompt(){

        System.out.print("\n?: ");

        int input = getInput();
        String type = yatchclub.getMember().getType();

        switch (input){
            case -1:
                MSTmenu();
                break;
            case 0:
                yatchclub.setMember(true); //logout
                AuthenticateMenu();
                break;
            case 1:
                // show user info menu
                UserInfoMenu();
                break;
            case 2:
                // show boats menu
                BoatMenu(false);
                break;
            case 3:
                // show calendar menu
                break;
            case 4:
                // show payments menu
                break;
            case 5:
                if(type.equals("secretary") || type.equals("treasurer")) {} //show members meny (more info)
                else {} //show members meny
                break;
            case 6:
                if(type.equals("secretary")) {}
                else if(type.equals("treasurer")) {}
                else showError("only values 0 - 5 are accepted");

                break;
            case 7:
                if(type.equals("secretary")) {}
                else if(type.equals("treasurer")) showError("only values 0 - 6 are accepted");
                else showError("only values 0 - 5 are accepted");

                break;
            default:
                if(type.equals("secretary")) showError("only values 0 - 7 are accepted");
                else if(type.equals("treasurer")) showError("only values 0 - 6 are accepted");
                else showError("only values 0 - 5 are accepted");
        }
    }

    private void UserInfoMenu(){
        PreMenu();
        Member m = yatchclub.getMember();
        System.out.print(String.format("--- User info menu ---\n0. Go back\n1: Save\n\nChangeable <number:change>\n  2. Username: %s\n  3. Email: %s\n  4. Name: %s\n  5. Change Password\n\nIdentity: %s\nMembership fee: %s\n\n?: ", m.getUsername(), m.getEmail(), m.getName(), m.getIdentity(), (m.hasPayedMembership() ? "has payed" : "has not payed")));

        String input = scan.next();
        int number = -1;
        if(input.length() > 1){
            String[] arr = input.split(":");
            try{
                number = Integer.parseInt(arr[0]);
            }
            catch (Exception e){ }
            input = arr[1];

            switch (number){
                case -1://error
                    showError("Number : string - is only supported");
                    UserInfoMenu();
                    break;
                case 2: //usn
                    m.setUsername(input);
                    UserInfoMenu();
                    break;
                case 3: //email
                    m.setEmail(input);
                    UserInfoMenu();
                    break;
                case 4: //name
                    m.setName(input);
                    UserInfoMenu();
                    break;
                case 5: //password
                    m.setPassword(input);
                    UserInfoMenu();
                    break;
                default:
                    showError("Only option 2-5 can have 2 values");
                    UserInfoMenu();
            }
        }
        else {
            try{
                number = Integer.parseInt(input);
            }
            catch (Exception e){ }

            switch (number){
                case -1://error
                    showError("only numbers!");
                    UserInfoMenu();
                    break;
                case 0: //go back
                    MSTmenu();
                    break;
                case 1: //save
                    yatchclub.setMember(false);
                    MSTmenu();
                    break;
                default:
                    showError("Only option 0-1 can have a number");
                    UserInfoMenu();
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

    private void BoatMenu(boolean listValue){
        PreMenu();
        if (listValue){
            // show boats
        }
        // \n4). register might be edited in the future.
        System.out.print("--- Boat Menu ---\n1). list boats.\n2). remove boats\n3). add new boat\n4). register\n0). Exit\n # ");
        int input = getInput();


        switch (input){
            case -1:
                BoatMenu(false);
            case 1:
                BoatMenu(true);
                break;
            case 2:
                // remove boats
                System.out.print("\n(0 to quit)\nBoat ID: ");
                String boatID = scan.next();
                if(boatID.equals("0")) BoatMenu(listValue);
                else {
                    // remove boat based on boatID.

                }
                break;
            case 3:
                // add new boat
                break;
            case 4:
                // register boat to a berth, might be updated
                break;
            case 0:
                MSTmenu();
                break;
        }
    }
}
