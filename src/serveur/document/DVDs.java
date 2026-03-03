package serveur.document;


import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

public class DVDs implements Document {
    private int id;
    private String titre;
    private Boolean adulte;

    public DVDs(int id, String titre, Boolean adulte) {
        this.id = id;
        this.titre = titre;
        this.adulte = adulte;
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
