package com.mapsit.jamaity.imageAssociation;

import com.mapsit.jamaity.etiquette.Etiquette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageAssociationRepository extends JpaRepository<ImageAssociation, Long> {
}
