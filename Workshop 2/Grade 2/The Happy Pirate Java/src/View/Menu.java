package View;

import Controller.YatchClub;
import Helper.PersonNumberCheck;
import Model.Boat;
import Model.Member;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

public class Menu {
    private Scanner scan = null;
    private YatchClub yatchclub;

    public Menu(YatchClub yatchclub, Scanner scan) {
        this.scan = scan;
        this.yatchclub = yatchclub;
        SplashScreen();
        AuthenticateMenu();
    }

    //************** Menu Fields ****************************************************************************************//

    private void AuthenticateMenu() {
        PreMenu();
        System.out.print("--- Authenticate Menu ---\n1). Register\n2). Anonymous\n0). Exit\n # ");
        String choise = scan.nextLine();
        switch (choise) {
            case "1":
                RegistrationMenu();
                break;
            case "2":
                AnonymousMenu();
                AuthenticateMenu();
                break;
            case "0":
                System.out.println("Thank you for visiting The Happy Pirate yacht club.");
                scan.close();
                break;
            default:
                System.out.println("That command in not valid.\nTry again!");
                AuthenticateMenu();
        }
    } //start menu
    private void RegistrationMenu() {
        PreMenu();

        System.out.println("--- Register Menu ---");
        System.out.print("Username: ");
        String userName = scan.nextLine();

        System.out.print("Email: ");
        String eMail = scan.nextLine();

        System.out.print("Identity nr: ");
        String identity = scan.nextLine();

        PersonNumberCheck personNumberCheck = new PersonNumberCheck();
        if(!personNumberCheck.Valid(identity)) {
            System.out.print("\nYour identity was wrong! - YYYYMMDDXXXC\nWould you like to correct it? (y/n): ");
            String ans = scan.nextLine();
            switch (ans.toLowerCase()){
                case "y":
                case "yes":
                    while(true) {
                        System.out.print("Would you like to generete or write your own?\nI want to generete (y/n): ");
                        ans = scan.nextLine().toLowerCase();
                        if (ans.equals("n") || ans.equals("no")) {
                            System.out.print("identity: ");
                            identity = scan.nextLine();
                            if(personNumberCheck.Valid(identity)) break;
                        }
                        else {
                            identity = personNumberCheck.GeneratePnr();
                            break;
                        }
                    }

                    break;
            }
        }
        yatchclub.register(userName, eMail, identity);
        AuthenticateMenu();
    }

    private void AnonymousMenu() {
        PreMenu();
        System.out.print("--- Anonymous Menu ---\n1). List members (Compact)\n2). List members (Verbose)\n0). Back\n # ");
        String choise = scan.nextLine();
        switch (choise) {
            case "1":
                PrintMembers(false);
                System.out.print("See more details by typing USERNAME, blank+enter to quit.\nUSERNAME: ");
                String usn = scan.nextLine();
                if (yatchclub.anonymousUser(usn)){
                    MSTmenu();
                }else{
                    AnonymousMenu();
                }
                showMessage(""); // works as a continue message as well :)
                break;
            case "2":
                PrintMembers(true);
                System.out.print("See more details by typing USERNAME, blank+enter to quit.\nUSERNAME: ");
                usn = scan.nextLine();
                if (yatchclub.anonymousUser(usn)){
                    MSTmenu();
                }else{
                    AnonymousMenu();
                }
                break;
            case "0":
                break;
            default:
                showMessage("only number 0-2 are valid");
                AnonymousMenu();
        }
    }
    private void MSTmenu() {
        PreMenu();
        System.out.println("--- Member Menu ---");
        System.out.print("0. Exit\n1. User info\n2. Boats");
        MSTprompt();
    }

