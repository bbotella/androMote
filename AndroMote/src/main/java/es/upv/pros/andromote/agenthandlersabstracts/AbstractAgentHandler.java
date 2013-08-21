package es.upv.pros.andromote.agenthandlersabstracts;

import android.app.Activity;
import android.util.Log;

import es.upv.pros.andromote.agenthanlersinterfaces.IAgentHandler;
import es.upv.pros.andromote.jsonclassess.ServerPayload;

/**
 * Created by bbotella on 21/08/13.
 */
public abstract class AbstractAgentHandler extends Activity implements IAgentHandler  {
    private String from;
    private ServerPayload payload;
    private String operation;
    static final String TAG = "AndroMote";

    public AbstractAgentHandler(ServerPayload payload){
        this.payload=payload;
    }

    public void handleMessage(){
        Log.d(TAG, "Handling message");
    }

    public void sendAck(){

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
}
