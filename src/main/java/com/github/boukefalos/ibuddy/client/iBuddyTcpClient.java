package com.github.boukefalos.ibuddy.client;

import java.net.Socket;
import java.nio.channels.SocketChannel;

import base.server.channel.TcpServer;
import base.server.channel.TcpServerClient;

public class iBuddyTcpClient extends TcpServerClient {
	public iBuddyTcpClient(TcpServer server, SocketChannel socketChannel) {
		super(server, socketChannel);
		// TODO Auto-generated constructor stub
	}

	/*public iBuddyTcpClient(Socket socket) {
		super(socket);
	}*/

	public void work() {
		// Work in progress
	}
}
