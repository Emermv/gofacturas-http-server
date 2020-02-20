package gofacturas.core;

import gofacturas.entity.Settings;

import java.util.HashMap;

public class App {
    public HashMap<String,Settings> settings;
    public App( HashMap<String,Settings> s) {
        settings=s;
    }
}
