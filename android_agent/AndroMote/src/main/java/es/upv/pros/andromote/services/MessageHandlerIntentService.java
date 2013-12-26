package es.upv.pros.andromote.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Hashtable;

import es.upv.pros.andromote.MainActivity;
import es.upv.pros.andromote.R;
import es.upv.pros.andromote.agenthandlers.ComputateHandler;
import es.upv.pros.andromote.agenthandlers.MoteHandler;
import es.upv.pros.andromote.agenthandlers.NotificateHandler;
import es.upv.pros.andromote.broadcastreceivers.AgentBroadcastReceiver;
import es.upv.pros.andromote.gcmcommunication.MessageSender;
import es.upv.pros.andromote.jsonclassess.ServerPayload;
import es.upv.pros.andromote.preferencesclassess.AgentPermissionPreferences;

import static es.upv.pros.andromote.auxclazzess.Constants.*;

/**
 * Created by bbotella on 19/08/13.
 */
public class MessageHandlerIntentService extends IntentService {
    private NotificationManager mNotificationManager;
    Context context;

    public MessageHandlerIntentService() {
        super("MessageHandlerIntentService");

    }

    @Override
    public void onCreate(){
        super.onCreate();
        this.context = getApplicationContext();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM
             * will be extended in the future with new message types, just ignore
             * any message types you're not interested in, or that you don't
             * recognize.
             */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " +
                        extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                String server_message = extras.getString("server_message", "ERROR");
                ServerPayload payload = new ServerPayload(server_message);
                AgentPermissionPreferences preferences = new AgentPermissionPreferences(context);
                if(payload.getOperation_type().equals("mote")){
                    if(preferences.getMotePermission()){
                        MoteHandler moteHandler = new MoteHandler(payload, context);
                        moteHandler.handleMessage();
                    } else {
                        this.operationNotAllowedHandler();
                    }
                } else if(payload.getOperation_type().equals("computate")){
                    if (preferences.getComputePermission()){
                        ComputateHandler computateHandler = new ComputateHandler(payload, context);
                        computateHandler.handleMessage();
                    } else {
                        this.operationNotAllowedHandler();
                    }
                } else if(payload.getOperation_type().equals("notificate")){
                    if(preferences.getNotifyPermission()){
                        NotificateHandler notificateHandler = new NotificateHandler(payload, context);
                        notificateHandler.handleMessage();
                    } else {
                        this.operationNotAllowedHandler();
                    }
                }
                Log.d(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        AgentBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("AndroMote notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void operationNotAllowedHandler(){
        Hashtable<String, String> result
                = new Hashtable<String, String>();
        result.put("exitcode", MODE_NOT_ALLOWED_CODE+"");
        result.put("msg", "Mode not allowed by agent");
        MessageSender sender = new MessageSender(SENDER_ID, result, context);
        sender.sendMessage();
    }
}