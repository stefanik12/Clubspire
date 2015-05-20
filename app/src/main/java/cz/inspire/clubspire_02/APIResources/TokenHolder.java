package cz.inspire.clubspire_02.APIResources;

/**
 * Created by michal on 5/16/15.
 */
public class TokenHolder {
    private static AccessTokenObject token;

    public static AccessTokenObject getTokenObject() {

        //TODO: osetrit vracanie stareho = neplatneho tokenu
        return token;
    }

    public static void setTokenObject(AccessTokenObject token) {
        TokenHolder.token = token;
    }
}
