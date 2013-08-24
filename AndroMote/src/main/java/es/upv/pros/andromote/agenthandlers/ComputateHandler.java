package es.upv.pros.andromote.agenthandlers;


import android.content.Context;

import java.util.Hashtable;

import es.upv.pros.andromote.agenthandlersabstracts.AbstractAgentHandler;
import es.upv.pros.andromote.computateworkers.SumHandler;
import es.upv.pros.andromote.gcmcommunication.MessageSender;
import es.upv.pros.andromote.jsonclassess.ServerPayload;

import static es.upv.pros.andromote.auxclazzess.Constants.SENDER_ID;

/**
 * Created by bbotella on 21/08/13.
 */
public class ComputateHandler extends AbstractAgentHandler {

    private ServerPayload payload;
    private Context context;

    public ComputateHandler(ServerPayload payload, Context context){
        super(payload);
        this.payload=payload;
        this.context = context;
    }

    @Override
    public void handleMessage(){
        String operation = payload.getOperation();
        if(operation.equals("sumFloat")){
            handleSumFloat();
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
