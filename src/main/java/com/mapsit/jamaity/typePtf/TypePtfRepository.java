package com.mapsit.jamaity.typePtf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TypePtfRepository extends JpaRepository<TypePtf, UUID> {
}
