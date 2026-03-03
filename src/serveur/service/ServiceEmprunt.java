package serveur.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceEmprunt extends Service {

	private StringBuffer txt;

	public ServiceEmprunt(Socket s){
		super(s);
	}

	public void run ( ) {

		try {
			BufferedReader sin = new BufferedReader (new InputStreamReader(socket.getInputStream ( )));
			PrintWriter sout = new PrintWriter (socket.getOutputStream ( ), true);

			txt = new StringBuffer("Tapez une chaîne de caractères\npetit retour a la ligne pour le flex");

			while(true) {

				sout.println(txt.toString().replace("\n", "##"));
				StringBuffer line = new StringBuffer(sin.readLine( ));

				if (line.toString().isBlank()) {
					this.socket.close();
					System.out.println("On raccroche avec le client\n");
					break;
				}
				// ici effectuer la tache du service
				txt = line.reverse();


			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
