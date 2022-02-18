package com.mapsit.jamaity.structureAssociation;

import com.mapsit.jamaity.domain.Domain;
import com.mapsit.jamaity.subDomain.SubDomain;
import com.mapsit.jamaity.subStructureAssociation.SubStructureAssociation;
import com.mapsit.jamaity.subStructureAssociation.SubStructureAssociationRepository;
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
public class StructureAssociationService {

    private final Logger log = LoggerFactory.getLogger(StructureAssociationService.class);

    private final StructureAssociationRepository structureAssociationRepository;

    private final SubStructureAssociationRepository subStructureAssociationRepository;

    public StructureAssociationService(StructureAssociationRepository structureAssociationRepository, SubStructureAssociationRepository subStructureAssociationRepository) {

        this.structureAssociationRepository = structureAssociationRepository;
        this.subStructureAssociationRepository = subStructureAssociationRepository;
    }

    /**
     * Save a structureAssociation.
     *
     * @param structureAssociation the entity to save.
     * @return the persisted entity.
     */
    public StructureAssociation save(StructureAssociation structureAssociation) {
        log.debug("Request to save StructureAssociation : {}", structureAssociation);

        StructureAssociation savedStructureAssociation = structureAssociationRepository.save(structureAssociation);

        SubStructureAssociation subStructureAssociation = new SubStructureAssociation();
        subStructureAssociation.setNameAr("إفتراضي");
        subStructureAssociation.setNameFr("defaut");
        subStructureAssociation.setNameEn("default");
        subStructureAssociation.setStructureAssociation(savedStructureAssociation);
        subStructureAssociationRepository.save(subStructureAssociation);

        return savedStructureAssociation;
    }

    /**
     * Get all the structureAssociations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StructureAssociation> findAll(Pageable pageable) {
        log.debug("Request to get all StructureAssociations");
        return structureAssociationRepository.findAll(pageable);
    }


    /**
     * Get all the structureAssociations without pagination.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StructureAssociation> findAll() {
        log.debug("Request to get all StructureAssociations without pagination");
        return structureAssociationRepository.findAll();
    }



    /**
     * Get one structureAssociation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StructureAssociation> findOne(UUID id) {
        log.debug("Request to get StructureAssociation : {}", id);
        return structureAssociationRepository.findById(id);
    }

    /**
     * Delete the structureAssociation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete StructureAssociation : {}", id);

        Optional<StructureAssociation> structureAssociation = structureAssociationRepository.findById(id);
        List<SubStructureAssociation> subStructureAssociationList = structureAssociation.get().getSubStructureAssociations();
        SubStructureAssociation subStructureAssociation = subStructureAssociationList.get(0);
        if(subStructureAssociationList.size() == 1 && subStructureAssociation.getNameEn().equals(("default"))){

            subStructureAssociationRepository.delete(subStructureAssociation);
        }

        structureAssociationRepository.deleteById(id);
    }
}
