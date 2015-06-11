package com.github.boukefalos.ibuddy.server;

import com.github.boukefalos.ibuddy.iBuddy;
import com.github.boukefalos.ibuddy.helper.ServerHelper;

import base.server.socket.TcpServer;

public class iBuddyTcpServer extends TcpServer implements iBuddyServer {
	protected iBuddy iBuddy;

	public iBuddyTcpServer(iBuddy iBuddy, int port, Class<?> clientClass) {
		super(port, clientClass);
		this.iBuddy = iBuddy;
	}

	public void receive(byte[] buffer) {
		ServerHelper.receive(iBuddy, buffer);
	}
}
