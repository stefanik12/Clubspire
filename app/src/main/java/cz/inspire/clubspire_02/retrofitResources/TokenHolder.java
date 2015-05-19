package cz.inspire.clubspire_02.retrofitResources;

import cz.inspire.clubspire_02.retrofitResources.POJO.AccessToken;

/**
 * Created by michal on 5/16/15.
 */
public class TokenHolder {
    private static AccessToken token;

    public static AccessToken getTokenObject() {

        //TODO: osetrit vracanie stareho = neplatneho tokenu
        return token;
    }

    public static void setTokenObject(AccessToken token) {
        TokenHolder.token = token;
    }
}
