package es.upv.pros.andromote.agenthandlers;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import es.upv.pros.andromote.agenthandlersabstracts.AbstractAgentHandler;
import es.upv.pros.andromote.jsonclassess.ServerPayload;

/**
 * Created by bbotella on 21/08/13.
 */
public class MoteHandler extends AbstractAgentHandler {

    public MoteHandler(ServerPayload payload){
        super(payload);
    }

    @Override
    public void handleMessage(){
        String operation = super.getOperation();
        if(operation.equals("getBattery")){
            handleGetBattery();
        } else if(operation.equals("ack")){
            handleAckMessage();
        }
    }

    private void handleGetBattery(){
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float final_level=-1;
        // Error checking that probably isn't needed but I added just in case.
        if(level == -1 || scale == -1) {
            final_level = 50.0f;
        }
        final_level = ((float)level / (float)scale) * 100.0f;
    }

    private void handleAckMessage(){

    }

}
