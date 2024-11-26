package com.mutuelle.gestiondossiersmutuelle.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Beneficiaire {

    private String nom;
    private String lienParente;
    private String dateDepotDossier;

    // Getters and Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLienParente() {
        return lienParente;
    }

    public void setLienParente(String lienParente) {
        this.lienParente = lienParente;
    }

    public String getDateDepotDossier() {
        return dateDepotDossier;
    }

    public void setDateDepotDossier(String dateDepotDossier) {
        this.dateDepotDossier = dateDepotDossier;
    }
}
