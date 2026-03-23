package serveur;

import serveur.document.DVDS;
import serveur.document.Document;
import serveur.document.Livres;

import java.util.HashMap;

public class BaseDeDonne {
    // class presente pour charger les abonner et les document

    private HashMap<Integer,Abonne> abonnes;
    private HashMap<String,Document> documents;


    public void loadAbonne(){ //dans une vrai app, on lirait la BD ici
        abonnes.put(1,new Abonne(1,"ab1","01-01-2001"));
        abonnes.put(2,new Abonne(2,"ab2","02-02-2002"));
        abonnes.put(3,new Abonne(3,"ab3","03-03-2023"));
    }
    public void loadDocument(){
        documents.put("1",new DVDS("1","DVDenfant",false));
        documents.put("2",new Livres("2","livre1",1));
        documents.put("3",new DVDS("3","DVDadulte",true));
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
