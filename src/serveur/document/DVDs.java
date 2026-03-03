package serveur.document;


import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

import java.util.Timer;
import java.util.TimerTask;

public class DVDs implements Document {
    private String id;
    private String titre;
    private Boolean adulte;

    private Boolean reserve = false;

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
        if (ab.plusDe16()) {
            throw new ReservationException("L'abonné doit avoir au moins 18 ans pour réserver.");
        }


        if (!reserve) {// rajouter le check de l'age de l'abo
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    synchronized(DVDs.this) {
                        reserve = false; // fin de la reservation
                    }
                }
            };

            timer.schedule(task, 1000 *60*60 * 2); // delay de 2h
        }else{
            throw new ReservationException("La ressource est déjà réservée.");
        }
    }

    @Override
    public void emprunt(Abonne ab) throws EmpruntException {

    }

    @Override
    public void retour() throws RetourException {

    }
}
