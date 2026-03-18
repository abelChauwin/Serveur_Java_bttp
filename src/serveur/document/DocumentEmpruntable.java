package serveur.document;

import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

import java.util.Timer;
import java.util.TimerTask;

public abstract class DocumentEmpruntable implements Document {

    public final static int TEMPS_RESERV = 1000 * 60 * 60 * 2;
    public final static int TEMPS_ATTENTE = 1000 * 60;
    public final static int TEMPS_RETOUR = 1000 * 60 * 60 * 24 * 7 * 2;

    private String id;
    private String titre;

    private Abonne abonne;
    private EtatReservation reserve = EtatReservation.LIBRE;

    private final Timer timer = new Timer();
    private TimerTask taskReservation;
    private TimerTask taskAttente;
    private TimerTask taskRetour;



    public enum EtatReservation {
        EMPRUNTER,
        RESERVE,
        EN_ATTENTE_FIN,
        LIBRE
    }

    public DocumentEmpruntable(String id, String titre) {
        this.id = id;
        this.titre = titre;
        planifierExpiration();
    }

    @Override
    public synchronized void reservation(Abonne ab) throws ReservationException {
        verificationAbonner(ab);
        if (ab.banni()) {
            throw new ReservationException("L'abonné est banni.");
        }

        while (reserve == EtatReservation.EN_ATTENTE_FIN) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (reserve == EtatReservation.LIBRE) {
            abonne = ab;
            reserve = EtatReservation.RESERVE;
            timer.schedule(taskReservation, TEMPS_RESERV - TEMPS_ATTENTE);
        }else if (reserve == EtatReservation.EMPRUNTER || reserve == EtatReservation.RESERVE) {
            throw new ReservationException("Le DVD n'est pas disponible.");
        }
    }

    @Override
    public synchronized void emprunt(Abonne ab) throws EmpruntException {

        if (reserve != EtatReservation.RESERVE ) {
            throw new EmpruntException("Ce DVD n'est pas reservé.");
        }else if (abonne != ab) {
            throw new EmpruntException("Ce DVD est reservé par un autre abonné.");
        }

        annulerTimers();
        reserve = EtatReservation.EMPRUNTER;


        taskRetour = new TimerTask() {
            @Override
            public void run() {
                ab.bannir();
            }
        };
        timer.schedule(taskRetour, TEMPS_RETOUR );
    }

    @Override
    public synchronized void retour() throws RetourException {

        if (reserve != EtatReservation.EMPRUNTER) {
            throw new RetourException("Le DVD n'est pas emprunté.");
        }
        taskRetour.cancel();
        reserve = EtatReservation.LIBRE;
        abonne = null;
    }


    private void planifierExpiration() {

        taskReservation = new TimerTask() {
            @Override
            public void run() {
                synchronized (DocumentEmpruntable.this) {

                    reserve = EtatReservation.EN_ATTENTE_FIN;

                    taskAttente = new TimerTask() {
                        @Override
                        public void run() {
                            synchronized (DocumentEmpruntable.this) {
                                reserve = EtatReservation.LIBRE;
                                abonne = null;
                                notifyAll();
                            }
                        }
                    };

                    timer.schedule(taskAttente, TEMPS_ATTENTE);
                }
            }
        };
    }

    private void annulerTimers() {
            taskReservation.cancel();
            taskAttente.cancel();
    }
    @Override
    public String idDoc() {
        return id;
    }

    public abstract void verificationAbonner(Abonne ab) throws ReservationException;

}