package serveur.document;

import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

import java.util.Timer;
import java.util.TimerTask;

public abstract class DocumentEmpruntable implements IDocument {

    public final static int TEMPS_RESERV = 1000 * 60 * 60 * 2;
    public final static int TEMPS_ATTENTE = 1000 * 60;
    public final static int TEMPS_RETOUR = 1000 * 60 * 60 * 24 * 7 * 2;

    private final String id;
    private final String titre;

    private final int tempsReserv;
    private final int tempsAttente;
    private final int tempsRetour;

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
        tempsReserv = TEMPS_RESERV;
        tempsAttente = TEMPS_ATTENTE;
        tempsRetour = TEMPS_RETOUR;
    }

    public DocumentEmpruntable(String id, String titre, int tempsReserv, int tempsAttente, int tempsRetour) {
        this.id = id;
        this.titre = titre;
        this.tempsReserv = tempsReserv;
        this.tempsAttente = tempsAttente;
        this.tempsRetour = tempsRetour;
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
                                    DocumentEmpruntable.this.notifyAll();
                                }
                            }
                        };
                        timer.schedule(taskAttente, tempsAttente);
                    }
                }
            };
            timer.schedule(taskReservation, tempsReserv - tempsAttente);
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
        notifyAll();

        taskRetour = new TimerTask() {
            @Override
            public void run() {
                ab.bannir();
            }
        };
        timer.schedule(taskRetour, tempsRetour );
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

    private void annulerTimers() {
        if(taskAttente != null) {taskAttente.cancel();}
        if(taskReservation != null){taskReservation.cancel();}
    }
    @Override
    public String idDoc() {return id;}
    public Abonne getAbonne() {return abonne;}
    public EtatReservation getEtat() {return reserve;}
    public String getTitre() {return titre;}
    public Timer getTimer() {return timer;}

    public abstract void verificationAbonner(Abonne ab) throws ReservationException;

}