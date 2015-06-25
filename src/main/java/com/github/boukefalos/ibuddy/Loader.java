package com.github.boukefalos.ibuddy;

import java.util.Properties;

import base.exception.LoaderException;
import base.loader.AbstractLoader;

import com.github.boukefalos.ibuddy.implementation.Local;
import com.github.boukefalos.ibuddy.implementation.Remote;

public class Loader extends AbstractLoader<Loader> {
    protected static final String PROPERTIES_FILE = "ibuddy.properties";

	public Loader(Properties properties) throws LoaderException {
		super();

		/* Add implementation */
		switch (properties.getProperty("implementation")) {
			case "local":
				pico.addComponent(Local.class);
				break;				
			case "remote":
				pico.addComponent(Remote.class);

				/* Add sender implementation */
				try {
					String protocol = properties.getOrDefault("protocol", "tcp").toString();
					String implementation = properties.getOrDefault("tcp.implementation", "socket").toString();
					String host = properties.getProperty("remote.host");
					int port = Integer.valueOf(properties.getProperty("remote.port"));
					addClientSender(protocol, implementation, host, port);
				} catch (NumberFormatException e) {
					throw new LoaderException("Failed to parse remote.port");
				}	
				break;
		}

		/* Add server */
		if (properties.getProperty("server") != null) {
			pico.addComponent(Server.class);

			/* Add server forwarder implementation */			
			try {
				String protocol = properties.getOrDefault("server.protocol", "tcp").toString();
				String implementation = properties.getOrDefault("tcp.implementation", "socket").toString();
				int port = Integer.valueOf(properties.getProperty("server.port"));
				addServerForwarder(protocol, implementation, port);
			} catch (NumberFormatException e) {
				throw new LoaderException("Failed to parse server.port");
			}	
		}
	}

	public iBuddy getiBuddy() {
    	return pico.getComponent(iBuddy.class);
    }

    public Server getServer() {
    	return pico.getComponent(Server.class);
    }
}
