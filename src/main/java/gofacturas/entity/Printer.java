package gofacturas.entity;

public class Printer {
    String name;
    int state;

    public Printer(String name, int state) {
        this.name = name;
        this.state = state;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
