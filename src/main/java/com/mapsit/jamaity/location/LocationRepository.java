package com.mapsit.jamaity.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
}
