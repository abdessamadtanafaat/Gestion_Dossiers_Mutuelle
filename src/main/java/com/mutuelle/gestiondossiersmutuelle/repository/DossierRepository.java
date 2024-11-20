package com.mutuelle.gestiondossiersmutuelle.repository;

import com.mutuelle.gestiondossiersmutuelle.model.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierRepository extends JpaRepository<Dossier, String> {
}
