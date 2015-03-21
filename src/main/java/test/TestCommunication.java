package test;

import java.util.Properties;

import com.github.boukefalos.ibuddy.Loader;
import com.github.boukefalos.ibuddy.iBuddy;
import com.github.boukefalos.ibuddy.server.Server;

public class TestCommunication {
	public static void main(String[] args) {
		try {
			Properties localProperties = new Properties();
			localProperties.setProperty("implementation", "local");
			localProperties.setProperty("server", "true");
			localProperties.setProperty("server.port", "8883");
			
			Properties remoteProperties = new Properties();
			remoteProperties.setProperty("implementation", "remote");
			remoteProperties.setProperty("remote.host", "localhost");
			remoteProperties.setProperty("remote.port", "8883");

			Loader localLoader = new Loader(localProperties);
			Loader remoteLoader = new Loader(remoteProperties);

			iBuddy localiBuddy = localLoader.getiBuddy();
			iBuddy remoteiBuddy = remoteLoader.getiBuddy();

			localiBuddy.test();
			 
			Server server = localLoader.getServer();

			server.start();
			remoteiBuddy.setHeadGreen(true);
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
