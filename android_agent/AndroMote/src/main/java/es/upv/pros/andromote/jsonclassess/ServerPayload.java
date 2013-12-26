package es.upv.pros.andromote.jsonclassess;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by bbotella on 21/08/13.
 */
public class ServerPayload {
    private String operation_type;
    private String operation;
    private Hashtable<String, Object> parameters;

    public ServerPayload (String stringPayload){
        try {
            JSONObject jsonPayload = new JSONObject(stringPayload);
            this.operation_type = jsonPayload.getString("operation_type");
            this.operation = jsonPayload.getString("operation");
            JSONArray arrayParams = jsonPayload.getJSONArray("parameters");
            this.parameters = new Hashtable<String, Object>();
            for(int i=0; i<arrayParams.length(); i++){
                JSONObject obj = arrayParams.getJSONObject(i);
                ServerParameter par = new ServerParameter(obj.getString("name"), obj.getString("value"));
                this.parameters.put(par.getName(), par.getValue());
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

    public Hashtable<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Hashtable<String, Object> parameters) {
        this.parameters = parameters;
    }
}
