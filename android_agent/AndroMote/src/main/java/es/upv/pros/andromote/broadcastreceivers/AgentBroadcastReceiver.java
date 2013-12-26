package es.upv.pros.andromote.broadcastreceivers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import es.upv.pros.andromote.services.MessageHandlerIntentService;

/**
 * Created by bbotella on 19/08/13.
 */

public class AgentBroadcastReceiver extends WakefulBroadcastReceiver {
    //Using Android receiver patterns
    @Override
    public void onReceive(Context context, Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                MessageHandlerIntentService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}