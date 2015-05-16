package cz.inspire.clubspire_02.retrofitResources;

import android.util.Base64;

import com.squareup.okhttp.OkHttpClient;

import cz.inspire.clubspire_02.retrofitResources.POJO.AccessToken;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by michal on 5/15/15.
 */
public class ServiceGenerator {

    private static RestAdapter.Builder builder = new RestAdapter.Builder().setClient(new OkClient(new OkHttpClient()));

    private static final String USERNAME = "fimuni";
    private static final String PASSWORD = "123456";

    // No need to instantiate this class.
    private ServiceGenerator() {
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        // call basic auth generator method without user and pass
        return createService(serviceClass, baseUrl, USERNAME, PASSWORD);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, String username, String password) {
        // set endpoint url
        builder.setEndpoint(baseUrl);

        if (username != null && password != null) {
            // concatenate username and password with colon for authentication
            final String credentials = username + ":" + password;

            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    // create Base64 encodet string
                    String string = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", string);
                }
            });
        }

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }


    public static <S> S createService(Class<S> serviceClass, String baseUrl, final AccessToken accessToken) {
        // set endpoint url
        builder.setEndpoint(baseUrl);

        if (accessToken != null) {
            builder.setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("Accept", "application/json");
                    request.addHeader("Authorization", accessToken.getTokenType() + " " + accessToken.getAccessToken());
                }
            });
        }

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }
}

