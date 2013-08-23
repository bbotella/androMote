package es.upv.pros.andromote.gcmcommunication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bbotella on 21/08/13.
 */
public class MessageSender {
    private String message;
    private String to;
    static final String TAG = "AndroMote";
    GoogleCloudMessaging gcm;

    public MessageSender(String to, String message, Context context){
        this.to = to;
        this.message = message;
        gcm = GoogleCloudMessaging.getInstance(context);
    }

    public void sendMessage(){
        ArrayList<String> passing = new ArrayList<String>();
        passing.add(this.to);
        passing.add(this.message);
        new AsyncTask<ArrayList<String>, Void, String>() {
            @Override
            protected String doInBackground(ArrayList<String>... params) {
                String msg = "";
                try {
                    ArrayList<String> passed = params[0];
                    Bundle data = new Bundle();
                    data.putString("message", passed.get(1));
                    //data.putString("my_action", "com.google.android.gcm.demo.app.ECHO_NOW");
                    Random rnd = new Random();
                    rnd.setSeed(System.currentTimeMillis());
                    int rnd_id = rnd.nextInt();
                    String id = Integer.toString(rnd_id);
                    Log.d(TAG, "The message id is: " + id);
                    gcm.send(passed.get(0) + "@gcm.googleapis.com", id, data);
                    msg = "Sent message";
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String result) {
                //mDisplay.append(msg + "\n");
            }
        }.execute(passing);
    }
}
