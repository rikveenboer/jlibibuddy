package com.github.boukefalos.ibuddy.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jraf.jlibibuddy.IBuddyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proto.Ibuddy.Blink;
import proto.Ibuddy.Color;
import proto.Ibuddy.Command;
import proto.Ibuddy.Direction;
import proto.Ibuddy.Flap;
import proto.Ibuddy.Head;
import proto.Ibuddy.Heart;
import proto.Ibuddy.Nudge;
import proto.Ibuddy.Rotate;
import proto.Ibuddy.State;
import proto.Ibuddy.Type;
import proto.Ibuddy.Wings;
import base.sender.Sender;


public class SenderHelper {
	protected final static int BUFFER_SIZE = 1024;
	protected static Logger logger = LoggerFactory.getLogger(SenderHelper.class);


	public static void setHeart(Sender sender, boolean on) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.HEART)
	        	.setHeart(Heart.newBuilder()
    				.setState(mapState(on))).build());		
	}

	public static void setHeadRed(Sender sender, boolean on) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.HEAD)
	        	.setHead(Head.newBuilder()
        			.setState(mapState(on))
					.setSingle(true)
					.setColor(Color.RED)).build());
	}


	public static void setHeadBlue(Sender sender, boolean on) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.HEAD)
	        	.setHead(Head.newBuilder()
        			.setState(mapState(on))
					.setSingle(true)
					.setColor(Color.BLUE)).build());		
	}


	public static void setHeadGreen(Sender sender, boolean on) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.HEAD)
	        	.setHead(Head.newBuilder()
        			.setState(mapState(on))
					.setSingle(true)
					.setColor(Color.GREEN)).build());
	}


	public static void setHead(Sender sender, Color color) throws IBuddyException {
		State state = mapState(!color.equals(Color.NONE));
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.HEAD)
	        	.setHead(Head.newBuilder()
        			.setState(state)
					.setSingle(false)
					.setColor(color)).build());
		
	}


	public static void setWingsUp(Sender sender) throws IBuddyException {
		setWings(sender, Direction.UP);	
	}

	public static void setWingsDown(Sender sender) throws IBuddyException {
		setWings(sender, Direction.DOWN);	
	}


	public static void setWingsCenter(Sender sender) throws IBuddyException {
		setWings(sender, Direction.CENTER);
	}

	public static void setWings(Sender sender, Direction direction) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.WINGS)
	        	.setWings(Wings.newBuilder()
        			.setDirection(direction)).build());	
		
	}

	public static void setRotateLeft(Sender sender) throws IBuddyException {
		setRotate(sender, Direction.LEFT);		
	}

	public static void setRotateRight(Sender sender) throws IBuddyException {
		setRotate(sender, Direction.RIGHT);		
	}

	public static void setRotateCenter(Sender sender) throws IBuddyException {
		setRotate(sender, Direction.CENTER);		
	}

	public static void setRotate(Sender sender, Direction direction) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.ROTATE)
	        	.setRotate(Rotate.newBuilder()
        			.setDirection(direction)).build());
	}


	public static void off(Sender sender) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.STATE).build());		
	}

	public static void blink(Sender sender, Color color, int onTime, int offTime, int times)	throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.BLINK)
	        	.setBlink(Blink.newBuilder()
        			.setOnTime(onTime)
        			.setOffTime(offTime)
        			.setTimes(times)).build());
	}

	public static void nudge(Sender sender, int delay, int times) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.NUDGE)
	        	.setNudge(Nudge.newBuilder()
        			.setDelay(delay)
        			.setTimes(times)).build());		
	}


	public static void flap(Sender sender, int delay, int times) throws IBuddyException {
		send(
			sender,
			Command.newBuilder()
	        	.setType(Type.FLAP)
	        	.setFlap(Flap.newBuilder()
        			.setDelay(delay)
        			.setTimes(times)).build());
	}

	protected static State mapState(boolean on) {
		return on ? State.ON : State.OFF;
	}

	protected static void send(Sender sender, Command command) {
		ByteArrayOutputStream output = new ByteArrayOutputStream(BUFFER_SIZE);
		try {
			command.writeDelimitedTo(output);
			sender.send(output.toByteArray());
		} catch (IOException e) {
			logger.error("Failed to send command");
		}	
	}
}
