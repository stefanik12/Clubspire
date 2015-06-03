package cz.inspire.clubspire_02;

import android.app.Application;
import android.content.Context;

/**
 * Created by Vukmir on 3.6.2015.
 */
public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}