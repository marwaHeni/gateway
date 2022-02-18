package com.mapsit.jamaity.subStructureAssociation;

import com.mapsit.jamaity.structureAssociation.StructureAssociation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubStructureAssociationRepository extends JpaRepository<SubStructureAssociation, UUID> {
}
