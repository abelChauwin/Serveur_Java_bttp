package serveur.document;


import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

public class Livres implements Document {
    private String id;
    private String titre;
    private int nbPages;

    public Livres(int String, String titre, int nbPages) {
        this.id = id;
        this.titre = titre;
        this.nbPages = nbPages;
    }


    @Override
    public String idDoc() {
        return id;
    }

    @Override
    public void reservation(Abonne ab) throws ReservationException {

    }

    @Override
    public void emprunt(Abonne ab) throws EmpruntException {

    }

    @Override
    public void retour() throws RetourException {

    }
}
