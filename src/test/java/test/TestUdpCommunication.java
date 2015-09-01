package test;

import java.util.Properties;

import proto.Ibuddy.Color;

import com.github.boukefalos.ibuddy.Loader;
import com.github.boukefalos.ibuddy.Server;
import com.github.boukefalos.ibuddy.iBuddy;

public class TestUdpCommunication {
    public static void main(String[] args) {
        try {
            Properties localProperties = new Properties();
            localProperties.setProperty("implementation", "local");
            localProperties.setProperty("protocol", "udp");
            localProperties.setProperty("server", "true");
            localProperties.setProperty("server.port", "8883");
            localProperties.setProperty("server.protocol", "udp");

            Properties remoteProperties = new Properties();
            remoteProperties.setProperty("implementation", "remote");
            remoteProperties.setProperty("protocol", "udp");
            remoteProperties.setProperty("remote.host", "255.255.255.255");
            remoteProperties.setProperty("remote.port", "8883");

            Loader localLoader = new Loader(localProperties);
            Loader remoteLoader = new Loader(remoteProperties);

            iBuddy iBuddy = remoteLoader.getiBuddy();            
            Server server = localLoader.getServer();

            iBuddy.start();
            server.start();

            iBuddy.setHead(Color.WHITE);

            server.exit();
            iBuddy.exit();
        } catch (Exception e) {
            e.printStackTrace();
        }    
    }
}
