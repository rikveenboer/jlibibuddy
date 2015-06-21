package test;

import java.util.Properties;

import proto.Ibuddy.Color;

import com.github.boukefalos.ibuddy.Loader;
import com.github.boukefalos.ibuddy.Server;
import com.github.boukefalos.ibuddy.iBuddy;

public class TestTcpCommunication {
	public static void main(String[] args) {
		try {
			Properties localProperties = new Properties();
			localProperties.setProperty("implementation", "local");
			localProperties.setProperty("server", "true");
			localProperties.setProperty("server.port", "8883");
			localProperties.setProperty("server.protocol", "tcp");
			localProperties.setProperty("tcp.implementation", "socket");

			Properties remoteProperties = new Properties();
			remoteProperties.setProperty("implementation", "remote");
			remoteProperties.setProperty("protocol", "tcp");
			remoteProperties.setProperty("remote.host", "localhost");
			remoteProperties.setProperty("remote.port", "8883");

			Loader localLoader = new Loader(localProperties);
			Loader remoteLoader = new Loader(remoteProperties);

			Server server = localLoader.getServer();
			iBuddy iBuddy = remoteLoader.getiBuddy();

			server.start();
			iBuddy.start();

			iBuddy.setHead(Color.BLUE);

			server.exit();
			iBuddy.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
