package com.mapsit.jamaity.association;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface AssociationRepository extends JpaRepository<Association, UUID> {


    //list des association : visibilité=public et datePublication > currentTime
    @Query("select a from Association a  where a.datePublication  <= current_timestamp and a.visibilite = 'PUBLIC'")
    List<Association> get_associations();


    ///search
    @Query("SELECT distinct a from Association a where ((LOWER(a.titleFr) LIKE %?1%) or (LOWER(a.titleAr) LIKE %?1%) or (LOWER(a.presidentFr) LIKE %?1%) or (LOWER(a.presidentAr) LIKE %?1%))")
    List<Association> findAllAssociationsWithPartOfAttribute(String attribute);
}