    private void MSTprompt() {

        System.out.print("\n?: ");

        String input = scan.nextLine();

        switch (input) {
            case "0":
                yatchclub.setMember(); //logout
                AuthenticateMenu();
                break;
            case "1":
                // show user info menu
                UserInfoMenu();
                break;
            case "2":
                // show boats menu
                BoatMenu(false);
                break;
            default:
                showMessage("only values 0 - 2 are accepted");
                MSTmenu();
        }
    }
    private void UserInfoMenu() {
        PreMenu();
        Member m = yatchclub.getMember();
        System.out.print(String.format("--- User info menu ---\n0. Go back\n1. Save\n2. Delete Member\n\nChangeable <number:change>\n  3. Username: %s\n  4. Email: %s\n  5. Name: %s\n\nIdentity: %s\n\n?: ", m.getUsername(), m.getEmail(), m.getName(), m.getIdentity()));

        String input = scan.nextLine();
        int number = -1;
        if (input.length() > 1) {
            String[] arr = input.split(":");
            try {
                number = Integer.parseInt(arr[0]);
            } catch (Exception e) {
            }
            input = arr[1];

            switch (number) {
                case -1://error
                    showMessage("Number : string - is only supported");
                    UserInfoMenu();
                    break;
                case 3: //usn
                    if(!m.setUsername(input, yatchclub)) showMessage("This username was already taken!");
                    UserInfoMenu();
                    break;
                case 4: //email
                    m.setEmail(input);
                    UserInfoMenu();
                    break;
                case 5: //name
                    m.setName(input);
                    UserInfoMenu();
                    break;
                default:
                    showMessage("Only option 3-5 can have 2 values");
                    UserInfoMenu();
            }
        } else {
            try {
                number = Integer.parseInt(input);
            } catch (Exception e) {}

            switch (number) {
                case -1://error
                    showMessage("only numbers!");
                    UserInfoMenu();
                    break;
                case 0: //go back
                    MSTmenu();
                    break;
                case 1: //save
                    yatchclub.updateDB();
                    MSTmenu();
                    break;
                case 2: // remove member
                    yatchclub.getMember().Remove();
                    yatchclub.updateDB();
                    showMessage("Member was successfully removed");
                    AuthenticateMenu();
                    break;
                default:
                    showMessage("Only option 0-1 can have a number");
                    UserInfoMenu();
            }
        }
    }
    private void BoatMenu(boolean listValue) {
        PreMenu();
        if (listValue) {
            // show boats
            printBoats(yatchclub.getMember());
        }
        // \n4). register might be edited in the future.
        System.out.print("--- Boat Menu ---\n1). List boats.\n2). Remove boats\n3). Add new boat\n4). Change\n0). Exit\n # ");
        String input = scan.nextLine();


        switch (input) {
            case "1":
                BoatMenu(true);
                break;
            case "2":
                // remove boats
                System.out.print("\n(0 to quit)\nBoat ID: ");
                String boatID = scan.nextLine();
                if (boatID.equals("0")) BoatMenu(false);
                else {
                    // remove boat based on boatID.
                    if(yatchclub.removeBoat(boatID)) {
                        showMessage("Boat was successfully removed"); //success
                    }
                    else showMessage("Boat not found!");

                    BoatMenu(false);
                }
                break;
            case "3":
                // add new boat
                AddNewBoat();
                break;
            case "4":
                System.out.print("\nEdit boat (dont have to set all fields)\nBoat ID: ");
                boatID = scan.nextLine();
                System.out.print("Name: ");
                String name = scan.nextLine();
                System.out.print("Type: ");
                String type = scan.nextLine();
                System.out.print("Length: ");
                String length = scan.nextLine();

                System.out.print("\nconfirm (y/n): ");
                String ans = scan.nextLine();
                switch (ans.toLowerCase()){
                    case "y":
                    case "yes":
                        int u = yatchclub.updateBoat(boatID, name, type, length);
                        showMessage((u == 0) ? "Boat successfully updated" : (u == -1 ? "boat was not found" : "boat length was not valid or too long"));
                        break;
                    default:
                        BoatMenu(false); //no
                }
                BoatMenu(false); //no
                break;

            case "0":
                MSTmenu();
                break;
            default:
                showMessage("Only number 0-4 are valid");
                BoatMenu(false);
        }
    }
    private void AddNewBoat() {
        System.out.print("--- Add new boat ---");

        //Boat name is not a property
        System.out.print("\nBoat Name: ");
        String bname = scan.nextLine();
        System.out.print(("Boat Type: "));
        String btype = scan.nextLine();
        System.out.print("Boat Length: ");
        String blength = scan.nextLine();


        switch (yatchclub.saveBoat(bname, btype, blength)){
            case 0:
                showMessage("Boat was saved");
                break;
            case -1:
                showMessage("Boat could not be added due to some unknown reason");
                break;
            case -2:
                showMessage(String.format("Boat length was not a valid length [0-%s]", yatchclub.getMaxlength()));
                break;
        }
        // save to xmlDB
        System.out.print("\nBoat has been saved");
        BoatMenu(false);
    }

    //************** Help methods ***************************************************************************************//

    private void printBoats(Member m){

        Iterator<Boat> i = m.getBoats();
        while(i.hasNext()){
            Boat b = i.next();
            System.out.print(String.format("Boat - Id: %s, Name: %s, Type: %s, Length: %s, Fee: %s\n", b.getId(), b.getName(), b.getTypeValue(), b.getLength(), b.getFee()));
        }
    }
    private void showMessage(String error) {
        System.out.println(error);
        System.out.print("Press enter to continue.. ");
        try {
            System.in.read();
        } catch (IOException e) {
        }
    }
    private void PrintMembers(boolean verbose){
        NodeList nl = yatchclub.SearchDB("//member");
        if(nl == null) System.out.print("not a valid search query!");
        else if(nl.getLength() == 0) System.out.print("No match");
        else {
            for (int i = 0; i < nl.getLength(); i++) {
                Member m = new Member((Element) nl.item(i));

                System.out.print(String.format("USERNAME: %s\tEMAIL: %s\tNAME: %s\nID: %s\tIDENTITY: %s\n", m.getUsername(), m.getEmail(), m.getName(), m.getId(), m.getIdentity()));
                if(verbose) {
                    printBoats(m);
                }
                else {
                    System.out.print("\tBoats: " + m.getBoatSize() +"\n");
                }
            }
        }
    }

    //************ Prompt methods ***************************************************************************************//

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
    private void PreMenu() {
        System.out.println("\n\n\n===============================================================================================================\n===============================================================================================================\n");
    }

}
