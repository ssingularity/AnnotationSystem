package cn.sjtu.project.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static void saveSharedPreference(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences("AnnotationSystem", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringFromSharedPreference(Context context, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("AnnotationSystem", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
