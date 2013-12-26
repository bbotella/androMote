package es.upv.pros.andromote.agenthandlers;


import android.content.Context;

import es.upv.pros.andromote.agenthandlersabstracts.AbstractAgentHandler;
import es.upv.pros.andromote.jsonclassess.ServerPayload;
import es.upv.pros.andromote.preferencesclassess.AgentPermissionPreferences;

/**
 * Created by bbotella on 21/08/13.
 */
public class NotificateHandler extends AbstractAgentHandler {

    private ServerPayload payload;
    private Context context;
    private AgentPermissionPreferences preferences;

    public NotificateHandler(ServerPayload payload, Context context){
        super(payload, context);
        this.payload=payload;
        this.context = context;
        this.preferences = new AgentPermissionPreferences(context);
    }

    @Override
    public void handleMessage(){
    }

    private void handleAckMessage(){

    }

}
