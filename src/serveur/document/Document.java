package serveur.document;

import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;
import serveur.document.exeption.RetourException;

public interface Document {
    String idDoc();
    // exception si déjà réservé ou emprunté
    void reservation (Abonne ab) throws ReservationException;
    // exception si réservé pour une autre abonné ou déjà emprunté
    void emprunt(Abonne ab) throws EmpruntException;
    // sert au retour d’un document ou à l’annulation d‘une réservation
    void retour() throws RetourException;
}