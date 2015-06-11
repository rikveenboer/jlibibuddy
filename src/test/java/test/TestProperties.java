package test;

import com.github.boukefalos.ibuddy.Loader;

public class TestProperties {
	public static void main(String[] args) {
		try {
			Loader.getLoader().getServer().start();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
