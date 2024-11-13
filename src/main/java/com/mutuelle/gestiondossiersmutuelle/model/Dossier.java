package com.mutuelle.gestiondossiersmutuelle.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Dossier {

    private String nomAssure;
    private String numeroAffiliation;
    private String immatriculation;
    private String lienParente;
    private double montantTotalFrais;
    private double prixConsultation;
    private int nombrePiecesJointes;
    private String nomBeneficiaire;
    private String dateDepotDossier;
    private Assure assure;           // Assure object
    private Beneficiaire beneficiaire; // Beneficiaire object

    // Getters and Setters

    public String getNomAssure() {
        return nomAssure;
    }

    public void setNomAssure(String nomAssure) {
        this.nomAssure = nomAssure;
    }

    public String getNumeroAffiliation() {
        return numeroAffiliation;
    }

    public void setNumeroAffiliation(String numeroAffiliation) {
        this.numeroAffiliation = numeroAffiliation;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getLienParente() {
        return lienParente;
    }

    public void setLienParente(String lienParente) {
        this.lienParente = lienParente;
    }

    public double getMontantTotalFrais() {
        return montantTotalFrais;
    }

    public void setMontantTotalFrais(double montantTotalFrais) {
        this.montantTotalFrais = montantTotalFrais;
    }

    public double getPrixConsultation() {
        return prixConsultation;
    }

    public void setPrixConsultation(double prixConsultation) {
        this.prixConsultation = prixConsultation;
    }

    public int getNombrePiecesJointes() {
        return nombrePiecesJointes;
    }

    public void setNombrePiecesJointes(int nombrePiecesJointes) {
        this.nombrePiecesJointes = nombrePiecesJointes;
    }

    public String getNomBeneficiaire() {
        return nomBeneficiaire;
    }

    public void setNomBeneficiaire(String nomBeneficiaire) {
        this.nomBeneficiaire = nomBeneficiaire;
    }

    public String getDateDepotDossier() {
        return dateDepotDossier;
    }

    public void setDateDepotDossier(String dateDepotDossier) {
        this.dateDepotDossier = dateDepotDossier;
    }

    public Assure getAssure() {
        return assure;
    }

    public void setAssure(Assure assure) {
        this.assure = assure;
    }

    public Beneficiaire getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(Beneficiaire beneficiaire) {
        this.beneficiaire = beneficiaire;
    }
}
