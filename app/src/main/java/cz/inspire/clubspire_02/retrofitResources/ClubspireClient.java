package cz.inspire.clubspire_02.retrofitResources;

import android.app.Activity;

import java.util.List;

import cz.inspire.clubspire_02.*;
import cz.inspire.clubspire_02.retrofitResources.POJO.ActivityObject;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by michal on 5/15/15.
 */
public interface ClubspireClient {
    @GET("/activities/{id}")
    public List<ActivityObject> activities(
            @Path("id") String owner
    );


}
