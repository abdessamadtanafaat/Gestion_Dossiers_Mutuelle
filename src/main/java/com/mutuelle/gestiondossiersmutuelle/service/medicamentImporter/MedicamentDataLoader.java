//package com.mutuelle.gestiondossiersmutuelle.service;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MedicamentDataLoader implements CommandLineRunner {
//
//    private final MedicamentService medicamentService;
//
//    public MedicamentDataLoader(MedicamentService medicamentService) {
//        this.medicamentService = medicamentService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        String excelFilePath = "T:\\5emeAnneeENSA\\ArchitectureLogicielle\\MiniProjet\\Gestion_Dossiers_Mutuelle\\src\\main\\resources\\data\\ref-des-medicaments-cnops-2014.xlsx"; // Remplacez par le chemin de votre fichier
//        medicamentService.importDataFromExcel(excelFilePath);
//    }
//}
