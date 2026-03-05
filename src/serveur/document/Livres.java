package serveur.document;


import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

public class Livres extends DocumentEmpruntable {

    private int nbPages;

    public Livres(String id,int String, String titre, int nbPages) {
        super(id,titre);
        this.nbPages = nbPages;
    }


    @Override
    public void verificationAbonner(Abonne ab) {
        //pas de verification en plus pour les livres
    }
}
