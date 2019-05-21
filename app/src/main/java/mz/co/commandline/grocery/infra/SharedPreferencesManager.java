package mz.co.commandline.grocery.infra;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    public static final String GROERY_PREF_NAME = "GROERY_PREF_NAME";

    private Context context;

    public SharedPreferencesManager(Context context) {
        this.context = context;
    }

    public void storeString(String key, String value) {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);

        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getString(key, null);
    }

    public void clearPreferences() {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();
        editor.apply();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GROERY_PREF_NAME, context.MODE_PRIVATE);
    }
}
