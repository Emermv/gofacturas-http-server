package gofacturas.core;

import java.awt.*;

public class Alert {
    public Alert() {
    }

    public void show(String title,String message,TrayIcon.MessageType type)  {
        //Obtain only one instance of the SystemTray object
        if(SystemTray.isSupported()){
            if(type==null){
                type=TrayIcon.MessageType.INFO;
            }
            SystemTray tray = SystemTray.getSystemTray();

            //If the icon is a file
            Image image = Toolkit.getDefaultToolkit().createImage(Constants.ASSETS_PATH+"/logo.png");
            // Alternative (if the icon is on the classpath):
          //  Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("logo.png"));

            TrayIcon trayIcon = new TrayIcon(image, "GOFACTURAS");
            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("GOFACTURAS");
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
            trayIcon.displayMessage(title, message, type);
        }

    }
}
