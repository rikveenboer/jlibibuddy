package com.github.boukefalos.ibuddy.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.jraf.jlibibuddy.IBuddy;
import org.jraf.jlibibuddy.IBuddy.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.boukefalos.ibuddy.Loader;
import com.github.boukefalos.ibuddy.exception.LoaderException;
import com.github.boukefalos.ibuddy.exception.ServerException;
import com.github.boukefalos.ibuddy.exception.iBuddyException;

public class Server extends Thread {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected com.github.boukefalos.ibuddy.iBuddy iBuddy;
	protected DatagramSocket diagramSocket;

	public Server(int port) throws ServerException {
		try {
			iBuddy = Loader.getiBuddy();
			diagramSocket = new DatagramSocket(port);
			return;
		} catch (LoaderException e) {
			logger.error("Failed to load iBuddy", e);
		} catch (SocketException e) {
			logger.error("Failed to initialize socket", e);
		}
		throw new ServerException();
	}

	@SuppressWarnings("incomplete-switch")
	public void run() {
		while (true) {
			byte[] buffer = new byte[1024];
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
			try {
				diagramSocket.receive(datagramPacket);
			} catch (IOException e) {
				logger.error("Failed to receive packet", e);
			}
			String received = new String(datagramPacket.getData(), datagramPacket.getOffset(), datagramPacket.getLength());
			try {
				Color color = IBuddy.Color.valueOf(received);
				switch (color) {
					case RED:
						iBuddy.sendHeadRed(true);
					case GREEN:
						iBuddy.sendHeadGreen(true);
					case BLUE:
						iBuddy.sendHeadBlue(true);
				}
			} catch (IllegalArgumentException e) {
				logger.error("No such command", e);
			} catch (iBuddyException e) {
				logger.error("Failed to send command to iBuddy", e);
			}
		}		
	}
}