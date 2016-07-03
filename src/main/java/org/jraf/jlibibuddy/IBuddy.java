/*
This source is part of the
     _____  ___   ____
 __ / / _ \/ _ | / __/___  _______ _
/ // / , _/ __ |/ _/_/ _ \/ __/ _ `/
\___/_/|_/_/ |_/_/ (_)___/_/  \_, /
                             /___/
repository. It is in the public domain.
Contact BoD@JRAF.org for more information.

$Id: IBuddy.java 265 2008-09-07 14:37:02Z bod $
*/
package org.jraf.jlibibuddy;

import ch.ntb.usb.Device;
import ch.ntb.usb.USB;
import ch.ntb.usb.USBException;

/**
 * Main entry point to i-Buddy commanding. Note: currently, one and only one i-Buddy can be manipulated with this
 * library. Therefore, this class should be used as a singleton, even if this not enforced by the code.
 */
public class IBuddy {
    /**
     * Usb device vendor id.
     */
    private static final short DEVICE_VENDOR = 0x1130;

    /**
     * Usb device product id.
     */
    private static final short DEVICE_PRODUCT = 0x0001;

    /**
     * Usb init command.
     */
    private static final byte[] INIT = new byte[]{0x22, 0x09, 0x00, 0x02, 0x01, 0x00, 0x00, 0x00};

    /**
     * Usb command header.
     */
    private static final byte[] COMMAND_HEADER = new byte[]{0x55, 0x53, 0x42, 0x43, 0x00, 0x40, 0x02};

    /**
     * Command to turn everything off on the i-Buddy.
     */
    private static final byte ALL_OFF = (byte) 0xff;

    /**
     * Bit controlling the heart LED (0 is ON, 1 is OFF).
     */
    private static final byte HEART = (byte) 0x80;

    /**
     * Bit controlling the head blue LED (0 is ON, 1 is OFF).
     */
    private static final byte HEAD_BLUE = 0x40;

    /**
     * Bit controlling the head green LED (0 is ON, 1 is OFF).
     */
    private static final byte HEAD_GREEN = 0x20;

    /**
     * Bit controlling the head red LED (0 is ON, 1 is OFF).
     */
    private static final byte HEAD_RED = 0x10;

    /**
     * Bit controlling the wings down state (0 is not down, 1 is down). Should not be flipped to 1 if {@link #WINGS_UP}
     * is also 1.
     */
    private static final byte WINGS_DOWN = 0x08;

    /**
     * Bit controlling the wings up state (0 is not up, 1 is up). Should not be flipped to 1 if {@link #WINGS_DOWN} is
     * also 1.
     */
    private static final byte WINGS_UP = 0x04;

    /**
     * Bit controlling the rotate right state (0 is do not rotate right, 1 rotate right). Should not be flipped to 1 if
     * {@link #ROTATE_LEFT} is also 1.
     */
    private static final byte ROTATE_RIGHT = 0x02;

    /**
     * Bit controlling the rotate left state (0 is do not rotate left, 1 rotate left). Should not be flipped to 1 if
     * {@link #ROTATE_RIGHT} is also 1.
     */
    private static final byte ROTATE_LEFT = 0x01;

    /**
     * Different colors for the head (all possible combinations of the blue, green and red LEDs).
     */
    public enum Color {
        OFF,
        RED,
        GREEN,
        BLUE,
        YELLOW,
        PURPLE,
        CYAN,
        WHITE
    }

    /**
     * Different wing positions.
     */
    public enum Wings {
        AT_EASE,
        DOWN,
        UP
    }

    /**
     * Different rotation states.
     */
    public enum Rotate {
        AT_EASE,
        LEFT,
        RIGHT
    }

    private Device device;

    /*
     * i-Buddy's current state.
     */
    private boolean heart;
    private boolean headBlue;
    private boolean headGreen;
    private boolean headRed;
    private boolean wingsDown;
    private boolean wingsUp;
    private boolean rotateRight;
    private boolean rotateLeft;

    private IBuddy() {
    }

    /**
     * Gets the i-Buddy.
     * @return the i-Buddy.
     */
    public static IBuddy getIBuddy() {
        return new IBuddy();
    }

    private static byte[] getCommandMessage(byte command) {
        byte[] res = new byte[COMMAND_HEADER.length + 1];
        System.arraycopy(COMMAND_HEADER, 0, res, 0, COMMAND_HEADER.length);
        res[COMMAND_HEADER.length] = command;
        return res;
    }

