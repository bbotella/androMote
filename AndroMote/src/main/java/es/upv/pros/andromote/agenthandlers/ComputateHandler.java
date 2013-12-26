package es.upv.pros.andromote.agenthandlers;


import android.content.Context;

import java.util.Hashtable;

import es.upv.pros.andromote.agenthandlersabstracts.AbstractAgentHandler;
import es.upv.pros.andromote.computateworkers.SumHandler;
import es.upv.pros.andromote.gcmcommunication.MessageSender;
import es.upv.pros.andromote.jsonclassess.ServerPayload;
import es.upv.pros.andromote.preferencesclassess.AgentPermissionPreferences;

import static es.upv.pros.andromote.auxclazzess.Constants.OPERATION_NOT_ALLOWED_CODE;
import static es.upv.pros.andromote.auxclazzess.Constants.SENDER_ID;

/**
 * Created by bbotella on 21/08/13.
 */
public class ComputateHandler extends AbstractAgentHandler {

    private ServerPayload payload;
    private Context context;
    private AgentPermissionPreferences preferences;

    public ComputateHandler(ServerPayload payload, Context context){
        super(payload, context);
        this.payload=payload;
        this.context = context;
        this.preferences = new AgentPermissionPreferences(context);
    }

    @Override
    public void handleMessage(){
        String operation = payload.getOperation();
        if(operation.equals("sumFloat")){
            if(preferences.getSumFloatPermission()){
                handleSumFloat();
            } else {
                this.handlePermissionDenied(OPERATION_NOT_ALLOWED_CODE);
            }
        } else if(operation.equals("ack")){
            handleAckMessage();
        }
    }

    private void handleSumFloat(){
        Hashtable<String, Object> params = payload.getParameters();
        float num1 = Float.parseFloat((String) params.get("num1"));
        float num2 = Float.parseFloat((String) params.get("num2"));
        SumHandler sumHandler = new SumHandler();
        float sumResult = sumHandler.sumFloat(num1, num2);

        Hashtable<String, String> result
                = new Hashtable<String, String>();
        result.put("result", sumResult+"");
        MessageSender sender = new MessageSender(SENDER_ID, result, context);
        sender.sendMessage();
    }

    private void handleAckMessage(){

    }

}
