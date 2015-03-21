package com.github.boukefalos.ibuddy;

import java.io.IOException;
import java.util.Properties;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.parameters.ConstantParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.boukefalos.ibuddy.implementation.Local;
import com.github.boukefalos.ibuddy.implementation.Remote;
import com.github.boukefalos.ibuddy.server.Server;

public class Loader {
    protected static final String PROPERTIES_FILE = "ibuddy.properties";
	protected Logger logger = LoggerFactory.getLogger(Loader.class);
    protected MutablePicoContainer pico;

	public Loader(Properties properties) {
		/* Initialise container */
		pico = new DefaultPicoContainer();
	
		/* Add implementation */
		if (properties.getProperty("implementation").equals("local")) {
			/* Local */
			pico.addComponent(Local.class);
		} else {
			/* Remote */
			pico.addComponent(Remote.class, Remote.class, new Parameter[]{
				new ConstantParameter(properties.getProperty("remote.host")),
				new ConstantParameter(Integer.valueOf(properties.getProperty("remote.port")))});
		}
	
		/* Add server */
		if (properties.getProperty("server") != null) {
			pico.addComponent(Server.class, Server.class, new Parameter[]{
				new ConstantParameter(getiBuddy()),
				new ConstantParameter(Integer.valueOf(properties.getProperty("server.port")))});
		}
	}

    public static Loader getLoader() throws IOException {
    	return getLoader(PROPERTIES_FILE);    	
    }

	public static Loader getLoader(String propertiesFile) throws IOException {
		/* Read properties file */
		Properties properties = new Properties();
		properties.load(Loader.class.getClassLoader().getResourceAsStream(propertiesFile));

		/* Initialise loader */
		return new Loader(properties);
	}

    public iBuddy getiBuddy() {
    	return pico.getComponent(iBuddy.class);
    }

    public Server getServer() {
    	return pico.getComponent(Server.class);
    }

}
