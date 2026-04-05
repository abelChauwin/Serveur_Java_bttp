package serveur;

import serveur.document.DVDS;
import serveur.document.Document;
import serveur.document.Livres;

import java.util.HashMap;

public class BaseDeDonne {
    // class presente pour charger les abonner et les document

    private final HashMap<Integer,Abonne> abonnes;
    private final HashMap<String, Document> documents;


    public void loadAbonne(){ //dans une vrai app, on lirait la BD ici
        addAbonne(new Abonne(1,"ab1","01-01-2001"));
        addAbonne(new Abonne(2,"ab2","02-02-2002"));
        addAbonne(new Abonne(3,"ab3","03-03-2023"));
    }
    public void loadDocument(){
        addDocument(new DVDS("1","DVDenfant",false));
        addDocument(new Livres("2","livre1",1));
        addDocument(new DVDS("3","DVDadulte",true));
    }

    public BaseDeDonne(){
        abonnes = new HashMap<>();
        documents = new HashMap<>();
        loadAbonne();
        loadDocument();
    }

    public void addAbonne(Abonne abonne){abonnes.put(abonne.getId(),abonne);}
    public void addDocument(Document document){documents.put(document.idDoc(),document);}

    public Abonne getAbonne(int id){ return abonnes.get(id);}
    public Document getDocument(String id){ return documents.get(id);}

}
