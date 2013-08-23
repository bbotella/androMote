package es.upv.pros.andromote.agenthandlers;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import es.upv.pros.andromote.agenthandlersabstracts.AbstractAgentHandler;
import es.upv.pros.andromote.gcmcommunication.MessageSender;
import es.upv.pros.andromote.jsonclassess.ServerPayload;

/**
 * Created by bbotella on 21/08/13.
 */
public class MoteHandler extends AbstractAgentHandler {

    String SENDER_ID = "84815785587";
    ServerPayload payload;
    Context context;

    public MoteHandler(ServerPayload payload, Context context){
        super(payload);
        this.payload=payload;
        this.context = context;
    }

    @Override
    public void handleMessage(){
        String operation = payload.getOperation();
        if(operation.equals("getBattery")){
            handleGetBattery();
        } else if(operation.equals("ack")){
            handleAckMessage();
        }
    }

    private void handleGetBattery(){
        //Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        //int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        //int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        //float final_level=-1;
        // Error checking that probably isn't needed but I added just in case.
        //if(level == -1 || scale == -1) {
        //    final_level = 50.0f;
        //}
        //final_level = ((float)level / (float)scale) * 100.0f;

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;

        MessageSender sender = new MessageSender(SENDER_ID, batteryPct+"", context);
        sender.sendMessage();
    }

    private void handleAckMessage(){

    }

}
