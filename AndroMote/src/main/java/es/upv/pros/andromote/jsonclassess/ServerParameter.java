package es.upv.pros.andromote.jsonclassess;

/**
 * Created by bbotella on 21/08/13.
 */
public class ServerParameter {
    private String name;
    private String value;

    public ServerParameter(String name, String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
