package com.mutuelle.gestiondossiersmutuelle.writer;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DossierConsoleWriter implements ItemWriter<Dossier> {

    @Override
    public void write(Chunk<? extends Dossier> dossiers) throws Exception {
        dossiers.forEach(dossier -> System.out.println("Dossier lu : "+dossier));

    }
}
