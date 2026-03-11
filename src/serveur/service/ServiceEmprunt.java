package serveur.service;

import serveur.LoadElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceEmprunt extends Service {

	private StringBuffer txt;

	public ServiceEmprunt(Socket s, LoadElement element) {
		super(s,element);
	}

	public void run ( ) {

		try {
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);

			txt = new StringBuffer("Bienvenue sur la Borne d'emprunt\nvous pouvez emprunter un document reserver au prealable\npour ce faire tapez: ID_document ID_Abonne");

			while(true) {

				sout.println(txt.toString().replace("\n", "##"));
				StringBuffer line = new StringBuffer(sin.readLine( ));

				if (line.toString().isBlank()) {
					this.socket.close();
					System.out.println("On raccroche avec le client\n");
					break;
				}
				// ici effectuer la tache du service



			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