    private static byte getCommand(boolean heart, boolean headBlue, boolean headGreen, boolean headRed, boolean wingsDown, boolean wingsUp, boolean rotateRight, boolean rotateLeft) {
        byte res = (byte) 0xF0;
        if (heart) {
            res ^= HEART;
        } else {
            res |= HEART;
        }
        if (headBlue) {
            res ^= HEAD_BLUE;
        } else {
            res |= HEAD_BLUE;
        }
        if (headGreen) {
            res ^= HEAD_GREEN;
        } else {
            res |= HEAD_GREEN;
        }
        if (headRed) {
            res ^= HEAD_RED;
        } else {
            res |= HEAD_RED;
        }
        if (wingsDown) {
            res |= WINGS_DOWN;
        } else {
            res &= ~WINGS_DOWN;
        }
        if (wingsUp) {
            res |= WINGS_UP;
        } else {
            res &= ~WINGS_UP;
        }
        if (rotateRight) {
            res |= ROTATE_RIGHT;
        } else {
            res &= ~ROTATE_RIGHT;
        }
        if (rotateLeft) {
            res |= ROTATE_LEFT;
        } else {
            res &= ~ROTATE_LEFT;
        }
        return res;
    }

    /**
     * Main low level send method.
     *
     * @param command the command byte.
     */
    private synchronized void sendMessage(byte command) throws IBuddyException {
        open();
        try {
            device.controlMsg(USB.REQ_TYPE_TYPE_CLASS | USB.REQ_TYPE_RECIP_INTERFACE, USB.REQ_SET_CONFIGURATION, 0x02, 0x01, INIT, INIT.length, 100, true);
        } catch (USBException e) {
            close();
            throw new IBuddyException("Could not send message to i-Buddy", e);
        }
        byte[] commandMessage = getCommandMessage(command);
        try {
            device.controlMsg(USB.REQ_TYPE_TYPE_CLASS | USB.REQ_TYPE_RECIP_INTERFACE, USB.REQ_SET_CONFIGURATION, 0x02, 0x01, commandMessage, commandMessage.length, 100, true);
        } catch (USBException e) {
            close();
            throw new IBuddyException("Could not send message to i-Buddy", e);
        }
        close();
    }

    /**
     * General purpose send state method.
     *
     * @param heart {@code true} to turn the heart LED on, {@code false} to turn it off.
     * @param headBlue {@code true} to turn the head blue LED on, {@code false} to turn it off.
     * @param headGreen {@code true} to turn the head green LED on, {@code false} to turn it off.
     * @param headRed {@code true} to turn the head red LED on, {@code false} to turn it off.
     * @param wingsDown {@code true} to turn the "wings down" state on, {@code false} to turn it off.
     * @param wingsUp {@code true} to turn the "wings up" state on, {@code false} to turn it off.
     * @param rotateRight {@code true} to turn the "rotate right" state on, {@code false} to turn it off.
     * @param rotateLeft {@code true} to turn the "rotate left" state on, {@code false} to turn it off.
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     * @throws IllegalArgumentException if {@code wingsUp} and {@code wingsDown} are both {@code true}, or if {@code
     * rotateRight} and {@code rotateLeft} are both {@code true}.
     */
    public void sendState(boolean heart, boolean headBlue, boolean headGreen, boolean headRed, boolean wingsDown, boolean wingsUp, boolean rotateRight, boolean rotateLeft) throws IBuddyException {
        if (wingsUp && wingsDown) {
            throw new IllegalArgumentException("wingsUp and wingsDown cannot be both true at the same time");
        }
        if (rotateRight && rotateLeft) {
            throw new IllegalArgumentException("rotateRight and rotateLeft cannot be both true at the same time");
        }

        this.heart = heart;
        this.headBlue = headBlue;
        this.headGreen = headGreen;
        this.headRed = headRed;
        this.wingsDown = wingsDown;
        this.wingsUp = wingsUp;
        this.rotateRight = rotateRight;
        this.rotateLeft = rotateLeft;
        sendMessage(getCommand(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, rotateLeft));
    }

