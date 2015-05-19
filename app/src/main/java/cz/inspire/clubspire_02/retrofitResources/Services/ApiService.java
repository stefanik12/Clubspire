package cz.inspire.clubspire_02.retrofitResources.Services;

import java.util.List;


import cz.inspire.clubspire_02.retrofitResources.POJO.AccessToken;
import cz.inspire.clubspire_02.retrofitResources.POJO.ActivityObject;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by michal on 5/15/15.
 */
public interface ApiService {
    public static final String BASE_URL = "http://netspire.net:6868";

    @GET("/api/activities")
    public void getActivities(Callback<List<ActivityObject>> callback);

    @POST("/token")
    AccessToken getAccessToken(@Query("code") String code,
                               @Query("grant_type") String grantType);
}
