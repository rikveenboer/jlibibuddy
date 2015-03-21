package com.github.boukefalos.ibuddy.implementation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.jraf.jlibibuddy.IBuddyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;






import com.github.boukefalos.ibuddy.iBuddy;
import com.github.boukefalos.ibuddy.exception.iBuddyException;

import ibuddy.Ibuddy.Color;
import ibuddy.Ibuddy.Command;
import ibuddy.Ibuddy.Command.Type;
import ibuddy.Ibuddy.SetLed;

public class Remote implements iBuddy {
    protected Logger logger = LoggerFactory.getLogger(getClass());

	protected DatagramSocket udpSocket;
	protected InetAddress inetAddress;
	protected int port;

	public Remote(String host, int port) throws UnknownHostException{
		inetAddress = InetAddress.getByName(host);
		logger.debug(host);
		logger.debug(String.valueOf(port));
		this.port = port;
	}

	public void setHeadRed(boolean headRed) {
		Command command = Command.newBuilder()
	        	.setType(Type.SET_LED)
	        	.setSetLed(
	        		SetLed.newBuilder()
	        			.setColor(Color.RED)
	        			.setPos(0).build()).build();

			ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
			try {
				command.writeDelimitedTo(output);
				send(output.toByteArray());
			} catch (IOException e) {
				logger.error("Failed to send command");
		}
	}

	public void setHeadGreen(boolean headGreen) {
		System.out.println("oki");
		send("GREEN");
	}

	public void setHeadBlue(boolean headBlue) {
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


	@Override
	public void test() throws IBuddyException {
		// TODO Auto-generated method stub
		
	}
}