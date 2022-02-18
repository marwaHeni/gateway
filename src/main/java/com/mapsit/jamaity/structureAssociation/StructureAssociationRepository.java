package com.mapsit.jamaity.structureAssociation;

import com.mapsit.jamaity.domain.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StructureAssociationRepository extends JpaRepository<StructureAssociation, UUID> {
}
