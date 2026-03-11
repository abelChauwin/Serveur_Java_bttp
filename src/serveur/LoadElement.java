package serveur;

import serveur.document.DVDS;
import serveur.document.Document;
import serveur.document.Livres;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoadElement {
    // class presente pour charger les abonner et les document

    private ArrayList<Abonne> abonnes;
    private ArrayList<Document> documents;


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

    public  LoadElement(){
        abonnes = LoadElement.loadAbonne();
        documents = LoadElement.loadDocument();
    }

    public ArrayList<Abonne> getAbonne(){ return abonnes;}
    public ArrayList<Document> getDocuments(){ return documents;}

}
