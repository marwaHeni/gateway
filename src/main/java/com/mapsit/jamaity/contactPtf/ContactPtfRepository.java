package com.mapsit.jamaity.contactPtf;

import com.mapsit.jamaity.association.Association;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactPtfRepository extends JpaRepository<ContactPtf, UUID> {

}
