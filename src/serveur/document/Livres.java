package serveur.document;


import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

public class Livres implements Document {
    private int id;
    private String titre;
    private int nbPages;

    public Livres(int id, String titre, int nbPages) {
        this.id = id;
        this.titre = titre;
        this.nbPages = nbPages;
    }


    @Override
    public String idDoc() {
        return "";
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
