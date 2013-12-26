package es.upv.pros.andromote.auxclazzess;

/**
 * Created by bbotella on 24/08/13.
 */
public final class Constants {
    public static final String SENDER_ID = "SENDER_ID";
    public static final String TAG = "AndroMote";
    public static final int NOTIFICATION_ID = 1;
    public static final String ACCESS_PREFS_NAME = "AccessPreferences";
    public static final int MODE_NOT_ALLOWED_CODE = -1;
    public static final int OPERATION_NOT_ALLOWED_CODE = -2;

    private Constants(){
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }
}
