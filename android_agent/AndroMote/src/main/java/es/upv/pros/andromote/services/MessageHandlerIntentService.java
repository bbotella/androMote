package es.upv.pros.andromote.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Hashtable;

import es.upv.pros.andromote.agenthandlers.ComputeHandler;
import es.upv.pros.andromote.agenthandlers.MoteHandler;
import es.upv.pros.andromote.agenthandlers.NotifyHandler;
import es.upv.pros.andromote.broadcastreceivers.AgentBroadcastReceiver;
import es.upv.pros.andromote.gcmcommunication.MessageSender;
import es.upv.pros.andromote.jsonclassess.ServerPayload;
import es.upv.pros.andromote.preferencesClasses.AgentPermissionPreferences;

import static es.upv.pros.andromote.auxclazzess.Constants.MODE_NOT_ALLOWED_CODE;
import static es.upv.pros.andromote.auxclazzess.Constants.SENDER_ID;
import static es.upv.pros.andromote.auxclazzess.Constants.TAG;

/**
 * Created by bbotella on 19/08/13.
 *
 * Service ot manage intents according to remote messages
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
        // Intent we received in BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.d(TAG, "Send Error");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.d(TAG, "Deleted messages on server");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
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
                        ComputeHandler computeHandler = new ComputeHandler(payload, context);
                        computeHandler.handleMessage();
                    } else {
                        this.operationNotAllowedHandler();
                    }
                } else if(payload.getOperation_type().equals("notificate")){
                    if(preferences.getNotifyPermission()){
                        NotifyHandler notifyHandler = new NotifyHandler(payload, context);
                        notifyHandler.handleMessage();
                    } else {
                        this.operationNotAllowedHandler();
                    }
                }
                Log.d(TAG, "Received: " + extras.toString());
            }
        }
        AgentBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void operationNotAllowedHandler(){
        Hashtable<String, String> result = new Hashtable<String, String>();
        result.put("exitCode", MODE_NOT_ALLOWED_CODE+"");
        result.put("msg", "Mode not allowed by agent");
        MessageSender sender = new MessageSender(SENDER_ID, result, context);
        sender.sendMessage();
    }
}