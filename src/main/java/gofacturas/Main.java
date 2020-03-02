package gofacturas;

import com.alexkasko.installer.DaemonLauncher;
import gofacturas.core.Alert;
import gofacturas.core.Constants;
import gofacturas.core.Socket;
import gofacturas.module.Printer;
import gofacturas.service.Settings;

import java.io.IOException;
import java.util.HashMap;

public class Main implements DaemonLauncher{

public  Socket socket=null;
    public  Main() {
       super();
        Settings s=new Settings();
        HashMap<String, gofacturas.entity.Settings> os=s.getInCode(new String[]{"TOKEN","CODE","USER"});
         socket=new Socket(Constants.SOCKET_URI,os);
    }

    public void startDaemon() {
        if(socket!=null){
            socket.start();
        }
    }

    public void stopDaemon() {
        if(socket!=null){
            socket.stop();
        }
    }


/*
public static  void main(String [] args){
   Settings s=new Settings();
    HashMap<String, gofacturas.entity.Settings> os=s.getInCode(new String[]{"TOKEN","CODE","USER"});
   Socket socket=new Socket(Constants.SOCKET_URI,os);
   socket.start();

}
*/

}
