package serveur;

import serveur.document.DVDS;
import serveur.document.Document;
import serveur.document.Livres;

import java.time.chrono.HijrahDate;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseDeDonne {
    // class presente pour charger les abonner et les document

    private HashMap<Integer,Abonne> abonnes;
    private HashMap<String,Document> documents;


    public static ArrayList<Abonne> loadAbonne(){
        ArrayList<Abonne> tab = new ArrayList<>();

        tab.add(new Abonne(1,"ab1","01-01-2001"));
        tab.add(new Abonne(1,"ab2","02-02-2002"));
        tab.add(new Abonne(1,"ab3","03-03-2023"));

        return tab;
    }
    public static ArrayList<Document> loadDocument(){
        ArrayList<Document> tab = new ArrayList<>();

        tab.add(new DVDS("1","DVDenfant",false));
        tab.add(new Livres("2","livre1",1));
        tab.add(new DVDS("3","DVDadulte",true));

        return tab;
    }

    public BaseDeDonne(){
        abonnes = new HashMap<>();
        documents = new HashMap<>();
    }

    public void addAbonne(Abonne abonne){abonnes.put(abonne.getId(),abonne);}
    public void addDocument(Document document){documents.put(document.idDoc(),document);}

    public Abonne getAbonne(int id){ return abonnes.get(id);}
    public Document getDocuments(String id){ return documents.get(id);}

}
