package com.github.boukefalos.ibuddy;

import java.io.IOException;
import java.util.Properties;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.parameters.ConstantParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import base.work.Work;

import com.github.boukefalos.ibuddy.client.iBuddyTcpClient;
import com.github.boukefalos.ibuddy.implementation.LocalImplementation;
import com.github.boukefalos.ibuddy.implementation.TcpImplementation;
import com.github.boukefalos.ibuddy.implementation.UdpImplementation;
import com.github.boukefalos.ibuddy.server.iBuddyTcpServer;
import com.github.boukefalos.ibuddy.server.iBuddyUdpServer;
import com.github.boukefalos.ibuddy.server.iBuddyServer;

public class Loader {
    protected static final String PROPERTIES_FILE = "ibuddy.properties";
	protected Logger logger = LoggerFactory.getLogger(Loader.class);
    protected MutablePicoContainer pico;

	public Loader(Properties properties) {
		/* Initialise container */
		pico = new DefaultPicoContainer();
	
		/* Add implementation */
		switch (properties.getProperty("implementation")) {
			case "local":
				pico.addComponent(LocalImplementation.class);
				break;				
			case "remote":
				//pico.addComponent(Remote.class);
				break;
		}

		/* Add protocol */
		if (properties.getProperty("protocol") != null) {
			switch (properties.getProperty("protocol")) {
				case "tcp":
					pico.addComponent(TcpImplementation.class, TcpImplementation.class, new Parameter[]{
						new ConstantParameter(properties.getProperty("remote.host")),
						new ConstantParameter(Integer.valueOf(properties.getProperty("remote.port")))});
					break;
				case "udp":
					pico.addComponent(UdpImplementation.class, UdpImplementation.class, new Parameter[] {
						new ConstantParameter(properties.getProperty("remote.host")),
						new ConstantParameter(Integer.valueOf(properties.getProperty("remote.port")))});
					break;
			}
		}

		/* Add server */
		if (properties.getProperty("server") != null) {
			switch (properties.getProperty("server.protocol")) {
				case "tcp":
					pico.addComponent(iBuddyTcpServer.class, iBuddyTcpServer.class, new Parameter[]{
						new ConstantParameter(getiBuddy()),
						new ConstantParameter(Integer.valueOf(properties.getProperty("server.port"))),
						new ConstantParameter(iBuddyTcpClient.class)});
					break;
				case "udp":
					pico.addComponent(iBuddyUdpServer.class, iBuddyUdpServer.class, new Parameter[]{
						new ConstantParameter(getiBuddy()),
						new ConstantParameter(Integer.valueOf(properties.getProperty("server.port")))});
			}
			
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

    public Work getServer() {
    	return (Work) pico.getComponent(iBuddyServer.class);
    }

}
