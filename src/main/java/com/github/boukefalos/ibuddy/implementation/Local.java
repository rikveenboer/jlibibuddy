package com.github.boukefalos.ibuddy.implementation;

import org.jraf.jlibibuddy.IBuddy;
import org.jraf.jlibibuddy.IBuddyException;

import com.github.boukefalos.ibuddy.iBuddy;
import com.github.boukefalos.ibuddy.exception.iBuddyException;

public class Local implements iBuddy {
	IBuddy IBuddy;

	@SuppressWarnings("static-access")
	public Local() {
		IBuddy = IBuddy.getIBuddy();
	}

	public void sendHeadRed(boolean headRed) throws iBuddyException {
		try {
			IBuddy.sendHeadRed(headRed);
		} catch (IBuddyException e) {
			throw new iBuddyException();
		}
	}

	public void setHeadGreen(boolean headGreen) throws iBuddyException {
		try {
			IBuddy.sendHeadGreen(headGreen);
		} catch (IBuddyException e) {
			throw new iBuddyException();
		}
		
	}

	public void setHeadBlue(boolean headBlue) throws iBuddyException {
		try {
			IBuddy.sendHeadBlue(headBlue);
		} catch (IBuddyException e) {
			throw new iBuddyException();
		}
		
	}

	public void setHeadRed(boolean headRed) throws iBuddyException {
		try {
			IBuddy.sendHeadRed(headRed);
		} catch (IBuddyException e) {
			throw new iBuddyException();
		}
		
	}

	public void test() throws IBuddyException {
		IBuddy.sendAllOff();		
	}
}
