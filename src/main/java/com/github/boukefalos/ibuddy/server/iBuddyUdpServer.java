package com.github.boukefalos.ibuddy.server;

import base.server.datagram.UdpServer;

import com.github.boukefalos.ibuddy.iBuddy;
import com.github.boukefalos.ibuddy.helper.ServerHelper;

public class iBuddyUdpServer extends UdpServer implements iBuddyServer {
    protected iBuddy iBuddy;

	public iBuddyUdpServer(iBuddy iBuddy, int port) {
		this(iBuddy, port, BUFFER_SIZE);
	}

	public iBuddyUdpServer(iBuddy iBuddy, int port, int bufferSize) {
		super(port, bufferSize);
		this.iBuddy = iBuddy;
		this.bufferSize = bufferSize;
	}

	protected void receive(byte[] buffer) {		
		ServerHelper.receive(iBuddy, buffer);
	}
}