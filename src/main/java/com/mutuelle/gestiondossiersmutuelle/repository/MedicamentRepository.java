package com.mutuelle.gestiondossiersmutuelle.repository;

import com.mutuelle.gestiondossiersmutuelle.model.MedicamentReferentiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends JpaRepository<MedicamentReferentiel, Long> {
    MedicamentReferentiel findByCodeBarre(String codeBarre);
    boolean existsByCodeBarre(String codeBarre);

}
