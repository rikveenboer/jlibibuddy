package com.github.boukefalos.ibuddy;

import java.io.IOException;
import java.util.Properties;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.parameters.ConstantParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.boukefalos.ibuddy.exception.LoaderException;
import com.github.boukefalos.ibuddy.implementation.Local;
import com.github.boukefalos.ibuddy.implementation.Remote;
import com.github.boukefalos.ibuddy.server.Server;

public class Loader {
    protected static Logger logger = LoggerFactory.getLogger(Loader.class);
    protected static MutablePicoContainer pico;

    public static iBuddy getiBuddy() throws LoaderException {
    	if (!setup()) {
    		throw new LoaderException();
    	}
    	return pico.getComponent(iBuddy.class);
    }

    public static Server getServer() throws LoaderException {
    	if (!setup()) {
    		throw new LoaderException();
    	}
    	return pico.getComponent(Server.class);
    }

    protected static boolean setup() {
    	if (pico == null) {
    		/* Read properties file */
    		Properties properties = new Properties();
    		try {
    			properties.load(Loader.class.getClassLoader().getResourceAsStream("ibuddy.properties"));
    		} catch (IOException e) {
    			e.printStackTrace();
    			logger.error("Failed to load properties file", e);
    			return false;
    		}

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
    		pico.addComponent(Server.class, Server.class, new Parameter[]{
    			new ConstantParameter(Integer.valueOf(properties.getProperty("server.port")))});
    	}
    	return true;
    }
}
