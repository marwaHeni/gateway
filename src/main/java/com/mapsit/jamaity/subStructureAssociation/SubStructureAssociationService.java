package com.mapsit.jamaity.subStructureAssociation;

import com.mapsit.jamaity.association.Association;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
public class SubStructureAssociationService {

    private final Logger log = LoggerFactory.getLogger(SubStructureAssociationService.class);

    private final SubStructureAssociationRepository subStructureAssociationRepository;

    public SubStructureAssociationService(SubStructureAssociationRepository subStructureAssociationRepository) {

        this.subStructureAssociationRepository = subStructureAssociationRepository;
    }

    /**
     * Save a subStructureAssociation.
     *
     * @param subStructureAssociation the entity to save.
     * @return the persisted entity.
     */
    public SubStructureAssociation save(SubStructureAssociation subStructureAssociation) {
        log.debug("Request to save SubStructureAssociation : {}", subStructureAssociation);
        return subStructureAssociationRepository.save(subStructureAssociation);
    }

//    /**
//     * Get all the subStructureAssociations.
//     *
//     * @param pageable the pagination information.
//     * @return the list of entities.
//     */
//    @Transactional(readOnly = true)
//    public Page<SubStructureAssociation> findAll(Pageable pageable) {
//        log.debug("Request to get all SubStructureAssociations");
//        return subStructureAssociationRepository.findAll(pageable);
//    }


    /**
     * Get all the subStructureAssociations.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubStructureAssociation> findAll() {
        log.debug("Request to get all SubStructureAssociations");
        return subStructureAssociationRepository.findAll();
    }



    /**
     * Get all the subStructureAssociations translated.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubStructureAssociation> findAll(String lang) {
        log.debug("Request to get all SubStructureAssociations translated");
        List<SubStructureAssociation> subStructureAssociationList = subStructureAssociationRepository.findAll();
        List<SubStructureAssociation> subStructureAssociationTranslatedList = new ArrayList<>();


        if (subStructureAssociationList != null && subStructureAssociationList.size() != 0) {
            for (SubStructureAssociation subStructureAssociation : subStructureAssociationList) {


                if (lang.equals("fr")) {

                    SubStructureAssociation subStructureAssociationTranslated = new SubStructureAssociation();
                    subStructureAssociationTranslated.setId(subStructureAssociation.getId());
                    if(subStructureAssociation.getNameFr().equals("defaut")) {
                        subStructureAssociationTranslated.setName(subStructureAssociation.getStructureAssociation().getNameFr());
                    }
                    else
                    {
                        subStructureAssociationTranslated.setName(subStructureAssociation.getNameFr());
                    }
                    subStructureAssociationTranslated.setSlug(subStructureAssociation.getSlug());
                    subStructureAssociationTranslated.setDescription(subStructureAssociation.getDescriptionFr());
                    subStructureAssociationTranslatedList.add(subStructureAssociationTranslated);

                }



                if (lang.equals("ar")) {

                    SubStructureAssociation subStructureAssociationTranslated = new SubStructureAssociation();
                    subStructureAssociationTranslated.setId(subStructureAssociation.getId());

                    if(subStructureAssociation.getNameAr().equals("إفتراضي")) {
                        subStructureAssociationTranslated.setName(subStructureAssociation.getStructureAssociation().getNameAr());
                    }
                    else
                    {
                        subStructureAssociationTranslated.setName(subStructureAssociation.getNameAr());
                    }

                    subStructureAssociationTranslated.setSlug(subStructureAssociation.getSlug());
                    subStructureAssociationTranslated.setDescription(subStructureAssociation.getDescriptionAr());
                    subStructureAssociationTranslatedList.add(subStructureAssociationTranslated);

                }



            }
        }

        return subStructureAssociationTranslatedList;

    }



    /**
     * Get one subStructureAssociation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubStructureAssociation> findOne(UUID id) {
        log.debug("Request to get SubStructureAssociation : {}", id);
        return subStructureAssociationRepository.findById(id);
    }

    /**
     * Delete the subStructureAssociation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete SubStructureAssociation : {}", id);
        subStructureAssociationRepository.deleteById(id);
    }
}
