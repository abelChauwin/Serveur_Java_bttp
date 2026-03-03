package serveur;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Abonne {
    private int id;
    private String nom;
    private String dateDeNaissance; // format attendu : "yyyy-MM-dd", ex: "2005-03-03"

    private static final String FORMAT ="yyyy-MM-dd";

    // Constructeur
    public Abonne(int id, String nom, String dateDeNaissance) {
        this.id = id;
        this.nom = nom;
        this.dateDeNaissance = dateDeNaissance;
    }

    public boolean plusDe16() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        LocalDate naissance = LocalDate.parse(dateDeNaissance, formatter);

        LocalDate aujourdHui = LocalDate.now();
        int age = Period.between(naissance, aujourdHui).getYears();

        return age >= 16;
    }

    public String getNom() {
        return nom;
    }

    public int getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        LocalDate naissance = LocalDate.parse(dateDeNaissance, formatter);
        return Period.between(naissance, LocalDate.now()).getYears();
    }
}