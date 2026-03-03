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
    private boolean adulte;

    public enum EtatReservation {
        RESERVE,                  // La réservation est active
        EN_ATTENTE_FIN,           // La réservation est en cours, fin dans 60 secondes
        LIBRE         // aucune reservation
    }

    private EtatReservation reserve = EtatReservation.LIBRE;

    public DVDs(String id, String titre, boolean adulte) {
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
        synchronized (this) {
            if ( adulte && !ab.plusDe16()) {
                throw new ReservationException("L'abonné doit avoir au moins 18 ans pour réserver.");
            }
            if (ab.banni()) {
                throw new ReservationException("L'abonné est banni");
            }

            if (reserve == EtatReservation.LIBRE) {
                reserve = EtatReservation.RESERVE;
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        synchronized(DVDs.this) {
                            reserve = EtatReservation.EN_ATTENTE_FIN;
                            Timer timer2 = new Timer();
                            TimerTask task2 = new TimerTask() {
                                @Override
                                public void run() {
                                    synchronized(DVDs.this) {
                                        reserve = EtatReservation.LIBRE;
                                        notifyAll();
                                    }
                                }
                            };
                            timer2.schedule(task2, 1000*60);
                        }
                    }
                };
                timer.schedule(task, 1000*60*60*2 - 1000);
            } else if (reserve == EtatReservation.RESERVE) {
                throw new ReservationException("La ressource est déjà réservée.");
            } else {
                while (reserve == EtatReservation.EN_ATTENTE_FIN) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    @Override
    public void emprunt(Abonne ab) throws EmpruntException {

    }

    @Override
    public void retour() throws RetourException {

    }
}
