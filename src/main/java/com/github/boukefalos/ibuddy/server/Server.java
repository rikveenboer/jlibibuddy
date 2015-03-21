package com.github.boukefalos.ibuddy.server;

import ibuddy.Ibuddy.Command;
import ibuddy.Ibuddy.Command.Type;
import ibuddy.Ibuddy.SetLed;

import java.io.ByteArrayInputStream;
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
			ByteArrayInputStream input = new ByteArrayInputStream(buffer);
			logger.debug("Received input");
			try {
				Command command = Command.parseDelimitedFrom(input);
				logger.debug("Command type = " + command.getType().name());
				switch (command.getType()) {
					case SET_LED:
						SetLed setLed = command.getSetLed();
						logger.debug("Color = " + setLed.getColor().name());
						switch (setLed.getColor()) {
							case RED:
							iBuddy.setHeadRed(true);
						}
						break;				
				}
			} catch (IOException e) {
				logger.error("Failed to parse input");
				return;
			} catch (iBuddyException e) {
				logger.error("Failed to send command to iBuddy", e);
			}
		}		
	}
}