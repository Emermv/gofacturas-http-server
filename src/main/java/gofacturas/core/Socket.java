package gofacturas.core;

import gofacturas.entity.Settings;
import gofacturas.module.Printer;
import io.socket.client.IO;
import io.socket.client.SocketIOException;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;

public class Socket extends App{
    private String code;
    private Alert alert;
   public  io.socket.client.Socket socket;
    public Socket(String url, HashMap<String,Settings> s) {
        super(s);
        this.alert=new Alert();
        try {
            IO.Options options = new IO.Options();
            options.query="code="+settings.get("CODE").getValue()+"-"+settings.get("USER").getValue();
            options.reconnection=true;
            socket= IO.socket(url,options);
            socket.on(io.socket.client.Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("connected");
                    alert.show("CONEXIÓN ESTABLECIDA","",null);
                }
            }).on(io.socket.client.Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("disconnented");
                }
            }).on(io.socket.client.Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    for(Object o : args){
                        System.out.println(o.toString());
                        if(o instanceof SocketIOException)
                            ((SocketIOException) o).printStackTrace();
                    }
                }
            }).on(io.socket.client.Socket.EVENT_ERROR, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("eventError");
                }
            });
            socket.on(Events.GET_PRINTERS, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Printer printer=new Printer();
                    JSONObject arg=printer.allPrinters();
                    emit(Events.PRINTERS,arg);
                }
            });
            socket.on(Events.SAVE_PRINTERS, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    gofacturas.service.Printer printer_service=new gofacturas.service.Printer();
                    JSONArray args=(JSONArray) objects[0];
                    printer_service.save(args);
                }
            });
            socket.on(Events.PRINT, new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    JSONObject args=(JSONObject) objects[0];
                   System.out.println(args.toString());
                    new Printer(args.getString("url"));
                    String  name=args.getString("title");
                    if(name!=null && !name.isEmpty()){
                        alert.show("IMPRIMIENDO",name,null);
                    }
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void stop(){
       if(socket.connected()){
           socket.disconnect();
       }
    }
    public  void start(){
        if(!socket.connected()){
            socket.connect();
        }
    }
    public void on(String event, Emitter.Listener listener){
        socket.emit(event,listener);
    }
    public void emit(String event,JSONObject args){
        args.put("code",settings.get("CODE").getValue());
        args.put("name",settings.get("USER").getValue());
       // args.put("token",settings.get("TOKEN"));
        socket.emit(event,args);
    }
}
