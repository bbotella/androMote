package es.upv.pros.andromote.agenthandlersabstracts;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Hashtable;

import es.upv.pros.andromote.agenthanlersinterfaces.IAgentHandler;
import es.upv.pros.andromote.gcmcommunication.MessageSender;
import es.upv.pros.andromote.jsonclassess.ServerPayload;
import static es.upv.pros.andromote.auxclazzess.Constants.*;

/**
 * Created by bbotella on 21/08/13.
 */
public abstract class AbstractAgentHandler extends Activity implements IAgentHandler  {
    private String from;
    private ServerPayload payload;
    private String operation;
    private Context context;

    public AbstractAgentHandler(ServerPayload payload, Context context){
        this.payload=payload;
        this.context = context;
    }

    public void handleMessage(){
        Log.d(TAG, "Handling message");
    }

    public void sendAck(){

    }

    public void handleAckMessage(){

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public ServerPayload getPayload() {
        return payload;
    }

    public void setPayload(ServerPayload payload) {
        this.payload = payload;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    protected void handlePermissionDenied(int error_code){
        Hashtable<String, String> result
                = new Hashtable<String, String>();
        result.put("exitcode", error_code+"");
        result.put("msg", "Operation not allowed by agent");
        MessageSender sender = new MessageSender(SENDER_ID, result, this.context);
        sender.sendMessage();
    }
}
