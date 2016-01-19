package ua.radioline.novaposhtasmsnotification;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;
import io.fabric.sdk.android.Fabric;


/**
 * Created by mikoladyachok on 1/13/16.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);


        // Add your initialization code here
        Parse.initialize(this);


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);


    }
}