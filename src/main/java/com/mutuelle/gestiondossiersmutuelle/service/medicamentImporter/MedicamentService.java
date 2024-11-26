//package com.mutuelle.gestiondossiersmutuelle.service;
//
//import com.mutuelle.gestiondossiersmutuelle.model.MedicamentReferentiel;
//import com.mutuelle.gestiondossiersmutuelle.repository.MedicamentRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.util.List;
//
//@Service
//public class MedicamentService {
//
//    private final MedicamentRepository medicamentRepository;
//    private final ExcelMedicamentImporter excelMedicamentImporter;
//
//    public MedicamentService(MedicamentRepository medicamentRepository,
//                             ExcelMedicamentImporter excelMedicamentImporter) {
//        this.medicamentRepository = medicamentRepository;
//        this.excelMedicamentImporter = excelMedicamentImporter;
//    }
//
//    @Transactional
//    public void importDataFromExcel(String filePath) throws IOException {
//        List<MedicamentReferentiel> medicaments = excelMedicamentImporter.readExcelFile(filePath);
//
//        for (MedicamentReferentiel medicament : medicaments) {
//            // Vérifier si le médicament existe déjà dans la base
//            if (!medicamentRepository.existsByCodeBarre(medicament.getCodeBarre())) {
//                medicamentRepository.save(medicament);
//            }
////            else {
////                System.out.println("Médicament déjà présent dans la base : " + medicament.getCodeBarre());
////            }
//        }
//
//        System.out.println("Données importées avec succès !");
//    }
//}
