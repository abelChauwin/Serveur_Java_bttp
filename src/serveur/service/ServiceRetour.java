package serveur.service;

import serveur.LoadElement;
import serveur.document.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceRetour extends Service {

	public ServiceRetour(Socket s, LoadElement element) {
		super(s, element);
	}

	public void run() {

		try {
			BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

			sout.println("Bienvenue sur le service de retour");
			sout.println("Format: ID_document");

			while (true) {

				String line = sin.readLine();

				if (line == null || line.isBlank()) {
					socket.close();
					System.out.println("Connexion fermée");
					break;
				}

				String idDoc = line.trim();

				try {
					Document doc = element.getDocument(idDoc);

					if (doc == null) {
						sout.println("Document introuvable");
						continue;
					}

					doc.retour();
					sout.println("Retour effectué avec succès");

				} catch (Exception e) {
					sout.println("Erreur : " + e.getMessage());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}