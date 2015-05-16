package cz.inspire.clubspire_02.retrofitResources;

import android.app.Activity;

import java.util.List;

import cz.inspire.clubspire_02.retrofitResources.POJO.ActivityObject;

/**
 * Created by michal on 5/15/15.
 */
public class RestDataProvider {
    private static final String API_URL = "http://netspire.net:6868/api/";

    public static void main() {


        // Create a very simple REST adapter which points the GitHub API endpoint.
        ClubspireClient client = ServiceGenerator.createService(ClubspireClient.class, API_URL);

        // Fetch and print a list of the Activities to this library.
        List<ActivityObject> activities = client.activities("639b1d5cac1303f00011cd38b40e36c0");


        for (ActivityObject a : activities) {
            System.out.println(a.login + " (" + a.contributions + ")");
        }
    }
}
