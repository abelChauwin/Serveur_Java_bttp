package serveur.service;

import serveur.Abonne;
import serveur.BaseDeDonne;
import serveur.document.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceReserv extends Service {

	public ServiceReserv(Socket s, BaseDeDonne element) {
		super(s, element);
	}

	public void run() {

		try {
			BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

			sout.println("Bienvenue sur le service de réservation");
			sout.println("Format: ID_document ID_abonne");

			while (true) {

				String line = sin.readLine();

				if (line == null || line.isBlank()) {
					socket.close();
					System.out.println("Connexion fermée");
					break;
				}

				String[] data = line.split(" ");

				if (data.length < 2) {
					sout.println("Format invalide");
					continue;
				}

				String idDoc = data[0];
				int idAbonne = Integer.parseInt(data[1]);

				try {
					Document doc = element.getDocument(idDoc);
					Abonne ab = element.getAbonne(idAbonne);

					if (doc == null) {
						sout.println("Document introuvable");
						continue;
					}

					if (ab == null) {
						sout.println("Abonné introuvable");
						continue;
					}

					doc.reservation(ab);
					sout.println("Réservation réussie");

				} catch (Exception e) {
					sout.println("Erreur : " + e.getMessage());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}