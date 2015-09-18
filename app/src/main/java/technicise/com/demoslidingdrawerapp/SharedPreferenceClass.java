package technicise.com.demoslidingdrawerapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by amiyo on 18/9/15.
 */
public class SharedPreferenceClass {

    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    public static final String KEY_SET_SLIDING_STATE  = "KEY_SET_SLIDING_STATE";

    public SharedPreferenceClass(Context context) {

        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void setKeySetSlidingState(String SlidingState)

    {
        editor.remove(KEY_SET_SLIDING_STATE);
        editor.putString(KEY_SET_SLIDING_STATE, SlidingState);
        editor.commit();

    }
    public String getKeySetSlidingState()
    {
        String  SlidingState= pref.getString(KEY_SET_SLIDING_STATE, null);
        return SlidingState;
    }

  }