package com.github.boukefalos.ibuddy.implementation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.boukefalos.ibuddy.iBuddy;

public class Remote implements iBuddy {
    protected Logger logger = LoggerFactory.getLogger(getClass());

	protected DatagramSocket udpSocket;
	protected InetAddress inetAddress;
	protected int port;

	public Remote(String host, int port) throws UnknownHostException{
		inetAddress = InetAddress.getByName(host);
		this.port = port;
	}

	public void sendHeadRed(boolean headRed) {
		send("RED");
	}

	public void sendHeadGreen(boolean headGreen) {
		send("GREEN");
	}

	public void sendHeadBlue(boolean headBlue) {
		send("BLUE");
	}

	protected void send(String request) {
		send(request.getBytes());
	}

	protected void send(byte[] buffer) {
		try {
			setup();
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
			udpSocket.send(datagramPacket);
		} catch (IOException e) {
			logger.error("Failed to send buffer", e);
		}
	}

	protected boolean setup() {
		if (udpSocket == null) {
			try {
				udpSocket = new DatagramSocket();
			} catch (SocketException e) {
				logger.error("Failed to create socket", e);
				return false;
			}
			Runtime.getRuntime().addShutdownHook(new Thread() {	
				public void run() {
					udpSocket.close();
				}
			});
		}
		return true;
	}
}