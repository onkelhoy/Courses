package View;

import Controller.YatchClub;
import Helper.Pnr;
import Model.Berth;
import Model.Boat;
import Model.CalendarEvent;
import Model.Member;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
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
                ContactsMenu("Anonymous", false);
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

        System.out.print("Password: ");
        String password = scan.nextLine();
        System.out.print("Password again: ");
        String passwordRetype = scan.nextLine();

        Pnr pnr = new Pnr();
        if(!pnr.Valid(identity)) {
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
                            if(pnr.Valid(identity)) break;
                        }
                        else {
                            identity = pnr.GeneratePnr();
                            break;
                        }
                    }

                    break;
            }
        }
        if (password.equals(passwordRetype)) {
            switch (yatchclub.register(userName, password, eMail, identity)){
                case 1:
                    System.out.println("Successful registration");
                    LoginMenu();
                    break;
                case -1:
                    showMessage("\n\nThis user information is already in use");
                    AuthenticateMenu();
                    break;
            }
        } else {
            System.out.println("Passwords does not match!\nTry again!!!");
            RegistrationMenu();
        }
    }
    private void LoginMenu() {
        PreMenu();
        System.out.print("--- Login Menu ---\nUsername: ");
        String uName = scan.nextLine();
        System.out.print("Password: ");
        String pass = scan.nextLine();

        if (yatchclub.login(uName, pass)) {
            MSTmenu();
        } else {
            PreMenu();
            showMessage("That username and password is not valid");
            AuthenticateMenu();
        }

    }
    private void ContactsMenu(String Name, Boolean compact) {
        PreMenu();
        System.out.print(String.format("--- %s Menu ---\n1). List members\n0). Back\n # ", Name));
        String choise = scan.nextLine();
        switch (choise) {
            case "1":
                PrintMembers("username = '*'", false);
                System.out.print("See more details by typing USERNAME, blank+enter to quit.\nUSERNAME: ");
                String usn = scan.nextLine();
                if (yatchclub.anonymousUser(usn)){
                    MSTmenu();
                }else{
                    ContactsMenu("Anonymous", false);
                }
                showMessage(""); // works as a continue message as well :)
                break;
            case "0":
                break;
        }
    }
    private void MSTmenu() {
        PreMenu();
        String type = yatchclub.getMember().getType();
        type = type.substring(0, 1).toUpperCase() + type.substring(1);

        System.out.println(String.format("--- %s Menu ---", type));
        System.out.print("0. Exit\n1. User info\n2. Boats");
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

    private void MSTprompt() {

        System.out.print("\n?: ");

        String input = scan.nextLine();
        String type = yatchclub.getMember().getType();

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
            /*case "3":
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
                break;*/
            case "6":
                if (type.equals("secretary")) {
                    // show club calendar
                    ClubCalendarMenu();
                } else if (type.equals("treasurer")) {
                    // show club payments [payment history and such]
                } else showMessage("only values 0 - 5 are accepted");

                break;
            case "7":
                if (type.equals("secretary")) {
                    BerthRegisterMenu();
                }
                else {
                    if (type.equals("treasurer")) showMessage("only values 0 - 6 are accepted");
                    else showMessage("only values 0 - 5 are accepted");
                    MSTmenu();
                }
                break;
            default:
                if (type.equals("secretary")) showMessage("only values 0 - 7 are accepted");
                else if (type.equals("treasurer")) showMessage("only values 0 - 6 are accepted");
                else showMessage("only values 0 - 5 are accepted");

                MSTmenu();
        }
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
                    showMessage("Number : string - is only supported");
                    UserInfoMenu();
                    break;
                case 2: //usn
                    if(!m.setUsername(input, yatchclub)) showMessage("This username was already taken!");
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
                    showMessage("Only option 2-5 can have 2 values");
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
                    yatchclub.updateDB("member");
                    MSTmenu();
                    break;
                default:
                    showMessage("Only option 0-1 can have a number");
                    UserInfoMenu();
            }
        }
    }
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
                    showMessage("offset and count must be numbers!");
                    CalenderMenu();
                    break;
                }
            case "2":
                //show all
                NodeList ls = yatchclub.SearchDB("@memberid = " + yatchclub.getMember().getId() + " or @memberid = ''", "calendar"); //get all events associated with member, or all the club events
                if(c == -1 && s == -1) {c = ls.getLength(); s = 0;}
                if(s < ls.getLength()){
                    System.out.print("\n");
                    for (int i = 0; i < c; i++) {
                        CalendarEvent cal = new CalendarEvent((Element)ls.item(i+s));

                        if(cal.dead) yatchclub.updateDB("calendar");
                        else System.out.print(cal.toString());
                    }
                    showMessage("");
                }
                else showMessage((ls.getLength() != 0 ? "\noffset was too high" : "\nthere were no events"));
                // show calendar events from index: offset and

                CalenderMenu();
                break;
            default:
                showMessage("only numbers 0-2 are allowed");
                CalenderMenu();
        }
    }
    private void ClubCalendarMenu() {
        PreMenu();
        System.out.print("--- Club Calendar ---\n1. list\n2. add\n3. remove\n4. change\n0. Exit\n #: ");
        String ans = scan.nextLine();

        switch (ans){
            case "0":
                MSTmenu();
                break;
            case "1":
                NodeList list = yatchclub.SearchDB("@id = '*'", "calendar");
                for (int i = 0; i < list.getLength(); i++){
                    CalendarEvent event = new CalendarEvent((Element) list.item(i));
                    if(event.dead) yatchclub.updateDB("calendar");
                    else System.out.print(event.compactInfo());
                }
                showMessage("");
                ClubCalendarMenu();
                break;
            case "2":
                System.out.print("\nAdd an event\nName: ");
                String name = scan.nextLine();
                System.out.print("EndTime (dd mmmm yyyy): ");//this breaks the season time thats fakes a year into 30min.. but the princip is still the same
                String end = scan.nextLine();
                System.out.print("Event: ");
                String event = scan.nextLine();
                System.out.print("To specific Member (don't have to be set)\n\tmemberID: ");
                String memberid = scan.nextLine();

                end = getEndTime(end);

                if(!end.equals("0")) {
                    yatchclub.AddEvent(end, name, event, memberid);
                    showMessage("Event added to clubcalendar!");
                }
                ClubCalendarMenu();
                break;
            case "3":
                System.out.print("\nRemove event\nEvent id: ");
                String id = scan.nextLine();
                if(yatchclub.RemoveNode(String.format("//event[@id = '%s']", id), "calendar")) showMessage("Event removed");
                else showMessage("Cant find event");

                ClubCalendarMenu();
                break;
            case "4":
                System.out.print("\nChange event - the only field that has to be set is id!\nEvent id: ");
                id = scan.nextLine();
                System.out.print("Name: ");
                name = scan.nextLine();
                System.out.print("EndTime (Day Month Year): ");
                end = scan.nextLine();
                System.out.print("Event: ");
                event = scan.nextLine();
                System.out.print("MemberID: ");
                memberid = scan.nextLine();

                if(!end.equals("")) end = getEndTime(end);
                if(!end.equals("0")){
                    yatchclub.ChangeEvent(id, end, name, event, memberid);
                }
                ClubCalendarMenu();
                break;
            default:
                showMessage("only number 0-4 are valid input");
                ClubCalendarMenu();
        }
    }
    private void PaymentsMenu(){
        PreMenu();
        System.out.print(String.format("--- Payment Menu ---\nCurrent Fee: %s\nHas payed fee\n1. pay all\n2. history\n0. Exit\n # ",
                yatchclub.getMember().getFee(), yatchclub.getMember().hasPayedMembership()));

        String ans = scan.nextLine();
        switch (ans){
            case "0":
                MSTmenu();
                break;
            case "1":
                break;
            case "2":
                break;
            default:
                showMessage("only number 0-2 are valid");
                PaymentsMenu();
        }
    }
    private void BoatMenu(boolean listValue) {
        PreMenu();
        if (listValue) {
            // show boats
            Iterator<Boat> i = yatchclub.getMember().getBoats();
            while(i.hasNext()){
                Boat b = i.next();
                System.out.print(b.toString());
            }
        }
        // \n4). register might be edited in the future.
        System.out.print("--- Boat Menu ---\n1). List boats.\n2). Remove boats\n3). Add new boat\n4). Register\n5). Change\n0). Exit\n # ");
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
                // register boat to a berth, might be updated
                System.out.print("\n(0 to quit)\nBoat ID: ");
                boatID = scan.nextLine();
                if (boatID.equals("0")) BoatMenu(false);
                else {
                    int u = yatchclub.updateBoat(boatID, "", "", "", "1");
                    showMessage((u == 0) ? "Boat successfully registered" : (u == -1 ? "boat was not found" : "unknown error"));
                    BoatMenu(false);
                }
                break;

            case "5": // edit a specific boat functionality, to keep in mind
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
                        int u = yatchclub.updateBoat(boatID, name, type, length, "");
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
    private void BerthRegisterMenu(){
        if(!yatchclub.isSeason()) {
            PreMenu();
            System.out.print("--- Berth Registrations Menu ---\n1. list\n2. assign\n3. declain\n0. Exit\n # ");
            String ans = scan.nextLine();

            switch (ans) {
                case "0":
                    MSTmenu();
                    break;
                case "1":
                    NodeList list = yatchclub.SearchDB("@fee >= 0", "berth"); //select all berths
                    for(int i = 0; i < list.getLength(); i++){
                        System.out.println(new Berth((Element)list.item(i)).toString());
                    }
                    showMessage("");
                    BerthRegisterMenu();
                    break;
                case "2":
                    System.out.print("\nDecline berth\nBoatID: ");
                    String boatID = scan.nextLine();

                    int feedback = yatchclub.assignBerth(boatID);
                    switch (feedback){
                        case 1:
                            showMessage("Boat successfully assigned of berth");
                            break;
                        case 0:
                            showMessage("Could not find boat");
                            break;
                        case -1:
                            showMessage("No more space for boats in " + yatchclub.getName());
                            break;
                    }
                    BerthRegisterMenu();
                    break;
                case "3":
                    System.out.print("\nDecline berth\nBoatID: ");
                    boatID = scan.nextLine();

                    if(yatchclub.declineBerth(boatID)) showMessage("Boat successfully declined of berth");
                    else showMessage("Could not find boat");
                    BerthRegisterMenu();
                    break;
                default:
                    showMessage("only number 0-3 is valid");
                    BerthRegisterMenu();
            }
        }
        else {
            showMessage("Its still season!");
            MSTmenu();
        }
    }

    //************** Help methods ***************************************************************************************//

    private void showMessage(String error) {
        System.out.println(error);
        System.out.print("Press enter to continue.. ");
        try {
            System.in.read();
        } catch (IOException e) {
        }
    }
    private void SearchField(boolean compact){
        System.out.print("\n--- Search ----\n Fields that can be searched on: \n\tboats, boatlength, boattype \n\tname, username, id, identity\n\tage, month, email, gender\n\n Search: ");
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
    private String getEndTime(String end){
        Date date = null;
        try {
            DateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
            date = format.parse(end);
            return date.toString();
        } catch (Exception e) {
            System.out.print("\nOBS endTime is not valid!\nplease use this format (dd mmmm yyyy)\nendTime (0 to quit): ");
            end = scan.nextLine();
            if(end.equals("0")){
                return "0";
            }
            else return getEndTime(end);
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
