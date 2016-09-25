package View;

import java.io.Console;

/**
 * Created by henry on 2016-09-20.
 */
public class EngMenu extends View.Console implements iMenu {


    public EngMenu() {
        super();
    }

    @Override
    public boolean AuthenticateField() {
        return GetAns("1: Login\n2: Register\nAns: ").equals("1");
    }

    @Override
    public String[] LoginField() {
        String username = GetAns("Username: ");
        String password = GetAns("Password: ");

        String[] arr = {username, password};
        return arr;
    }

    @Override
    public void RegistrationField() {

    }

    @Override
    public void MemberMenu() {

    }

    @Override
    public void SecretaryMenu() {

    }

    @Override
    public void TreasurerMenu() {

    }

    @Override
    public void BoatMenu() {

    }

}
