package com.camsys.carmonic.utilities;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.camsys.carmonic.constants.Constants;

public class SharedData {

    SharedPreferences pref;
    Editor editor;
    Context _context;


    // Constructor
    public SharedData(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(Constants.SharedDataCst.PREF_NAME, Constants.SharedDataCst.PRIVATE_MODE);
        editor = pref.edit();
    }

        public  void Clear(String key)
        {
            editor.clear();
            editor.commit();

        }

        public  String Get(String key, String defaultValue)
        {
            String  prefString =  pref.getString(key,defaultValue);
            System.out.println("Save key " + key + " value " + prefString);
            return prefString;
        }

        public  void Set(String key, String value)
        {
            try{
                editor.putString(key, value);
                editor.commit();
                System.out.println("Save key " + key + " value " + value);

            }catch (Exception ex){
                ex.printStackTrace();
                System.out.println("Save key " + key + " Exception:: " + ex.toString());
            }
        }



}