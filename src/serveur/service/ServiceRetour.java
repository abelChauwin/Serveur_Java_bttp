package serveur.service;

import serveur.BaseDeDonne;
import serveur.document.IDocument;
import serveur.document.exeption.RetourException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceRetour extends Service {

	public ServiceRetour(Socket s, BaseDeDonne element) {
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
					IDocument doc = element.getDocument(idDoc);

					if (doc == null) {
						sout.println("Document introuvable");
						continue;
					}

					doc.retour();
					sout.println("Retour effectué avec succès");

				} catch (RetourException e) {
					sout.println("Erreur : " + e.getMessage());
				}catch (NumberFormatException e) { sout.println("format des id invalide"); }
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}