package com.github.boukefalos.ibuddy;


import org.jraf.jlibibuddy.IBuddyException;

import proto.Ibuddy.Color;
import proto.Ibuddy.Direction;

public interface iBuddy {
	public void setHeart(boolean on) throws IBuddyException;
	public void setHeadRed(boolean on) throws IBuddyException;
	public void setHeadBlue(boolean on) throws IBuddyException;
	public void setHeadGreen(boolean on) throws IBuddyException;
	public void setHead(Color color) throws IBuddyException;
	public void setWingsUp() throws IBuddyException;
	public void setWingsDown() throws IBuddyException;
	public void setWingsCenter() throws IBuddyException;
	public void setWings(Direction direction) throws IBuddyException;
	public void setRotateLeft() throws IBuddyException;
	public void setRotateRight() throws IBuddyException;
	public void setRotateCenter() throws IBuddyException;
	public void setRotate(Direction direction) throws IBuddyException;	
	public void off() throws IBuddyException;
	public void blink(Color color, long onTime, long offTime, int times) throws IBuddyException;
	public void nudge(long delay, int times) throws IBuddyException;
	public void flap(long delay, int times) throws IBuddyException;
}
