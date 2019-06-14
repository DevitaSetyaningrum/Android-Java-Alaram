package com.example.alaram;

import android.content.Context;
import android.content.SharedPreferences;

public class AlaramPreference {

    private final String PREF_NAME = "AlaramPreference";
    private final String KEY_ONE_TIME_DATE = "OneTImeDate";
    private final String KEY_ONE_TIME_TIME = "OneTimeTime";
    private final String KEY_ONE_TIME_MESSAGE = "OneTimeMessage";
    private final String KEY_REPEATING_TIME = "RepeatingTime";
    private final String KEY_REPEATING_MESSAGE = "RepeatingMessage";

    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor editor;

    AlaramPreference(Context context) {
        mSharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreference.edit();
    }

    public void setOneTimeDate(String date) {
        editor.putString(KEY_ONE_TIME_DATE, date);
        editor.commit();
    }
    public String getOneTimeDate() {
        return mSharedPreference.getString(KEY_ONE_TIME_DATE, null);
    }

    public void setOneTimeTime(String time) {
        editor.putString(KEY_ONE_TIME_TIME, time);
        editor.commit();
    }
    public String getOneTimeTime(){
        return mSharedPreference.getString(KEY_ONE_TIME_TIME, null);
    }

    public void setOneTimeMessage(String message){
        editor.putString(KEY_ONE_TIME_MESSAGE, message);
        editor.commit();
    }
    public String getOneTimeMessage (){
        return mSharedPreference.getString(KEY_ONE_TIME_MESSAGE, null);
    }

    public void SetOneRepeatingTime (String time){
        editor.putString(KEY_REPEATING_TIME, time);
        editor.commit();
    }
    public String getOneRepeatingTime (){
        return mSharedPreference.getString(KEY_REPEATING_TIME, null);
    }

    public void SetOneRepeatingMessage(String message){
        editor.putString(KEY_REPEATING_MESSAGE, message);
        editor.commit();
    }
    public String getOneRepeatingMessage(){
        return mSharedPreference.getString(KEY_REPEATING_MESSAGE, null);
    }


    public void clear(){
        editor.clear();
        editor.commit();
    }

}
