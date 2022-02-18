package com.mapsit.jamaity.etiquette;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation
 */
@Service
@Transactional
public class EtiquetteService {

    private final Logger log = LoggerFactory.getLogger(EtiquetteService.class);

    private final EtiquetteRepository etiquetteRepository;

    public EtiquetteService(EtiquetteRepository etiquetteRepository) {

        this.etiquetteRepository = etiquetteRepository;
    }

    /**
     * Save a etiquette.
     *
     * @param etiquette the entity to save.
     * @return the persisted entity.
     */
    public Etiquette save(Etiquette etiquette) {
        log.debug("Request to save Etiquette : {}", etiquette);
        return etiquetteRepository.save(etiquette);
    }

    /**
     * Get all the etiquettes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Etiquette> findAll(Pageable pageable) {
        log.debug("Request to get all Etiquettes");
        return etiquetteRepository.findAll(pageable);
    }


    /**
     * Get all the etiquettes.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Etiquette> findAll() {
        log.debug("Request to get all Etiquettes");
        return etiquetteRepository.findAll();
    }


    /**
     * Get one etiquette by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Etiquette> findOne(UUID id) {
        log.debug("Request to get Etiquette : {}", id);
        return etiquetteRepository.findById(id);
    }

    /**
     * Delete the etiquette by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Etiquette : {}", id);
        etiquetteRepository.deleteById(id);
    }
}