    /**
     * Turns everything off.
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendAllOff() throws IBuddyException {
        heart = false;
        headBlue = false;
        headGreen = false;
        headRed = false;
        wingsDown = false;
        wingsUp = false;
        rotateRight = false;
        rotateLeft = false;
        sendMessage(ALL_OFF);
    }

    /**
     * Turns the heart LED on or off.
     *
     * @param heart whether to turn the heart LED on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendHeart(boolean heart) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, rotateLeft);
    }

    /**
     * Turns the head blue LED on or off.
     *
     * @param headBlue whether to turn the head blue LED on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendHeadBlue(boolean headBlue) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, rotateLeft);
    }

    /**
     * Turns the head green LED on or off.
     *
     * @param headGreen whether to turn the head green LED on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendHeadGreen(boolean headGreen) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, rotateLeft);
    }

    /**
     * Turns the head red LED on or off.
     *
     * @param headRed whether to turn the head red LED on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendHeadRed(boolean headRed) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, rotateLeft);
    }

    /**
     * Turns the head LEDs on or off.
     *
     * @param headBlue whether to turn the head blue LED on ({@code true}) or off ({@code false}).
     * @param headGreen whether to turn the head green LED on ({@code true}) or off ({@code false}).
     * @param headRed whether to turn the head red LED on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendHeadColor(boolean headBlue, boolean headGreen, boolean headRed) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, rotateLeft);
    }

    /**
     * Sets a color on the head by turning the LEDs on or off.
     *
     * @param color the color to set on the head.
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendHeadColor(Color color) throws IBuddyException {
        switch (color) {
            case OFF:
                sendHeadColor(false, false, false);
                break;
            case RED:
                sendHeadColor(false, false, true);
                break;
            case GREEN:
                sendHeadColor(false, true, false);
                break;
            case BLUE:
                sendHeadColor(true, false, false);
                break;
            case YELLOW:
                sendHeadColor(false, true, true);
                break;
            case PURPLE:
                sendHeadColor(true, false, true);
                break;
            case CYAN:
                sendHeadColor(true, true, false);
                break;
            case WHITE:
                sendHeadColor(true, true, true);
                break;
        }
    }

    /**
     * Turns the "wings down" state on or off. This also automatically turns the "wings up" state to the opposite
     * value.
     *
     * @param wingsDown whether to turn the "wings down" on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendWingsDown(boolean wingsDown) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, wingsDown, !wingsDown, rotateRight, rotateLeft);
    }

    /**
     * Turns the "wings up" state on or off. This also automatically turns the "wings down" state to the opposite
     * value.
     *
     * @param wingsUp whether to turn the "wings up" on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendWingsUp(boolean wingsUp) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, !wingsUp, wingsUp, rotateRight, rotateLeft);
    }

    /**
     * Turns the "wings up" and "wings down" states on or off.
     *
     * @param wingsUp whether to turn the "wings up" on ({@code true}) or off ({@code false}).
     * @param wingsDown whether to turn the "wings down" on ({@code true}) or off ({@code false}).
     *
     * @throws IllegalArgumentException if {@code wingsUp} and {@code wingsDown} are both {@code true}.
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendWings(boolean wingsDown, boolean wingsUp) throws IBuddyException {
        if (wingsDown && wingsUp) {
            throw new IllegalArgumentException("wingsDown and wingsUp cannot be both true at the same time");
        }
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, rotateLeft);
    }

    /**
     * Sets the wings state.
     *
     * @param wings the wings state.
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendWings(Wings wings) throws IBuddyException {
        switch (wings) {
            case AT_EASE:
                sendWings(false, false);
                break;
            case DOWN:
                sendWings(true, false);
                break;
            case UP:
                sendWings(false, true);
                break;
        }
    }

    /**
     * Turns the "rotate right" state on or off. This also automatically turns the "rotate left" state to the opposite
     * value.
     *
     * @param rotateRight whether to turn the "rotate right" on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendRotateRight(boolean rotateRight) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, !rotateRight);
    }

    /**
     * Turns the "rotate left" state on or off. This also automatically turns the "rotate right" state to the opposite
     * value.
     *
     * @param rotateLeft whether to turn the "rotate left" on ({@code true}) or off ({@code false}).
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendRotateLeft(boolean rotateLeft) throws IBuddyException {
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateLeft, !rotateLeft);
    }

    /**
     * Turns the "rotate right" and "rotate left" states on or off.
     *
     * @param rotateRight whether to turn the "rotate right" on ({@code true}) or off ({@code false}).
     * @param rotateLeft whether to turn the "rotate left" on ({@code true}) or off ({@code false}).
     *
     * @throws IllegalArgumentException if {@code rotateRight} and {@code rotateLeft} are both {@code true}.
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendRotate(boolean rotateRight, boolean rotateLeft) throws IBuddyException {
        if (rotateRight && rotateLeft) {
            throw new IllegalArgumentException("rotateRight and rotateLeft cannot be both true at the same time");
        }
        sendState(heart, headBlue, headGreen, headRed, wingsDown, wingsUp, rotateRight, rotateLeft);
    }

    /**
     * Sets the rotate state.
     *
     * @param rotate the rotate state.
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public void sendRotate(Rotate rotate) throws IBuddyException {
        switch (rotate) {
            case AT_EASE:
                sendRotate(false, false);
                break;
            case LEFT:
                sendRotate(false, true);
                break;
            case RIGHT:
                sendRotate(true, false);
                break;
        }
    }

    /**
     * Opens the usb connection to the i-Buddy and initializes its state.
     *
     * @throws IBuddyException in case of problem while opening the usb device or if the i-Buddy was already open.
     */
    private synchronized void open() throws IBuddyException {
        device = USB.getDevice(DEVICE_VENDOR, DEVICE_PRODUCT);
        try {
            device.open(1, 1, 0);
        } catch (USBException e) {
            throw new IBuddyException("Could not open i-Buddy", e);
        }
    }

    /**
     * Closes the usb connection to the i-Buddy.
     *
     * @throws IBuddyException in case of problem while closing the usb device.
     */
    private synchronized void close() throws IBuddyException {
        try {
            device.close();
        } catch (USBException e) {
            throw new IBuddyException("Could not close i-Buddy", e);
        }
    }
}