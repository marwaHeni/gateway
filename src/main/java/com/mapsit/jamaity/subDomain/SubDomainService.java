package com.mapsit.jamaity.subDomain;

import com.mapsit.jamaity.subStructureAssociation.SubStructureAssociation;
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
public class SubDomainService {

    private final Logger log = LoggerFactory.getLogger(SubDomainService.class);

    private final SubDomainRepository subDomainRepository;

    public SubDomainService(SubDomainRepository subDomainRepository) {

        this.subDomainRepository = subDomainRepository;
    }

    /**
     * Save a subDomain.
     *
     * @param subDomain the entity to save.
     * @return the persisted entity.
     */
    public SubDomain save(SubDomain subDomain) {
        log.debug("Request to save SubDomain : {}", subDomain);
        return subDomainRepository.save(subDomain);
    }

//    /**
//     * Get all the subDomains.
//     *
//     * @param pageable the pagination information.
//     * @return the list of entities.
//     */
//    @Transactional(readOnly = true)
//    public Page<SubDomain> findAll(Pageable pageable) {
//        log.debug("Request to get all SubDomains");
//        return subDomainRepository.findAll(pageable);
//    }


    /**
     * Get all the subDomains.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubDomain> findAll() {
        log.debug("Request to get all SubDomains");
        return subDomainRepository.findAll();
    }


    /**
     * Get all the subDomains translated.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubDomain> findAll(String lang) {
        log.debug("Request to get all SubDomains translated");
        List<SubDomain> subDomainList = subDomainRepository.findAll();
        List<SubDomain> subDomainTranslatedList = new ArrayList<>();


        if (subDomainList != null && subDomainList.size() != 0) {
            for (SubDomain subDomain : subDomainList) {


                if (lang.equals("fr")) {

                    SubDomain subDomainTranslated = new SubDomain();
                    subDomainTranslated.setId(subDomain.getId());
                    if(subDomain.getNameFr().equals("defaut")) {
                        System.out.println("aaaaaaaaaaaa");
                        subDomainTranslated.setName(subDomain.getDomain().getNameFr());
                    }
                    else
                    {
                        subDomainTranslated.setName(subDomain.getNameFr());
                    }
                    subDomainTranslated.setSlug(subDomain.getSlug());
                    subDomainTranslated.setDescription(subDomain.getDescriptionFr());

                    subDomainTranslatedList.add(subDomainTranslated);

                }


                if (lang.equals("ar")) {

                    SubDomain subDomainTranslated = new SubDomain();
                    subDomainTranslated.setId(subDomain.getId());
                    if(subDomain.getNameAr().equals("إفتراضي")) {
                        subDomainTranslated.setName(subDomain.getDomain().getNameAr());
                    }
                    else
                    {
                        subDomainTranslated.setName(subDomain.getNameAr());
                    }
                    subDomainTranslated.setSlug(subDomain.getSlug());
                    subDomainTranslated.setDescription(subDomain.getDescriptionAr());

                    subDomainTranslatedList.add(subDomainTranslated);

                }

            }
        }

        return subDomainTranslatedList;
    }


    /**
     * Get one subDomain by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubDomain> findOne(UUID id) {
        log.debug("Request to get SubDomain : {}", id);
        return subDomainRepository.findById(id);
    }

    /**
     * Delete the subDomain by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete SubDomain : {}", id);
        subDomainRepository.deleteById(id);
    }
}
