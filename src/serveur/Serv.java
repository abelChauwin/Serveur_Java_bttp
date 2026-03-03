package serveur;

import serveur.service.ServiceEmprunt;
import serveur.service.ServiceReserv;
import serveur.service.ServiceRetour;

import java.io.IOException;

public class Serv {
	
	private final static int PORT_RESERV = 2000;
	private final static int PORT_EMPRUNT= 2001;
	private final static int PORT_RETOUR= 2002;

	public static void main(String[] args) {
		try {
			new Thread(new ServeurBrette(ServiceReserv.class, PORT_RESERV)).start();
			System.out.println("Serveur reservation demarre sur le port " + PORT_RESERV);

			new Thread(new ServeurBrette(ServiceEmprunt.class, PORT_EMPRUNT)).start();
			System.out.println("Serveur emprunt demarre sur le port " + PORT_EMPRUNT);

			new Thread(new ServeurBrette(ServiceRetour.class, PORT_RETOUR)).start();
			System.out.println("Serveur retour demarre sur le port " + PORT_RETOUR);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
