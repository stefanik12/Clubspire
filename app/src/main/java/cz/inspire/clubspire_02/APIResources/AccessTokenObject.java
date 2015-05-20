package cz.inspire.clubspire_02.APIResources;

/**
 * Created by michal on 5/15/15.
 */
public class AccessTokenObject {

    private String accessToken;
    private String tokenType;
    private long expires_in;
    private String scope;

    public AccessTokenObject setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public AccessTokenObject setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public AccessTokenObject setExpires_in(long expires_in) {
        this.expires_in = expires_in;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        // OAuth requires uppercase Authorization HTTP header value for token type
        if ( ! Character.isUpperCase(tokenType.charAt(0))) {
            tokenType = Character.toString(tokenType.charAt(0)).toUpperCase() + tokenType.substring(1);
        }

        return tokenType;
    }

    public AccessTokenObject setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }
}
