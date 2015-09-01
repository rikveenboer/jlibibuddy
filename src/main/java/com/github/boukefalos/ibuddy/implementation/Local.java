package com.github.boukefalos.ibuddy.implementation;

import org.jraf.jlibibuddy.IBuddy;
import org.jraf.jlibibuddy.IBuddyException;
import org.jraf.jlibibuddy.IBuddyUtils;

import proto.Ibuddy.Color;
import proto.Ibuddy.Direction;

import com.github.boukefalos.ibuddy.iBuddy;

public class Local implements iBuddy {
    IBuddy IBuddy;

    @SuppressWarnings("static-access")
    public Local() {
        IBuddy = IBuddy.getIBuddy();
    }

    public void start() {}
    public void stop() {}
    public void exit() {}

    public void setHeadRed(boolean on) throws IBuddyException {
        IBuddy.sendHeadRed(on);        
    }

    public void setHeadBlue(boolean on) throws IBuddyException {
        IBuddy.sendHeadBlue(on);        
    }

    public void setHeadGreen(boolean on) throws IBuddyException {
        IBuddy.sendHeadGreen(on);        
    }

    public void setHeart(boolean on) throws IBuddyException {
        IBuddy.sendHeart(on);
    }

    public void setHead(Color color) throws IBuddyException {
        IBuddy.sendHeadColor(mapColor(color));
    }

    public void setWingsUp() throws IBuddyException {
        IBuddy.sendWings(false, true);        
    }

    public void setWingsDown() throws IBuddyException {
        IBuddy.sendWings(true, false);        
    }

    public void setWingsCenter() throws IBuddyException {
        IBuddy.sendWings(false, false);        
    }

    public void setWings(Direction direction) throws IBuddyException {
        switch (direction) {
            case UP:
                setWingsUp();
                break;
            case DOWN:
                setWingsDown();
                break;
            default:
                setWingsCenter();
                break;
        }        
    }

    public void setRotateLeft() throws IBuddyException {
        IBuddy.sendRotate(false, true);    
    }


    public void setRotateRight() throws IBuddyException {
        IBuddy.sendRotate(true, false);        
    }

    public void setRotateCenter() throws IBuddyException {
        IBuddy.sendRotate(false, false);        
    }

    public void setRotate(Direction direction) throws IBuddyException {
        switch (direction) {
            case LEFT:
                setRotateLeft();
                break;
            case RIGHT:
                setRotateRight();
            default:
                setRotateCenter();                
        }        
    }

    public void off() throws IBuddyException {
        IBuddy.sendAllOff();        
    }

    public void blink(Color color, int onTime, int offTime, int times) throws IBuddyException {
        IBuddyUtils.blink(IBuddy, mapColor(color), onTime, offTime, times);
    }

    public void nudge(int delay, int times) throws IBuddyException {
        IBuddyUtils.nudge(IBuddy, delay, times);        
    }

    public void flap(int delay, int times) throws IBuddyException {
        IBuddyUtils.flap(IBuddy, delay, times);        
    }

    protected org.jraf.jlibibuddy.IBuddy.Color mapColor(Color color) {
        return color.equals(Color.NONE)
            ? org.jraf.jlibibuddy.IBuddy.Color.OFF
            : org.jraf.jlibibuddy.IBuddy.Color.valueOf(color.name());
    }
}
