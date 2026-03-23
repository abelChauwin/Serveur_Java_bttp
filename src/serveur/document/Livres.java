package serveur.document;


import serveur.Abonne;


public class Livres extends DocumentEmpruntable {

    private final int nbPages;

    public Livres(String id, String titre, int nbPages) {
        super(id,titre);
        this.nbPages = nbPages;
    }


    @Override
    public void verificationAbonner(Abonne ab) {
        //pas de verification en plus pour les livres
    }
}
