package Helper;

/**
 * Created by delorian1986 on 9/28/16.
 */
public interface iHashAndAuth {

    String hash(char[] password);

    @Deprecated
    String hash(String password);

    boolean authenticate(char[] password, String token);

    @Deprecated
    boolean authenticate(String password, String token);

}
