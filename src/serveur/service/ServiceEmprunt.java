package serveur.service;

import serveur.Abonne;
import serveur.BaseDeDonne;
import serveur.document.IDocument;
import serveur.document.exeption.EmpruntException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceEmprunt extends Service {

	 StringBuffer txt;
	 StringBuffer consigne;

	public ServiceEmprunt(Socket s, BaseDeDonne element) {
		super(s, element);
	}

	public ServiceEmprunt(Socket s) {
		super(s);
	}

	public void run() {

		try {
			BufferedReader sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter sout = new PrintWriter(socket.getOutputStream(), true);

			txt = new StringBuffer("Bienvenue sur la Borne d'emprunt\nvous pouvez emprunter un document reservé\n");
			consigne = new StringBuffer("tapez: ID_document ID_Abonne");

			sout.println(txt.toString().replace("\n", "##"));

			while (true) {

				sout.println(consigne.toString().replace("\n", "##"));
				String line = sin.readLine();

				if (line == null || line.isBlank()) {
					socket.close();
					System.out.println("Deconnexion client");
					break;
				}

				String[] id = line.split(" ");
				if (id.length < 2) {
					sout.println("Format invalide");
					continue;
				}

				String result = emprunt(id[0], id[1]);
				sout.println(result);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String emprunt(String idDocument, String idAbonne) {

		try {
			IDocument doc = element.getDocument(idDocument);
			Abonne ab = element.getAbonne(Integer.parseInt(idAbonne));

			if (doc == null) return "Document introuvable";
			if (ab == null) return "Abonné introuvable";

			doc.emprunt(ab);
			return "Emprunt réussi";

		} catch (EmpruntException e) {
			return "Erreur : " + e.getMessage();
		}catch (NumberFormatException e) { return "format des id invalide"; }
	}
}