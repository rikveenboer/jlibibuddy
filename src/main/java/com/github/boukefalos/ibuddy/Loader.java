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
				addSender(properties);
				break;
		}

		/* Add server */
		if (properties.getProperty("server") != null) {
			pico.addComponent(Server.class);

			/* Add server forwarder implementation */
			addForwarder(properties);			
		}
	}

	public iBuddy getiBuddy() {
    	return pico.getComponent(iBuddy.class);
    }

    public Server getServer() {
    	return pico.getComponent(Server.class);
    }
}
