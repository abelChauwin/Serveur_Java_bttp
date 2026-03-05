package serveur.document;

import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

import java.util.Timer;
import java.util.TimerTask;

public abstract class DocumentEmpruntable implements Document {

    private String id;
    private String titre;

    private Abonne abonne;

    private EtatReservation reserve = EtatReservation.LIBRE;

    private final Timer timer = new Timer();
    private TimerTask taskReservation;
    private TimerTask taskAttente;

    public final static int TEMPS_RESERV = 1000 * 60 * 60 * 2;
    public final static int TEMPS_ATTENTE = 1000 * 60;

    public enum EtatReservation {
        EMPRUNTER,
        RESERVE,
        EN_ATTENTE_FIN,
        LIBRE
    }

    public DocumentEmpruntable(String id, String titre) {
        this.id = id;
        this.titre = titre;
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
            planifierExpiration();
        }else if (reserve == EtatReservation.EMPRUNTER || reserve == EtatReservation.RESERVE) {
            throw new ReservationException("Le DVD n'est pas disponible.");
        }
    }

    @Override
    public synchronized void emprunt(Abonne ab) throws EmpruntException {

        if (reserve != EtatReservation.RESERVE ) {
            throw new EmpruntException("se DVD n'est pas reserver.");
        }else if (abonne != ab) {
            throw new EmpruntException("se DVD est reserver par un autre abonner.");
        }

        annulerTimers();
        reserve = EtatReservation.EMPRUNTER;

        // TODO lancer un timer pour le retour
    }

    @Override
    public synchronized void retour() throws RetourException {

        if (reserve != EtatReservation.EMPRUNTER) {
            throw new RetourException("Le DVD n'est pas emprunté.");
        }
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
        timer.schedule(taskReservation, TEMPS_RESERV - TEMPS_ATTENTE);
    }

    private void annulerTimers() {

        if (taskReservation != null) {
            taskReservation.cancel();
        }

        if (taskAttente != null) {
            taskAttente.cancel();
        }
    }
    @Override
    public String idDoc() {
        return id;
    }

    public abstract void verificationAbonner(Abonne ab) throws ReservationException;

}