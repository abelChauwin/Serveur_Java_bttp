package serveur;

import serveur.service.ServiceEmprunt;
import serveur.service.ServiceReserv;
import serveur.service.ServiceRetour;

import java.io.IOException;
import java.util.Objects;

public class Main {

	private final static int PORT_RESERV = 2000;
	private final static int PORT_EMPRUNT = 2001;
	private final static int PORT_RETOUR = 2002;

	public static void main(String[] args) {

		BaseDeDonne element = new BaseDeDonne();


		try {
			//Si l'usager est sur une version externe de l'appli, on peut imaginer que le port est automatiquement saisie et redirigier vers le service reservation
			if(Objects.equals(args[1], String.valueOf(PORT_RESERV))){
				new Thread(new ServeurBrette(ServiceReserv.class, PORT_RESERV, element)).start();
				System.out.println("Serveur reservation demarre sur le port " + PORT_RESERV);
			}

			//partie médiathèque

			new Thread(new ServeurBrette(ServiceReserv.class, PORT_RESERV, element)).start();
			System.out.println("Serveur reservation demarre sur le port " + PORT_RESERV);

			new Thread(new ServeurBrette(ServiceEmprunt.class, PORT_EMPRUNT, element)).start();
			System.out.println("Serveur emprunt demarre sur le port " + PORT_EMPRUNT);

			new Thread(new ServeurBrette(ServiceRetour.class, PORT_RETOUR, element)).start();
			System.out.println("Serveur retour demarre sur le port " + PORT_RETOUR);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}