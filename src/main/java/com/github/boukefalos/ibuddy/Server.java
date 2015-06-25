package com.github.boukefalos.ibuddy;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.jraf.jlibibuddy.IBuddyException;

import proto.Ibuddy.Blink;
import proto.Ibuddy.Color;
import proto.Ibuddy.Command;
import proto.Ibuddy.Flap;
import proto.Ibuddy.Head;
import proto.Ibuddy.Nudge;
import proto.Ibuddy.State;
import base.Forwarder;
import base.server.receiver.AbstractReceiver;

public class Server extends AbstractReceiver {
	protected iBuddy iBuddy;

	public Server(iBuddy iBuddy, Forwarder forwarder) {
		super(forwarder);
		this.iBuddy = iBuddy;
	}

	public void receive(byte[] buffer) {
		ByteArrayInputStream input = new ByteArrayInputStream(buffer);
		logger.debug("Received input");
		try {
			Command command = Command.parseDelimitedFrom(input);
			logger.debug("Command type = " + command.getType().name());
			switch (command.getType()) {					
				case HEAD:
					Head head = command.getHead();
					Color color = head.getColor();
					if (head.getSingle()) {
						boolean state = head.getState().equals(State.ON);
						switch (color) {
							case RED:
								iBuddy.setHeadRed(state);
							case GREEN:
								iBuddy.setHeadGreen(state);
							case BLUE:
								iBuddy.setHeadBlue(state);
							default:
								break;
						}
					} else {
						iBuddy.setHead(color);
					}
					break;
				case WINGS:
					iBuddy.setWings(command.getWings().getDirection());
					break;
				case ROTATE:
					iBuddy.setRotate(command.getRotate().getDirection());
					break;
				case HEART:
					iBuddy.setHeart(command.getHeart().getState().equals(State.ON));
					break;
				case BLINK:
					Blink blink = command.getBlink();
					iBuddy.blink(blink.getColor(), blink.getOnTime(), blink.getOffTime(), blink.getTimes());
					break;
				case NUDGE:
					Nudge nudge = command.getNudge();
					iBuddy.nudge(nudge.getDelay(), nudge.getTimes());
					break;
				case FLAP:
					Flap flap = command.getFlap();
					iBuddy.flap(flap.getDelay(), flap.getTimes());
					break;
				default:
					iBuddy.off();
			}
		} catch (IOException e) {
			logger.error("Failed to parse input");
			return;
		} catch (IBuddyException e) {
			logger.error("Failed to send command to iBuddy", e);
		}
	}
}
