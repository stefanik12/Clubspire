package cz.inspire.clubspire_02.APIResources;

/**
 * Created by michal on 5/16/15.
 */
public class AuthenticationHolder {
    private static AccessTokenObject token;
    private static String username;
    private static String password;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        AuthenticationHolder.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        AuthenticationHolder.password = password;
    }

    public static AccessTokenObject getTokenObject() {
        return token;
    }

    public static void setTokenObject(AccessTokenObject token) {
        AuthenticationHolder.token = token;
    }

    public static void clear(){
        token = null;
        username = null;
        password = null;
    }
}
