package com.idax.ventax.DataPersistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.idax.ventax.Entity.User;

import static android.content.Context.MODE_PRIVATE;
import static com.idax.ventax.Extra.Constansts.DATA_PERSISTENCE_ENROLLED;
import static com.idax.ventax.Extra.Constansts.DATA_PERSISTENCE_IS_FIRST_TIME;
import static com.idax.ventax.Extra.Constansts.DATA_PERSISTENCE_USER;
import static com.idax.ventax.Extra.Constansts.SHARD_PREFERENCES_USER;

public class DataPersistence {

    public static DataPersistence userDataPersistence;
    public static DataPersistence enrollCompanyDataPersistence;
    public static DataPersistence isFirstTimeDataPersistence;

    //DataPersistence.userSharedPreferences = getSharedPreferences(SHARD_PREFERENCES_USER, MODE_PRIVATE);
    public SharedPreferences sharedPreferences;
    public DataPersistence(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public void clearShardPreferences() {
        sharedPreferences.edit().clear().apply();
    }
    public User getUserData() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(DATA_PERSISTENCE_USER, "");
        return gson.fromJson(json, User.class);
    }
    public void saveUserData(User obj) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(obj, User.class);
        prefsEditor.putString(DATA_PERSISTENCE_USER, json);
        prefsEditor.apply();
        prefsEditor.commit();
    }

    public Boolean getStateRequestEnroll(){
        return sharedPreferences.getBoolean(DATA_PERSISTENCE_ENROLLED, false);
    }
    public void setStateRequestEnroll(boolean b) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(DATA_PERSISTENCE_ENROLLED, b);
        prefsEditor.apply();
        prefsEditor.commit();
    }

    public Boolean isFirstTime(){
        return sharedPreferences.getBoolean(DATA_PERSISTENCE_IS_FIRST_TIME, false);
    }
    public void setFirstTime(boolean seeAgain){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(DATA_PERSISTENCE_IS_FIRST_TIME, seeAgain);
        prefsEditor.apply();
        prefsEditor.commit();
    }

}
