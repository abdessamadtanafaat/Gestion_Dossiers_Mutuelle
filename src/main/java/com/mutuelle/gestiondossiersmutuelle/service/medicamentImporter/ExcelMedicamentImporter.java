//package com.mutuelle.gestiondossiersmutuelle.service;
//
//import com.mutuelle.gestiondossiersmutuelle.model.MedicamentReferentiel;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Service
//public class ExcelMedicamentImporter {
//
//    public List<MedicamentReferentiel> readExcelFile(String filePath) throws IOException {
//        List<MedicamentReferentiel> medicaments = new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(filePath);
//             Workbook workbook = new XSSFWorkbook(fis)) {
//
//            Sheet sheet = workbook.getSheetAt(0); // Première feuille Excel
//
//            for (Row row : sheet) {
//                // Ignore la ligne d'en-tête
//                if (row.getRowNum() == 0) {
//                    continue;
//                }
//
//                MedicamentReferentiel medicament = new MedicamentReferentiel();
//
//                Cell codeBarreCell = row.getCell(0);
//                Cell nomCell = row.getCell(1);
//                Cell dci1Cell = row.getCell(2);
//
//                Random random = new Random();
//
//                // Remplissez les champs avec les données des cellules (par exemple, code barre, nom, etc.)
//                medicament.setCodeBarre(codeBarreCell.getStringCellValue());
//                medicament.setNom(nomCell.getStringCellValue());
//                medicament.setDci1(dci1Cell.getStringCellValue());
//
//                // Génération d'un prix de référence aléatoire entre 10 et 500
//                double prixReferenceAleatoire = 10.0 + (500.0 - 10.0) * random.nextDouble(); // Entre 10 et 500
//                medicament.setPrixReference(prixReferenceAleatoire);
//                System.out.println("Prix de référence aléatoire généré : " + prixReferenceAleatoire);
//
//                // Génération d'un pourcentage de remboursement aléatoire entre 50% (0.5) et 100% (1.0)
//                double pourcentageRemboursementAleatoire = 0.5 + (1.0 - 0.5) * random.nextDouble(); // Entre 50% et 100%
//                medicament.setPourcentageRemboursement(pourcentageRemboursementAleatoire);
//                System.out.println("Pourcentage de remboursement aléatoire généré : " + pourcentageRemboursementAleatoire);
//                medicaments.add(medicament);
//            }
//        }
//
//        return medicaments;
//    }
//}
