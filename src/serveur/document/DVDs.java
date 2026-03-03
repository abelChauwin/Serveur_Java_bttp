package serveur.document;


import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

public class DVDs implements Document {
    private String id;
    private String titre;
    private Boolean adulte;

    public DVDs(String id, String titre, Boolean adulte) {
        this.id = id;
        this.titre = titre;
        this.adulte = adulte;
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
