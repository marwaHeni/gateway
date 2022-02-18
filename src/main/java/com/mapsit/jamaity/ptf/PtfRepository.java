package com.mapsit.jamaity.ptf;

import com.mapsit.jamaity.association.Association;
import com.mapsit.jamaity.typePtf.TypePtf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PtfRepository extends JpaRepository<Ptf, UUID> {


    ///search
    @Query("SELECT distinct p from Ptf p where ((LOWER(p.titleFr) LIKE %?1%) or (LOWER(p.titleAr) LIKE %?1%))")
    List<Ptf> findAllPtfsWithPartOfAttribute(String attribute);

}
