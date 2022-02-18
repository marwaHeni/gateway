package com.mapsit.jamaity.contactPtf;

import com.mapsit.jamaity.prioritePtf.PrioritePtf;
import com.mapsit.jamaity.prioritePtf.PrioritePtfRepository;
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
public class ContactPtfService {

    private final Logger log = LoggerFactory.getLogger(ContactPtfService.class);

    private final PrioritePtfRepository prioritePtfRepository;

    public ContactPtfService(PrioritePtfRepository prioritePtfRepository) {

        this.prioritePtfRepository = prioritePtfRepository;
    }

    /**
     * Save a prioritePtf.
     *
     * @param prioritePtf the entity to save.
     * @return the persisted entity.
     */
    public PrioritePtf save(PrioritePtf prioritePtf) {
        log.debug("Request to save PrioritePtf : {}", prioritePtf);
        return prioritePtfRepository.save(prioritePtf);
    }

    /**
     * Get all the prioritePtfs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PrioritePtf> findAll(Pageable pageable) {
        log.debug("Request to get all PrioritePtfs");
        return prioritePtfRepository.findAll(pageable);
    }


    /**
     * Get all the prioritePtfs.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PrioritePtf> findAll() {
        log.debug("Request to get all PrioritePtfs");
        return prioritePtfRepository.findAll();
    }


    /**
     * Get one prioritePtf by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PrioritePtf> findOne(UUID id) {
        log.debug("Request to get PrioritePtf : {}", id);
        return prioritePtfRepository.findById(id);
    }

    /**
     * Delete the prioritePtf by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete PrioritePtf : {}", id);
        prioritePtfRepository.deleteById(id);
    }
}
