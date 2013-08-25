package es.upv.pros.andromote.preferencesclassess;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import static es.upv.pros.andromote.auxclazzess.Constants.ACCESS_PREFS_NAME;

/**
 * Created by bbotella on 25/08/13.
 */
public class AgentPermissionPreferences {
    String test = ACCESS_PREFS_NAME;
    private SharedPreferences settings;
    private Context context;

    public AgentPermissionPreferences(Context context){
        // Restore preferences
        //this.settings = context.getSharedPreferences(ACCESS_PREFS_NAME, 0);
        this.settings = PreferenceManager.getDefaultSharedPreferences(context);

    }

    //General getters and setters
    public boolean getMotePermission(){
        return settings.getBoolean("motePermission", true);
    }
    public boolean getComputePermission(){
        return settings.getBoolean("computePermission", true);
    }
    public boolean getNotifyPermission(){
        return settings.getBoolean("notifyPermission", true);
    }
    public void setMotePermission(boolean pref){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("motePermission", pref);
        editor.commit();
    }
    public void setComputePermission(boolean pref){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("computePermission", pref);
        editor.commit();
    }
    public void setNotifyPermission(boolean pref){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("notifyPermission", pref);
        editor.commit();
    }

    //Mote getters and setters
    public boolean getGetBatteryPermission(){
        return settings.getBoolean("getBatteryPermission", true);
    }
    public void setGetBatteryPermission(boolean pref){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("getBatteryPermission", pref);
        editor.commit();
    }


    //Compute getters and setters
    public boolean getSumFloatPermission(){
        return settings.getBoolean("sumFloatPermission", true);
    }
    public void setSumFloatPermission(boolean pref){
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("sumFloatPermission", pref);
        editor.commit();
    }


    //Notify getters and setters


    public String getPreferencesAsJson(){
        JSONObject prefJson = new JSONObject();
        Map settingsMap = settings.getAll();
        final Iterator<Map.Entry<String, Object>> it = settingsMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Object> entry = it.next();
            try {
                prefJson.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return prefJson.toString();
    }
}