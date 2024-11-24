package com.mutuelle.gestiondossiersmutuelle.writer;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import com.mutuelle.gestiondossiersmutuelle.repository.DossierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class DossierDatabaseWriter implements ItemWriter<Dossier> {

    private static final Logger logger = LoggerFactory.getLogger(DossierDatabaseWriter.class);

    private final DossierRepository dossierRepository;

    public DossierDatabaseWriter(DossierRepository dossierRepository) {
        this.dossierRepository = dossierRepository;
    }

    @Override
    public void write(Chunk<? extends Dossier> dossiers) throws Exception {
        dossierRepository.saveAll(dossiers);
        dossiers.forEach(dossier -> logger.info("Dossier saved: {}", dossier));
    }
}
