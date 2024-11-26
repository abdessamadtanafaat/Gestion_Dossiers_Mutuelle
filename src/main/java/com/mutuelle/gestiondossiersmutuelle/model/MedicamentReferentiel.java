package com.mutuelle.gestiondossiersmutuelle.model;

import jakarta.persistence.*;

@Entity
public class MedicamentReferentiel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code_barre")
    private String codeBarre;
    private String nom;
    private String dci1;  // Dénomination Commune Internationale
    private double prixReference;
    private double pourcentageRemboursement;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDci1() {
        return dci1;
    }

    public void setDci1(String dci1) {
        this.dci1 = dci1;
    }

    public double getPrixReference() {
        return prixReference;
    }

    public void setPrixReference(double prixReference) {
        this.prixReference = prixReference;
    }

    public double getPourcentageRemboursement() {
        return pourcentageRemboursement;
    }

    public void setPourcentageRemboursement(double pourcentageRemboursement) {
        this.pourcentageRemboursement = pourcentageRemboursement;
    }


}
