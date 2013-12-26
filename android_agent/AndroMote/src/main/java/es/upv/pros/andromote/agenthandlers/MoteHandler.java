package es.upv.pros.andromote.agenthandlers;


import android.content.Context;

import java.util.Hashtable;

import es.upv.pros.andromote.agenthandlersabstracts.AbstractAgentHandler;
import es.upv.pros.andromote.gcmcommunication.MessageSender;
import es.upv.pros.andromote.jsonclassess.ServerPayload;
import es.upv.pros.andromote.moteWorkers.BatteryGetter;
import es.upv.pros.andromote.preferencesClasses.AgentPermissionPreferences;

import  static es.upv.pros.andromote.auxclazzess.Constants.*;

/**
 * Created by bbotella on 21/08/13.
 */
public class MoteHandler extends AbstractAgentHandler {

    private ServerPayload payload;
    private Context context;
    private AgentPermissionPreferences preferences;

    public MoteHandler(ServerPayload payload, Context context){
        super(payload, context);
        this.payload=payload;
        this.context = context;
        this.preferences = new AgentPermissionPreferences(context);
    }

    @Override
    public void handleMessage(){
        String operation = payload.getOperation();
        if(operation.equals("getBattery")){
            if(this.preferences.getGetBatteryPermission()){
                handleGetBattery();
            } else{
                this.handlePermissionDenied(OPERATION_NOT_ALLOWED_CODE);
            }

        } else if(operation.equals("ack")){
            handleAckMessage();
        }
    }

    private void handleGetBattery(){
        BatteryGetter battery = new BatteryGetter(context);
        float batteryPct = battery.getBatteryLevel();
        Hashtable<String, String> result
                = new Hashtable<String, String>();
        result.put("batteryLevel", batteryPct+"");
        MessageSender sender = new MessageSender(SENDER_ID, result, context);
        sender.sendMessage();
    }
}
