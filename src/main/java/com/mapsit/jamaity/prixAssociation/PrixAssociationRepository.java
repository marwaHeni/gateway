package com.mapsit.jamaity.prixAssociation;

import com.mapsit.jamaity.etiquette.Etiquette;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrixAssociationRepository extends JpaRepository<PrixAssociation, UUID> {
}
