package cz.inspire.clubspire_02.retrofitResources.Services;

import retrofit.RequestInterceptor;

/**
 * Created by michal on 5/16/15.
 */
public class HeaderInterceptor implements RequestInterceptor
{
    @Override
    public void intercept(RequestInterceptor.RequestFacade request)
    {
        request.addHeader("Header name", "Header Value");
    }
}

