package serveur.service;

import serveur.LoadElement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceEmprunt extends Service {

	private StringBuffer txt;
	private StringBuffer consigne;

	public ServiceEmprunt(Socket s, LoadElement element) {
		super(s,element);
	}
	public ServiceEmprunt(Socket s) {
		super(s);
	}

	public void run ( ) {

		try {
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);

			txt = new StringBuffer("Bienvenue sur la Borne d'emprunt\nvous pouvez emprunter un document reserver au prealable\n");
			consigne = new StringBuffer("tapez: ID_document ID_Abonne\"");
			sout.println(txt.toString().replace("\n", "##"));
			sout.println(consigne.toString().replace("\n", "##"));
			while(true) {
				StringBuffer line = new StringBuffer(sin.readLine( ));
				sout.println(consigne.toString().replace("\n", "##"));

				if (line.toString().isBlank()) {
					this.socket.close();
					System.out.println("Deconnexion avec le client\n");
					break;
				}
				//TODO: ici effectuer la tache du service
				String[] id = line.toString().split(" ");
				emprunt(id[0],id[1]);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void emprunt (String idDocument,String idAbonne) {
		int idDoc = Integer.parseInt(idDocument);
		int idAb = Integer.parseInt(idAbonne);

		//TODO: faire la fonction emprunter


	}

}
