package com.github.boukefalos.ibuddy;

import org.jraf.jlibibuddy.IBuddyException;

import com.github.boukefalos.ibuddy.exception.iBuddyException;

public interface iBuddy {
	public void setHeadRed(boolean headRed) throws iBuddyException;
	public void setHeadGreen(boolean headGreen) throws iBuddyException;
	public void setHeadBlue(boolean headBlue) throws iBuddyException;
	public void test() throws IBuddyException;
}
