package com.mutuelle.gestiondossiersmutuelle.reader;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


@Component
public class DossierJsonReader extends JsonItemReader<Dossier> {
    public DossierJsonReader() {
        super();
        setName("dossierJsonReader");
        setResource(new ClassPathResource("/data/dossiers.json"));
        setJsonObjectReader(new org.springframework.batch.item.json.JacksonJsonObjectReader<>(Dossier.class));
    }

}
