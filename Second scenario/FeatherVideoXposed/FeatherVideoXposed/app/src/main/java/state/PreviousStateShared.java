package state;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class PreviousStateShared {

    private SharedPreferences db;
    private SharedPreferences.Editor editor;
    private static String DB_NAME = "state";

    public PreviousStateShared(Context c) {
        db = c.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        editor = db.edit();
    }

    public void insert (String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void insert (String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void insert (String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void insert (String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.commit();
    }

    public void insert (String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public String get (String key, String value) {
        String result = db.getString(key, value);
        return result;
    }

    public int get (String key, int value) {
        int result = db.getInt(key, value);
        return result;
    }

    public boolean get (String key, boolean value) {
        boolean result = db.getBoolean(key, value);
        return result;
    }

    public Set<String> get (String key, Set<String> value) {
        Set result = db.getStringSet(key, value);
        return result;
    }

    public float get(String key, float value) {
        float result = db.getFloat(key, value);
        return result;
    }

    public void delete (String key) {
        editor.remove(key);
    }

}
