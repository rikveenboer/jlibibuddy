/*
This source is part of the
     _____  ___   ____
 __ / / _ \/ _ | / __/___  _______ _
/ // / , _/ __ |/ _/_/ _ \/ __/ _ `/
\___/_/|_/_/ |_/_/ (_)___/_/  \_, /
                             /___/
repository. It is in the public domain.
Contact BoD@JRAF.org for more information.

$Id: IBuddyUtils.java 265 2008-09-07 14:37:02Z bod $
*/
package org.jraf.jlibibuddy;

/**
 * Utility class providing high-level commands for the i-Buddy.
 */
public class IBuddyUtils {
    private IBuddyUtils() {
        // suppress default constructor for noninstanciability
        throw new UnsupportedOperationException("Utility class should not be instantieded");
    }

    /**
     * Makes the current thread sleep a number of milliseconds.
     * This simply calls {@link Thread#sleep(long)} and ignores the {@link InterruptedException} if it is thrown.
     * @param ms the number of milliseconds to sleep.
     */
    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // ignore if happens
        }
    }

    /**
     * Makes the i-Buddy's head blink.
     *
     * @param iBuddy the {@link IBuddy} object.
     * @param color the color to use.
     * @param onTime the duration in milliseconds to wait after turning the head on.
     * @param offTime the duration in milliseconds to wait after turning the head off.
     * @param times number of times to blink.
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public static void blink(IBuddy iBuddy, IBuddy.Color color, long onTime, long offTime, int times) throws IBuddyException {
        for (int i = 0; i < times; i++) {
            iBuddy.sendHeadColor(color);
            sleep(onTime);
            iBuddy.sendHeadColor(IBuddy.Color.OFF);
            if (i != times - 1) {
                sleep(offTime);
            }
        }
    }

    /**
     * Makes the i-Buddy nudge (rotate left, then right).
     *
     * @param iBuddy the {@link IBuddy} object.
     * @param delay the delay in milliseconds to use between rotating left and right.
     * @param times the number of times to nudge.
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public static void nudge(IBuddy iBuddy, long delay, int times) throws IBuddyException {
        for (int i = 0; i < times; i++) {
            iBuddy.sendRotate(IBuddy.Rotate.LEFT);
            sleep(delay);
            iBuddy.sendRotate(IBuddy.Rotate.RIGHT);
            sleep(delay);
        }
        iBuddy.sendRotate(IBuddy.Rotate.AT_EASE);
    }

    /**
     * Makes the i-Buddy flap its wings (move up then down).
     *
     * @param iBuddy the {@link IBuddy} object.
     * @param delay the delay in milliseconds to use between moving the wings up and down.
     * @param times the number of times to flap.
     *
     * @throws IBuddyException in case of problem while sending the usb command.
     */
    public static void flap(IBuddy iBuddy, long delay, int times) throws IBuddyException {
        for (int i = 0; i < times; i++) {
            iBuddy.sendWings(IBuddy.Wings.DOWN);
            sleep(delay);
            iBuddy.sendWings(IBuddy.Wings.UP);
            sleep(delay);
        }
        iBuddy.sendWings(IBuddy.Wings.AT_EASE);
    }
}
