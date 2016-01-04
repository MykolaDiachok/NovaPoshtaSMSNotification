package ua.radioline.novaposhtasmsnotification.basic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import ua.radioline.novaposhtasmsnotification.MainActivity;

/**
 * Created by mikoladyachok on 12/28/15.
 */
public class BaseValues {

    public static String GetValue(String key) {
        Context applicationContext = MainActivity.getContextOfApplication();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        return prefs.getString(key, "");

    }


    public static void SetValue(String Key, String Value) {
        Context applicationContext = MainActivity.getContextOfApplication();
        SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(applicationContext).edit();
        prefs.putString(Key, Value);
        prefs.commit();
    }
}
