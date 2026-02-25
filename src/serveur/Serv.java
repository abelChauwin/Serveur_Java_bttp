package serveur;

import java.io.IOException;

public class Serv {
	
	private final static int PORT = 1234;

	public static void main(String[] args) {
		try {
			new Thread(new ServeurBrette(ServiceBrette.class, PORT)).start();
			System.out.println("Serveur demarre sur le port " + PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
