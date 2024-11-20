package com.mutuelle.gestiondossiersmutuelle.writer;


import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import com.mutuelle.gestiondossiersmutuelle.repository.DossierRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class DossierDatabaseWriter implements ItemWriter<Dossier> {

    private final DossierRepository dossierRepository;
    public DossierDatabaseWriter(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    @Override
    public void write(Chunk<? extends Dossier> dossiers) throws Exception {
        dossierRepository.saveAll(dossiers);
        dossiers.forEach(dossier -> System.out.print("Dossier enregistree :" +dossier));
    }

}
