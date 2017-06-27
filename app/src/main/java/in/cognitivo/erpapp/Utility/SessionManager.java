package in.cognitivo.erpapp.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import in.cognitivo.erpapp.Entity.User;

/**
 * Created by cognitivo on 18/05/17.
 */

public class SessionManager {

    public static final String PREFS_NAME = "ERPAPP_PREFS";
    public static final String PREF_AFFILIATE_ID = "PREF_USER_ID";
    public static final String PREF_AFFILIATE_NAME = "PREF_AFFILIATE_NAME";

    private final SharedPreferences mPrefs;

    private boolean mIsLoggedIn = false;

    private static SessionManager INSTANCE;

    public static SessionManager get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionManager(context);
        }
        return INSTANCE;
    }

    private SessionManager(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_AFFILIATE_ID, null));
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void saveUser(User affiliate) {
        if (affiliate != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREF_AFFILIATE_ID, affiliate.getId());
            editor.putString(PREF_AFFILIATE_NAME, affiliate.getName());

            editor.apply();

            mIsLoggedIn = true;
        }
    }

    public void logOut(){
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_AFFILIATE_ID, null);
        editor.putString(PREF_AFFILIATE_NAME, null);

        editor.apply();
    }

    public SharedPreferences getmPrefs(){

        return mPrefs;

    }


    public  String getPrefAffiliateName() {
        return PREF_AFFILIATE_NAME;
    }

    public  String getPrefAffiliateId() {
        return PREF_AFFILIATE_ID;
    }
}
