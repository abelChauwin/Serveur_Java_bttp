package serveur.document;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import serveur.Abonne;
import serveur.document.exeption.EmpruntException;
import serveur.document.exeption.ReservationException;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class dvdsTests {

    @Test
    void testDocument() throws Exception {
        Abonne abAdulte = new Abonne(1, "abAdulte", "01-01-2006"); // 20 ans
        Abonne abEnfant = new Abonne(1, "abEnfant", "01-01-2025"); // 1 ans
        DVDS docAdult = new DVDS("1", "dvdAdulte", true);
        Livres docEnfant = new Livres("1", "dvdEnfant", 10);

        Assertions.assertEquals(DocumentEmpruntable.EtatReservation.LIBRE, docEnfant.getEtat());    // le doc peut etre reserver
        docEnfant.reservation(abEnfant);
        Assertions.assertEquals(DocumentEmpruntable.EtatReservation.RESERVE, docEnfant.getEtat());  // reservaion effectuer

        assertThrows(ReservationException.class, () -> docEnfant.reservation(abAdulte));            // deja reserver
        assertThrows(ReservationException.class, () ->docAdult.reservation(abEnfant));              //enfant ne peut pas reserver docAdult
        docAdult.reservation(abAdulte);
        Assertions.assertEquals(DocumentEmpruntable.EtatReservation.RESERVE, docAdult.getEtat());   // reservaion effectuer

        assertThrows(EmpruntException.class, () ->docAdult.emprunt(abEnfant));                      // emprunt par le mauvaise abonner
        docAdult.emprunt(abAdulte);
        Assertions.assertEquals(DocumentEmpruntable.EtatReservation.EMPRUNTER, docAdult.getEtat());    // emprunt effectuer

        docAdult.retour();
        Assertions.assertEquals(DocumentEmpruntable.EtatReservation.LIBRE, docAdult.getEtat());    // le doc peut re etre reserver


        DVDS docTime = new DVDS("1", "dvdTime", false, 200, 100, 500);
        Thread t = new Thread(() -> {
            try {
                docTime.reservation(abEnfant);
            } catch (Exception e) {
                Assertions.fail(e);
            }
        });

        docTime.reservation(abAdulte);  // on reserve
        Thread.sleep(120);         // la reservation est presque fini
        Assertions.assertEquals(
                DocumentEmpruntable.EtatReservation.EN_ATTENTE_FIN,
                docTime.getEtat()
        );
        t.start();      // on tente de reservé avec un thread
        t.join();       // on attend que la reservation finisse et que le thread reserve
        Assertions.assertEquals(DocumentEmpruntable.EtatReservation.RESERVE, docTime.getEtat());    // reservation effectuer a la fin du wait()
        Assertions.assertEquals(abEnfant, docTime.getAbonne());

        docTime.emprunt(abEnfant);
        Thread.sleep(600); // le delay de rendu est passer l'abonne doit etre banni
        Assertions.assertTrue(abEnfant.banni());
        docTime.retour();
        assertThrows( ReservationException.class,()->docTime.reservation(abEnfant));// abEnfant est bani
        abEnfant.setDateFinBannissement(LocalDate.now().minusDays(1)); // on raccoursis le bannisement pour tester qu'il senleve
        Assertions.assertFalse(abEnfant.banni()); // le banissement doit etre enlever car la date de fin et passer

    }


}