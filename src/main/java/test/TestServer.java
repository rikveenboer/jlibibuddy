package test;
import com.github.boukefalos.ibuddy.Loader;

public class TestServer {
	public static void main(String[] args) {
		try {
			Loader.getServer().start();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
