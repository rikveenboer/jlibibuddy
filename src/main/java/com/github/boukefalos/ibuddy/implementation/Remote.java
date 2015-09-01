package com.github.boukefalos.ibuddy.implementation;

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
import base.Control;
import base.Sender;

import com.github.boukefalos.ibuddy.iBuddy;

public class Remote implements iBuddy, Control {
    protected final static int BUFFER_SIZE = 1024;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Sender sender;

    public Remote(Sender sender) {
        this.sender = sender;
    }

    public void start() {
        sender.start();        
    }

    public void stop() {
        sender.stop();        
    }

    public void exit() {
        sender.exit();        
    }

    public void setHeart(boolean on) throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.HEART)
                .setHeart(Heart.newBuilder()
                    .setState(mapState(on))).build());        
    }

    public void setHeadRed(boolean on) throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.HEAD)
                .setHead(Head.newBuilder()
                    .setState(mapState(on))
                    .setSingle(true)
                    .setColor(Color.RED)).build());
    }

    public void setHeadBlue(boolean on) throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.HEAD)
                .setHead(Head.newBuilder()
                    .setState(mapState(on))
                    .setSingle(true)
                    .setColor(Color.BLUE)).build());        
    }


    public void setHeadGreen(boolean on) throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.HEAD)
                .setHead(Head.newBuilder()
                    .setState(mapState(on))
                    .setSingle(true)
                    .setColor(Color.GREEN)).build());
    }

    public void setHead(Color color) throws IBuddyException {
        State state = mapState(!color.equals(Color.NONE));
        send(            
            Command.newBuilder()
                .setType(Type.HEAD)
                .setHead(Head.newBuilder()
                    .setState(state)
                    .setSingle(false)
                    .setColor(color)).build());
    }

    public void setWingsUp() throws IBuddyException {
        setWings(Direction.UP);    
    }

    public void setWingsDown() throws IBuddyException {
        setWings(Direction.DOWN);    
    }

    public void setWingsCenter() throws IBuddyException {
        setWings(Direction.CENTER);
    }

    public void setWings(Direction direction) throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.WINGS)
                .setWings(Wings.newBuilder()
                    .setDirection(direction)).build());    
    }

    public void setRotateLeft() throws IBuddyException {
        setRotate(Direction.LEFT);        
    }

    public void setRotateRight() throws IBuddyException {
        setRotate(Direction.RIGHT);        
    }

    public void setRotateCenter() throws IBuddyException {
        setRotate(Direction.CENTER);        
    }

    public void setRotate(Direction direction) throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.ROTATE)
                .setRotate(Rotate.newBuilder()
                    .setDirection(direction)).build());
    }

    public void off() throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.STATE).build());        
    }

    public void blink(Color color, int onTime, int offTime, int times)    throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.BLINK)
                .setBlink(Blink.newBuilder()
                    .setOnTime(onTime)
                    .setOffTime(offTime)
                    .setTimes(times)).build());
    }

    public void nudge(int delay, int times) throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.NUDGE)
                .setNudge(Nudge.newBuilder()
                    .setDelay(delay)
                    .setTimes(times)).build());        
    }

    public void flap(int delay, int times) throws IBuddyException {
        send(            
            Command.newBuilder()
                .setType(Type.FLAP)
                .setFlap(Flap.newBuilder()
                    .setDelay(delay)
                    .setTimes(times)).build());
    }

    protected static State mapState(boolean on) {
        return on ? State.ON : State.OFF;
    }

    protected void send(Command command) {
        ByteArrayOutputStream output = new ByteArrayOutputStream(BUFFER_SIZE);
        try {
            command.writeDelimitedTo(output);
            sender.send(output.toByteArray());
        } catch (IOException e) {
            logger.error("Failed to send command");
        }    
    }
}
