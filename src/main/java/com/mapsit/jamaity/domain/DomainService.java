package com.mapsit.jamaity.domain;

import com.mapsit.jamaity.subDomain.SubDomain;
import com.mapsit.jamaity.subDomain.SubDomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Service Implementation
 */
@Service
@Transactional
public class DomainService {

    private final Logger log = LoggerFactory.getLogger(DomainService.class);

    private final DomainRepository domainRepository;

    private final SubDomainRepository subDomainRepository;

    public DomainService(DomainRepository domainRepository, SubDomainRepository subDomainRepository) {

        this.domainRepository = domainRepository;
        this.subDomainRepository = subDomainRepository;
    }

    /**
     * Save a domain.
     *
     * @param domain the entity to save.
     * @return the persisted entity.
     */
    public Domain save(Domain domain) {
        log.debug("Request to save Domain : {}", domain);
        Domain savedDomain =  domainRepository.save(domain);
        SubDomain subDomain = new SubDomain();
        subDomain.setNameAr("إفتراضي");
        subDomain.setNameFr("defaut");
        subDomain.setNameEn("default");
        subDomain.setDomain(savedDomain);
        subDomainRepository.save(subDomain);
        return savedDomain;
    }


    /**
     * Save a domain.
     *
     * @param domain the entity to save.
     * @return the persisted entity.
     */
    public Domain update(Domain domain) {
        log.debug("Request to save Domain : {}", domain);
        return domainRepository.save(domain);
    }

    /**
     * Get all the domains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Domain> findAll(Pageable pageable) {
        log.debug("Request to get all Domains");
        return domainRepository.findAll(pageable);
    }


    /**
     * Get all the domains without pagination.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Domain> findAll() {
        log.debug("Request to get all Domains without pagination");
        return domainRepository.findAll();
    }


    /**
     * Get one domain by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Domain> findOne(UUID id) {
        log.debug("Request to get Domain : {}", id);
        return domainRepository.findById(id);
    }

    /**
     * Delete the domain by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete Domain : {}", id);
        Optional<Domain> domain = domainRepository.findById(id);
        List<SubDomain> subDomainList = domain.get().getSubDomains();
        SubDomain subDomain = subDomainList.get(0);
        if(subDomainList.size() == 1 && subDomain.getNameEn().equals(("default"))){

            subDomainRepository.delete(subDomain);
        }
        domainRepository.deleteById(id);
    }
}
