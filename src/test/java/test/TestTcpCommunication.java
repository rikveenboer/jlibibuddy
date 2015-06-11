package test;

import java.util.Properties;

import proto.Ibuddy.Color;
import base.work.Work;

import com.github.boukefalos.ibuddy.Loader;
import com.github.boukefalos.ibuddy.iBuddy;

public class TestTcpCommunication {
	public static void main(String[] args) {
		try {
			Properties localProperties = new Properties();
			localProperties.setProperty("implementation", "local");
			localProperties.setProperty("server", "true");
			localProperties.setProperty("server.port", "8883");
			localProperties.setProperty("server.protocol", "tcp");

			Properties remoteProperties = new Properties();
			remoteProperties.setProperty("implementation", "remote");
			remoteProperties.setProperty("protocol", "tcp");
			remoteProperties.setProperty("remote.host", "localhost");
			remoteProperties.setProperty("remote.port", "8883");

			Loader localLoader = new Loader(localProperties);
			Loader remoteLoader = new Loader(remoteProperties);

			iBuddy localiBuddy = localLoader.getiBuddy();
			iBuddy remoteiBuddy = remoteLoader.getiBuddy();
			//localiBuddy.setHead(Color.WHITE);
 
			Work server = localLoader.getServer();
			server.start();
			remoteiBuddy.setHeadRed(true);
			Thread.sleep(1000);
			//server.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
