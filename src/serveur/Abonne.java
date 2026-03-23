package serveur;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Abonne {
    private final int id;
    private final String nom;
    private final String dateDeNaissance;

    private LocalDate dateFinBannissement;

    private static final String FORMAT ="dd-MM-yyyy";

    public Abonne(int id, String nom, String dateDeNaissance) {
        this.id = id;
        this.nom = nom;
        this.dateDeNaissance = dateDeNaissance;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        LocalDate naissance = LocalDate.parse(dateDeNaissance, formatter);
        return Period.between(naissance, LocalDate.now()).getYears();
    }

    public boolean banni() {
        if (dateFinBannissement == null) {
            return false;
        }
        return LocalDate.now().isBefore(dateFinBannissement);
    }


    public void bannir() {
        dateFinBannissement = LocalDate.now().plusMonths(1);
    }

    public LocalDate getDateFinBannissement() {
        return dateFinBannissement;
    }
    public void setDateFinBannissement(LocalDate date) { // afin de tester
        this.dateFinBannissement = date;
    }
}