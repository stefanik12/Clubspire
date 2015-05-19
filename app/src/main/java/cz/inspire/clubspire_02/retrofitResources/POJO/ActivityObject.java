package cz.inspire.clubspire_02.retrofitResources.POJO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michal on 5/15/15.
 */
public class ActivityObject {

    @SerializedName("id")
    public String id;

    @SerializedName("name")
    public String name;

    @SerializedName("nameBlack")
    public String iconLink;

    //TODO: consider JSONpath
}
