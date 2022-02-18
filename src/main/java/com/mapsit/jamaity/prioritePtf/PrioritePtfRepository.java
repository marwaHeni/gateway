package com.mapsit.jamaity.prioritePtf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrioritePtfRepository extends JpaRepository<PrioritePtf, UUID> {

}
