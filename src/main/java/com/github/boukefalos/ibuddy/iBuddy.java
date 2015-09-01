package com.github.boukefalos.ibuddy;

import org.jraf.jlibibuddy.IBuddyException;

import proto.Ibuddy.Color;
import proto.Ibuddy.Direction;
import base.Control;

public interface iBuddy extends Control {
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
    public void blink(Color color, int onTime, int offTime, int times) throws IBuddyException;
    public void nudge(int delay, int times) throws IBuddyException;
    public void flap(int delay, int times) throws IBuddyException;
}
