package com.mutuelle.gestiondossiersmutuelle.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
public class Traitement {

    private String codeBarre;
    private String nomMedicament;
    private String typeMedicament;
    private double prixMedicament;
    private boolean existe;
    private double remboursementPourcentage;


}

