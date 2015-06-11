package com.github.boukefalos.ibuddy;

import java.util.Properties;

import org.picocontainer.Parameter;
import org.picocontainer.parameters.ConstantParameter;

import base.loader.AbstractLoader;
import base.work.Work;

import com.github.boukefalos.ibuddy.client.iBuddyTcpClient;
import com.github.boukefalos.ibuddy.implementation.LocalImplementation;
import com.github.boukefalos.ibuddy.implementation.TcpImplementation;
import com.github.boukefalos.ibuddy.implementation.UdpImplementation;
import com.github.boukefalos.ibuddy.server.iBuddyServer;
import com.github.boukefalos.ibuddy.server.iBuddyTcpServer;
import com.github.boukefalos.ibuddy.server.iBuddyUdpServer;

public class Loader extends AbstractLoader {
    protected static final String PROPERTIES_FILE = "ibuddy.properties";

	public Loader(Properties properties) {
		super();

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

    public iBuddy getiBuddy() {
    	return pico.getComponent(iBuddy.class);
    }

    public Work getServer() {
    	return (Work) pico.getComponent(iBuddyServer.class);
    }

}
