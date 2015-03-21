package com.github.boukefalos.ibuddy.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.jraf.jlibibuddy.IBuddy;
import org.jraf.jlibibuddy.IBuddy.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.boukefalos.ibuddy.iBuddy;
import com.github.boukefalos.ibuddy.exception.ServerException;
import com.github.boukefalos.ibuddy.exception.iBuddyException;

public class Server extends Thread {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected iBuddy iBuddy;
	protected DatagramSocket diagramSocket;

	public Server(iBuddy iBuddy, int port) throws ServerException {
		logger.debug(String.valueOf(port));
		this.iBuddy = iBuddy;
		try {
			diagramSocket = new DatagramSocket(port);
			return;
		} catch (SocketException e) {
			logger.error("Failed to initialize socket", e);
		}
		throw new ServerException();
	}

	@SuppressWarnings("incomplete-switch")
	public void run() {
		while (true) {
			logger.debug("Wait for input");
			byte[] buffer = new byte[1024];
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
			try {
				diagramSocket.receive(datagramPacket);
			} catch (IOException e) {
				logger.error("Failed to receive packet", e);
			}
			// Rewrite with protocol buffers!
			String received = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
			
			try {
				Color color = IBuddy.Color.valueOf(received);
				switch (color) {
					case RED:
						iBuddy.setHeadRed(true);
						break;
					case GREEN:
						iBuddy.setHeadGreen(true);
						break;
					case BLUE:
						iBuddy.setHeadBlue(true);
						break;
				}
			} catch (IllegalArgumentException e) {
				logger.error("No such command", e);
			} catch (iBuddyException e) {
				logger.error("Failed to send command to iBuddy", e);
			}
		}		
	}
}