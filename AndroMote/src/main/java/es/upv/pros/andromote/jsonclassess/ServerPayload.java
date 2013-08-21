package es.upv.pros.andromote.jsonclassess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bbotella on 21/08/13.
 */
public class ServerPayload {
    private String operation_type;
    private String operation;
    private ArrayList <ServerParameter> parameters;

    public ServerPayload (String stringPayload){
        try {
            JSONObject jsonPayload = new JSONObject(stringPayload);
            this.operation_type = jsonPayload.getString("operation_payload");
            this.operation = jsonPayload.getString("operation");
            JSONArray arrayParams = jsonPayload.getJSONArray("parameters");
            this.parameters = new ArrayList<ServerParameter>();
            for(int i=0; i<arrayParams.length(); i++){
                JSONObject obj = arrayParams.getJSONObject(i);
                ServerParameter par = new ServerParameter(obj.getString("name"), obj.getString("value"));
                this.parameters.add(par);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            this.operation_type = null;
            this.operation = null;
            this.parameters = null;
        }
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public ArrayList<ServerParameter> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<ServerParameter> parameters) {
        this.parameters = parameters;
    }
}
