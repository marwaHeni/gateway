package com.mapsit.jamaity.typePtf;

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
public class TypePtfService {

    private final Logger log = LoggerFactory.getLogger(TypePtfService.class);

    private final TypePtfRepository typePtfRepository;

    public TypePtfService(TypePtfRepository typePtfRepository) {

        this.typePtfRepository = typePtfRepository;
    }

    /**
     * Save a typePtf.
     *
     * @param typePtf the entity to save.
     * @return the persisted entity.
     */
    public TypePtf save(TypePtf typePtf) {
        log.debug("Request to save TypePtf : {}", typePtf);
        return typePtfRepository.save(typePtf);
    }

    /**
     * Get all the typePtfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypePtf> findAll(Pageable pageable) {
        log.debug("Request to get all TypePtfs");
        return typePtfRepository.findAll(pageable);
    }


    /**
     * Get all the typePtfs.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TypePtf> findAll() {
        log.debug("Request to get all TypePtfs");
        return typePtfRepository.findAll();
    }


    /**
     * Get one typePtf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypePtf> findOne(UUID id) {
        log.debug("Request to get TypePtf : {}", id);
        return typePtfRepository.findById(id);
    }

    /**
     * Delete the typePtf by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete TypePtf : {}", id);
        typePtfRepository.deleteById(id);
    }
}
