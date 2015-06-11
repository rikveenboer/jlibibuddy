package test;
import org.jraf.jlibibuddy.IBuddy;
import org.jraf.jlibibuddy.IBuddy.Color;
import org.jraf.jlibibuddy.IBuddyException;
import org.jraf.jlibibuddy.IBuddyUtils;

public class TestOriginal {
	public static void main(String[] args) {
		IBuddy iBuddy = IBuddy.getIBuddy();
        try {
        	while (true) {
	        	iBuddy.sendAllOff();
	            for (Color color : IBuddy.Color.values()) {
	            	iBuddy.sendHeart(false);
	    			iBuddy.sendHeadColor(color);
	            	Thread.sleep(100);
	            	iBuddy.sendHeart(true);
	            	Thread.sleep(500);
	            }
	            iBuddy.sendAllOff();
            	Thread.sleep(2000);
                IBuddyUtils.flap(iBuddy, 400,  6);
        	}
		} catch (IBuddyException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}