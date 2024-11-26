package com.mutuelle.gestiondossiersmutuelle.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Data
public class Dossier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroAffiliation;
    private String nomAssure;
    private String immatriculation;
    private String lienParente;
    private double montantTotalFrais;
    private double prixConsultation;
    private int nombrePiecesJointes;
    private String nomBeneficiaire;
    private String dateDepotDossier;
    private boolean rejet;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nom", column = @Column(name = "assure_nom")),
            @AttributeOverride(name = "numeroAffiliation", column = @Column(name = "assure_numero_affiliation")),
            @AttributeOverride(name = "immatriculation", column = @Column(name = "assure_immatriculation"))
    })
    private Assure assure;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nom", column = @Column(name = "beneficiaire_nom")),
            @AttributeOverride(name = "lienParente", column = @Column(name = "beneficiaire_lien_parente")),
            @AttributeOverride(name = "dateDepotDossier", column = @Column(name = "beneficiaire_date_depot"))
    })
    private Beneficiaire beneficiaire;

    @Transient
    private List<Traitement> traitements;

    private double montantTotalRembourse;
}
