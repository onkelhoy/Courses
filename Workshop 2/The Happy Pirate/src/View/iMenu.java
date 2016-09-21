package View;


import Model.Boat;
import Users.Member;

/**
 * Created by henry on 2016-09-20.
 */
public interface iMenu {

    boolean AuthenticateField(); // Login fields
    String[] LoginField();
    void RegistrationField();

    void MemberMenu();
    void SecretaryMenu();
    void TreasurerMenu();

    void BoatMenu();

}
