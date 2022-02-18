package com.mapsit.jamaity.prixAssociation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation
 */
@Service
@Transactional
public class PrixAssociationService {

    private final Logger log = LoggerFactory.getLogger(PrixAssociationService.class);

    private final PrixAssociationRepository prixAssociationRepository;

    public PrixAssociationService(PrixAssociationRepository prixAssociationRepository) {

        this.prixAssociationRepository = prixAssociationRepository;
    }

    /**
     * Save a prixAssociation.
     *
     * @param prixAssociation the entity to save.
     * @return the persisted entity.
     */
    public PrixAssociation save(PrixAssociation prixAssociation) {
        log.debug("Request to save PrixAssociation : {}", prixAssociation);
        return prixAssociationRepository.save(prixAssociation);
    }

    /**
     * Get all the prixAssociations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrixAssociation> findAll(Pageable pageable) {
        log.debug("Request to get all PrixAssociations");
        return prixAssociationRepository.findAll(pageable);
    }


    /**
     * Get all the prixAssociations.
     *
 //    * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrixAssociation> findAll() {
        log.debug("Request to get all PrixAssociations");
        return prixAssociationRepository.findAll();
    }


    /**
     * Get one prixAssociation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrixAssociation> findOne(UUID id) {
        log.debug("Request to get PrixAssociation : {}", id);
        return prixAssociationRepository.findById(id);
    }

    /**
     * Delete the prixAssociation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete PrixAssociation : {}", id);
        prixAssociationRepository.deleteById(id);
    }
}
