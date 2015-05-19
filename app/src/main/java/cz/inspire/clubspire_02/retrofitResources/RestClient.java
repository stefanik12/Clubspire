package cz.inspire.clubspire_02.retrofitResources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.inspire.clubspire_02.retrofitResources.Services.ApiService;
import cz.inspire.clubspire_02.retrofitResources.Services.HeaderInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by michal on 5/16/15.
 */
public class RestClient
{
    private static final String BASE_URL = "http://netspire.net:6868";
    private ApiService apiService;

    public RestClient()
    {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new HeaderInterceptor()) // This is the important line ;)
                .build();

        apiService = restAdapter.create(ApiService.class);
    }

    public ApiService getApiService()
    {
        return apiService;
    }
}
