package es.upv.pros.andromote.auxclazzess;

/**
 * Created by bbotella on 24/08/13.
 */
public final class Constants {
    public static final String SENDER_ID = "84815785587";
    public static final String TAG = "AndroMote";
    public static final int NOTIFICATION_ID = 1;

    private Constants(){
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }
}
