package gofacturas.entity;

public class Settings {
    String id;
    String code;
    String value;
    int state;
    String type;

    public Settings() {
    }

    public Settings(String id, String code, String value, int state, String type) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.state = state;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
