package serveur;

import serveur.document.Document;
import serveur.document.DVDS;
import serveur.document.Livres;

import java.util.HashMap;
import java.util.Map;

public class LoadElement {

    private Map<String, Document> documents;
    private Map<Integer, Abonne> abonnes;

    public LoadElement() {
        documents = new HashMap<>();
        abonnes = new HashMap<>();
        init();
    }

    private void init() {

        documents.put("1", new Livres("1", "Le Petit Prince", 96));
        documents.put("2", new Livres("2", "1984", 300));
        documents.put("3", new DVDS("3", "Matrix", true));
        documents.put("4", new DVDS("4", "Toy Story", false));
        
        abonnes.put(1, new Abonne(1, "Dupont", "01-01-2000"));
        abonnes.put(2, new Abonne(2, "Martin", "15-06-2012"));
    }

    public Document getDocument(String id) {
        return documents.get(id);
    }

    public Abonne getAbonne(int id) {
        return abonnes.get(id);
    }
}