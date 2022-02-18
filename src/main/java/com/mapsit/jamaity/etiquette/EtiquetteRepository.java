package com.mapsit.jamaity.etiquette;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EtiquetteRepository extends JpaRepository<Etiquette, UUID> {
}
