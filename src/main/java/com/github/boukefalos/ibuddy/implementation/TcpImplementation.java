package com.github.boukefalos.ibuddy.implementation;

import org.jraf.jlibibuddy.IBuddyException;

import proto.Ibuddy.Color;
import proto.Ibuddy.Direction;
import base.sender.TcpSender;

import com.github.boukefalos.ibuddy.iBuddy;
import com.github.boukefalos.ibuddy.helper.SenderHelper;

public class TcpImplementation extends TcpSender implements iBuddy {
	public TcpImplementation(String host, int port) {
		super(host, port);
	}

	public void setHeart(boolean on) throws IBuddyException {
		SenderHelper.setHeart(this, on);		
	}

	public void setHeadRed(boolean on) throws IBuddyException {
		SenderHelper.setHeadRed(this, on);		
	}

	public void setHeadBlue(boolean on) throws IBuddyException {
		SenderHelper.setHeadBlue(this, on);		
	}

	public void setHeadGreen(boolean on) throws IBuddyException {
		SenderHelper.setHeadGreen(this, on);		
	}

	public void setHead(Color color) throws IBuddyException {
		SenderHelper.setHead(this, color);		
	}

	public void setWingsUp() throws IBuddyException {
		SenderHelper.setWingsUp(this);
	}

	public void setWingsDown() throws IBuddyException {
		SenderHelper.setWingsDown(this);	
	}

	public void setWingsCenter() throws IBuddyException {
		SenderHelper.setWingsCenter(this);
	}

	public void setWings(Direction direction) throws IBuddyException {
		SenderHelper.setWings(this, direction);		
	}

	public void setRotateLeft() throws IBuddyException {
		SenderHelper.setRotateLeft(this);		
	}

	public void setRotateRight() throws IBuddyException {
		SenderHelper.setRotateRight(this);		
	}

	public void setRotateCenter() throws IBuddyException {
		SenderHelper.setRotateCenter(this);		
	}

	public void setRotate(Direction direction) throws IBuddyException {
		SenderHelper.setRotate(this, direction);		
	}

	public void off() throws IBuddyException {
		SenderHelper.off(this);		
	}

	public void blink(Color color, long onTime, long offTime, int times) throws IBuddyException {
		SenderHelper.blink(this, color, (int) onTime, (int) offTime, times);
	}

	public void nudge(long delay, int times) throws IBuddyException {
		SenderHelper.nudge(this, (int) delay, (int) times);		
	}

	public void flap(long delay, int times) throws IBuddyException {
		SenderHelper.flap(this, (int) delay, times);		
	}
}
