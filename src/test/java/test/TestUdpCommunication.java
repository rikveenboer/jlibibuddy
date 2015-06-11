package test;

import java.util.Properties;

import base.work.Work;

import com.github.boukefalos.ibuddy.Loader;
import com.github.boukefalos.ibuddy.iBuddy;

public class TestUdpCommunication {
	public static void main(String[] args) {
		try {
			Properties localProperties = new Properties();
			localProperties.setProperty("implementation", "local");
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

			iBuddy localiBuddy = localLoader.getiBuddy();
			iBuddy remoteiBuddy = remoteLoader.getiBuddy();

			localiBuddy.setHeadRed(false);
			
			Work server = localLoader.getServer();

			server.start();
			remoteiBuddy.setHeadRed(true);
			Thread.sleep(1000);
			server.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
