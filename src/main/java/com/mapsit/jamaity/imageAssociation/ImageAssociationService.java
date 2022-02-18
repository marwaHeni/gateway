package com.mapsit.jamaity.imageAssociation;

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
public class ImageAssociationService {

    private final Logger log = LoggerFactory.getLogger(ImageAssociationService.class);

    private final ImageAssociationRepository imageAssociationRepository;

    public ImageAssociationService(ImageAssociationRepository imageAssociationRepository) {

        this.imageAssociationRepository = imageAssociationRepository;
    }

    /**
     * Save a imageAssociation.
     *
     * @param imageAssociation the entity to save.
     * @return the persisted entity.
     */
    public ImageAssociation save(ImageAssociation imageAssociation) {
        log.debug("Request to save ImageAssociation : {}", imageAssociation);
        return imageAssociationRepository.save(imageAssociation);
    }

    /**
     * Get all the imageAssociations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ImageAssociation> findAll(Pageable pageable) {
        log.debug("Request to get all ImageAssociations");
        return imageAssociationRepository.findAll(pageable);
    }


    /**
     * Get all the imageAssociations.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ImageAssociation> findAll() {
        log.debug("Request to get all ImageAssociations");
        return imageAssociationRepository.findAll();
    }


    /**
     * Get one imageAssociation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ImageAssociation> findOne(Long id) {
        log.debug("Request to get ImageAssociation : {}", id);
        return imageAssociationRepository.findById(id);
    }

    /**
     * Delete the imageAssociation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ImageAssociation : {}", id);
        imageAssociationRepository.deleteById(id);
    }
}
