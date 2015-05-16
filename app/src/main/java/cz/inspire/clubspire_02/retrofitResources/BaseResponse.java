package cz.inspire.clubspire_02.retrofitResources;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;

/**
 * Created by michal on 5/15/15.
 */
public class BaseResponse {
    private List<String> errors = new ArrayList<String>();
    private Boolean mSuccessful = false;
    public Response getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(Response rawResponse) {
        this.rawResponse = rawResponse;
    }

    private Response rawResponse;


    public BaseResponse() {
        super();
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setSuccessful(Boolean successful) {
        mSuccessful = successful;
    }

    public Boolean isSuccessful() {
        return mSuccessful;
    }
}
