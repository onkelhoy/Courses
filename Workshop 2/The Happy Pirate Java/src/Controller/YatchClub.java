package Controller;

import Helper.FileHandler;

import java.util.Scanner;

/**
 * Created by henry on 2016-09-25.
 */
public class YatchClub {

    private FileHandler memberDB, brsDB, calendarDB;

    public YatchClub(){

        Menu menu = new Menu(new Scanner(System.in));
    }
}
