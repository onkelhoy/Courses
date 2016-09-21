package View;

import Helper.FileHandler;
import Users.Member;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by henry on 2016-09-20.
 */
public class Menu {
    private iMenu menu;
    private FileHandler memberDB;

    public Menu(iMenu menu){
        this.menu = menu;
        AuthenticateMenu();
    }


    private void AuthenticateMenu(){
        if (menu.AuthenticateField()) LoginMenu();
        else RegistrationMenu();
    }
    private void LoginMenu(){
        String[] data = menu.LoginField();
        String query = "";
    }
    private void RegistrationMenu(){

    }
}
