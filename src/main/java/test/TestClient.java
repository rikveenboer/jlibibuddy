package test;
import com.github.boukefalos.ibuddy.Loader;

public class TestClient {
	public static void main(String[] args) {
		try {
			Loader.getiBuddy().sendHeadGreen(true);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
