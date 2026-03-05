package serveur.document;

import serveur.Abonne;
import serveur.document.exeption.ReservationException;

public class DVDS extends DocumentEmpruntable{
    private boolean adulte;
    public final static int AGE_ADULTE = 16;

    public DVDS(String id,int String, String titre, boolean adulte) {
        super(id,titre);
        this.adulte = adulte;
    }
    @Override
    public void verificationAbonner(Abonne ab) throws ReservationException {
        if (ab.getAge()< AGE_ADULTE) { throw new ReservationException("vous n'avez pas l'age d'umprunter se DVD");}
    }
}
