package cz.inspire.clubspire_02.retrofitResources;

import cz.inspire.clubspire_02.retrofitResources.POJO.AccessToken;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by michal on 5/15/15.
 */
public interface LoginService {
    public static final String BASE_URL = "http://netspire.net:6868/";

    @POST("/login")
    String basicLogin();

    @POST("/token")
    AccessToken getAccessToken(@Query("code") String code,
                               @Query("grant_type") String grantType);
}
