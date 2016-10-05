package View;

import Controller.YatchClub;
import Helper.Pnr;
import Model.Boat;
import Model.CalendarEvent;
import Model.Member;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    private Scanner scan = null;
    private YatchClub yatchclub;

    public Menu(YatchClub yatchclub, Scanner scan) {
        this.scan = scan;
        this.yatchclub = yatchclub;
        SplashScreen();
        StartMenu();
    }

    //************** Menu Fields ****************************************************************************************//

    // Authenticate is grade 3, case 1 needs to go
    private void StartMenu() {
        PreMenu();
        System.out.print("--- Start Menu ---\n1). Register\n2). Anonymous\n0). Exit\n # ");
        String choise = scan.nextLine();
        switch (choise) {
            case "1":
                RegistrationMenu();
                break;
            case "2":
                ContactsMenu("Anonymous", false);
                StartMenu();
                break;
            case "0":
                System.out.println("Thank you for visiting The Happy Pirate yacht club.");
                scan.close();
                break;
            default:
                System.out.println("That command in not valid.\nTry again!");
                StartMenu();
        }
    }
    private void RegistrationMenu() {
        PreMenu();

        System.out.println("--- Register Menu ---");
        System.out.print("Name: ");
        String name = scan.nextLine();

        System.out.print("Username: ");
        String userName = scan.nextLine();

        System.out.print("Email: ");
        String eMail = scan.nextLine();

        System.out.print("Identity nr: ");
        String id = scan.nextLine();

        System.out.print("Password: ");
        String password = scan.nextLine();
        System.out.print("Password again: ");
        String passwordRetype = scan.nextLine();

        Pnr pnr = new Pnr();
        if(!pnr.Valid(id)) {
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
                            id = scan.nextLine();
                            if(pnr.Valid(id)) break;
                        }
                        else {
                            id = pnr.GeneratePnr();
                            break;
                        }
                    }

                    break;
            }
        }
        if (password.equals(passwordRetype)) {
            switch (yatchclub.register(name, userName, password, eMail, id)){
                case 1:
                    System.out.println("Successful registration");
                    StartMenu();
                    break;
                case -1:
                    showError("\n\nThis user information is already in use");
                    StartMenu();
                    break;
            }
        } else {
            System.out.println("Passwords does not match!\nTry again!!!");
            RegistrationMenu();
        }
    }

    private void ContactsMenu(String Name, Boolean compact) {
        PreMenu();
        System.out.print(String.format("--- %s Menu ---\n1). List members(Compact)\n2). List members(Verbose) \n0). Back\n # ", Name));
        String choise = scan.nextLine();
        switch (choise) {
            case "1":
                // add list boats
                PrintMembers("username = '*'", false);
                // Need to come to by typing username MSTmenu();
                System.out.print("\nUser info by username: ");
                String userName = scan.next();
                if( yatchclub.userInfo(userName)){
                    MSTmenu();
                }else{
                    PreMenu();
                    showError("That username is not valid");
                    ContactsMenu(Name, compact);
                }
                break;
            case "2":
                //put verbose list here
                ContactsMenu(Name, compact);
                break;
            case "0":
                break;
        }
    }

    // As we dont need login some parts nedd editing
    private void MSTmenu() {
        PreMenu();
        String type = yatchclub.getMember().getType();
        type = type.substring(0, 1).toUpperCase() + type.substring(1);

        System.out.println(String.format("--- %s Menu ---", type));
        System.out.print("0. Logout\n1. User info\n2. Boats\n3. Calendar\n4. Payments\n5. Contacts");
        switch (yatchclub.getMember().getType()) {
            case "secretary":
                System.out.print("\n6. Club Calendar\n7. Berth Registrations");
                break;
            case "treasurer":
                System.out.print("\n6. Club Payments"); //change to better name later
                break;
        }

        MSTprompt();
    }

    private void UserInfoMenu() {
        PreMenu();
        Member m = yatchclub.getMember();
        System.out.print(String.format("--- User info menu ---\n0. Go back\n1: Save\n\nChangeable <number:change>\n  2. Username: %s\n  3. Email: %s\n  4. Name: %s\n  5. Change Password\n\nIdentity: %s\nMembership fee: %s\n\n?: ", m.getUsername(), m.getEmail(), m.getName(), m.getIdentity(), (m.hasPayedMembership() ? "has payed" : "has not payed")));

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
        } else {
            try {
                number = Integer.parseInt(input);
            } catch (Exception e) {}

            switch (number) {
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

    // This is not mentioned in grade2
    private void CalenderMenu(){
        PreMenu();
        System.out.print("--- Calendar Menu ---\n0. go back\n1. list\n2. show all\n?: ");
        String ans = scan.nextLine();
        int s = -1, c = -1;

        switch (ans){
            case "0":
                MSTmenu();
                break;
            case "1":
                System.out.print("\noffset: ");
                String start = scan.nextLine();
                System.out.print("count: ");
                String count = scan.nextLine();

                try {
                    s = Integer.parseInt(start);
                    c = Integer.parseInt(count);
                }
                catch (Exception e){ }

                if(c == -1 || s == -1){
                    showError("offset and count must be numbers!");
                    CalenderMenu();
                    break;
                }
            case "2":
                //show all
                NodeList ls = yatchclub.SearchDB("@id = " + yatchclub.getMember().getId(), "calendar");
                if(c == -1 && s == -1) {c = ls.getLength(); s = 0;}
                if(s < ls.getLength()){
                    for (int i = 0; i < c; i++) {
                        CalendarEvent cal = new CalendarEvent((Element)ls.item(i+s));

                        if(cal.dead) yatchclub.RemoveEvent(cal);
                        else System.out.print(cal.toString());
                    }
                    showError("");
                }
                else showError((ls.getLength() != 0 ? "\noffset was too high" : "\nthere were no events"));
                // show calendar events from index: offset and

                CalenderMenu();
                break;
            default:
                showError("only numbers 0-2 are allowed");
                CalenderMenu();
        }
    }

    // this is not mentioned in grad2
    private void ClubCalendarMenu() {
        PreMenu();
        System.out.print("--- Club Calendar ---\n1. list\n2. add\n3. remove\n4. change\n?: ");
        String ans = scan.nextLine();

        switch (ans){
            case "0":
                MSTmenu();
                break;
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                System.out.print("id: ");
                String id = scan.nextLine();


                break;
            default:
                showError("only number 0-4 are valid input");
                ClubCalendarMenu();
        }
    }

    // not mentioned in grade2
    private void PaymentsMenu(){

    }

    // Register might need to be omitted for grad2
    private void BoatMenu(boolean listValue) {
        PreMenu();
        if (listValue) {
            // show boats
            for(Boat b : yatchclub.getMember().getBoats()){
                System.out.print(b.toString());
            }
        }
        // \n4). register might be edited in the future.
        System.out.print("--- Boat Menu ---\n1). List boats.\n2). Remove boats\n3). Add new boat\n4). Register\n0). Exit\n # ");
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
                    if(yatchclub.RemoveNode(String.format("//boat[@id = %s]", boatID), "member")){
                        showError("Boat was successfully removed"); //success
                    }
                    else showError("Some error occurred and boat could not be removed!");

                    BoatMenu(false);
                }
                break;
            case "3":
                // add new boat
                AddNewBoat();
                break;
            case "4":
                // register boat to a berth, might be updated
                break;

            // case 5: edit a specific boat functionality, to keep in mind

            case "0":
                MSTmenu();
                break;
            default:
                showError("Only number 0-4 are valid");
                BoatMenu(false);
        }
    }
    private void AddNewBoat() {
        System.out.print("--- Add new boat ---");

        //Boat name is not a property
        System.out.print("\nBoat Name: ");
        String bname = scan.nextLine();
        System.out.print(("\nBoat Type: "));
        String btype = scan.nextLine();
        System.out.print("Boat Length: ");
        String blength = scan.nextLine();


        switch (yatchclub.saveBoat(bname, btype, blength)){
            case 0:
                showError("Boat was saved");
                break;
            case -1:
                showError("Boat could not be added due to some unknown reason");
                break;
            case -2:
                showError(String.format("Boat length was not a valid length [0-%s]", yatchclub.getMaxlength()));
                break;
        }
        // save to xmlDB
        System.out.print("\nBoat has been saved");
        BoatMenu(false);
    }

    //************** Help methods ***************************************************************************************//

    private void showError(String error) {
        System.out.println(error);
        System.out.print("Press enter to continue.. ");
        try {
            System.in.read();
        } catch (IOException e) {
        }
    }

    // this is not mentioned in grade2
    private void SearchField(boolean compact){
        System.out.print("\n--- Search ----\n Fields that can be searched on: \n\tboats, boatlength, boattype \n\tname, username, id, identity\n\tage, month, email, gender\n" +
                "An example, Search: username = 'ada*'" +
                "\n\nSearch: ");
        String query = scan.nextLine();

        PrintMembers(query, compact);

        System.out.print("\nDo another search (y/n): ");
        String ans = scan.nextLine();
        switch (ans.toLowerCase()){
            case "y":
            case "yes":
                SearchField(compact);
        }
        //no and then continue to the other method thats called after this!
    }
    private void PrintMembers(String query, boolean compact){
        NodeList nl = yatchclub.SearchDB(query, "member");
        if(nl == null) System.out.print("not a valid search query!");
        else if(nl.getLength() == 0) System.out.print("No match");
        else {
            for (int i = 0; i < nl.getLength(); i++) {
                Member m = new Member((Element) nl.item(i));
                System.out.print("\n" + (compact ? m.compactInfo() : m.verboseInfo()));
            }
        }
    }

    private void PintBoats(){
        // list boatsfor compact list
    }

    //************ Prompt methods ***************************************************************************************//

    private void MSTprompt() {

        System.out.print("\n?: ");

        String input = scan.nextLine();
        String type = yatchclub.getMember().getType();

        switch (input) {
            case "0":
                yatchclub.setMember(true); //logout
                StartMenu();
                break;
            case "1":
                // show user info menu
                UserInfoMenu();
                break;
            case "2":
                // show boats menu
                BoatMenu(false);
                break;
            case "3":
                // show calendar menu
                CalenderMenu();
                break;
            case "4":
                // show payments menu
                PaymentsMenu();
                break;
            case "5":
                if (type.equals("secretary") || type.equals("treasurer")) {
                    ContactsMenu("Contacts", true);
                    MSTmenu();
                } //show contacts meny (more info)
                else {
                    ContactsMenu("Contacts", false);
                    MSTmenu();
                } //show contacts meny
                break;
            case "6":
                if (type.equals("secretary")) {
                    // show club calendar
                    ClubCalendarMenu();
                } else if (type.equals("treasurer")) {
                    // show club payments [payment history and such]
                } else showError("only values 0 - 5 are accepted");

                break;
            case "7":
                if (type.equals("secretary")) {
                } else if (type.equals("treasurer")) showError("only values 0 - 6 are accepted");
                else showError("only values 0 - 5 are accepted");

                break;
            default:
                if (type.equals("secretary")) showError("only values 0 - 7 are accepted");
                else if (type.equals("treasurer")) showError("only values 0 - 6 are accepted");
                else showError("only values 0 - 5 are accepted");

                MSTmenu();
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
    private void PreMenu() {
        System.out.println("\n\n\n===============================================================================================================\n===============================================================================================================\n");
    }

}
