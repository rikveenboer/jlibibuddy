package com.github.boukefalos.ibuddy;

import com.github.boukefalos.ibuddy.exception.iBuddyException;

public interface iBuddy {
	public void sendHeadRed(boolean headRed) throws iBuddyException;
	public void sendHeadGreen(boolean headGreen) throws iBuddyException;
	public void sendHeadBlue(boolean headBlue) throws iBuddyException;
}
