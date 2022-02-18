package com.mapsit.jamaity.domain;

import com.mapsit.jamaity.errors.BadRequestAlertException;
import com.mapsit.jamaity.utility.HeaderUtil;
import com.mapsit.jamaity.utility.PaginationUtil;
import com.mapsit.jamaity.utility.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller
 */
@RestController
@RequestMapping("/api")
public class DomainRessource {

    private final Logger log = LoggerFactory.getLogger(DomainRessource.class);

    private static final String ENTITY_NAME = "domain";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomainService domainService;

    public DomainRessource(DomainService domainService) {

        this.domainService = domainService;
    }

    /**
     * {@code POST  /domains} : Create a new domain.
     *
     * @param domain the domain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new domain, or with status {@code 400 (Bad Request)} if the domain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/domains")
    public ResponseEntity<Domain> createDomain(@RequestBody Domain domain) throws URISyntaxException {
        log.debug("REST request to save Domain : {}", domain);
        if (domain.getId() != null) {
            throw new BadRequestAlertException("A new domain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Domain result = domainService.save(domain);
        return ResponseEntity.created(new URI("/api/domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /domains} : Updates an existing domain.
     *
     * @param domain the domain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domain,
     * or with status {@code 400 (Bad Request)} if the domain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the domain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/domains")
    public ResponseEntity<Domain> updateDomain(@RequestBody Domain domain) throws URISyntaxException {
        log.debug("REST request to update Domain : {}", domain);
        if (domain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Domain result = domainService.update(domain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domain.getId().toString()))
            .body(result);
      //  return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /domains} : get all the domain.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domain in body.
     */
    @GetMapping("/domains")
    public ResponseEntity<List<Domain>> getAllDomains(Pageable pageable) {
        log.debug("REST request to get a page of Domain");
        Page<Domain> page = domainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /domains} : get all the domain.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domain in body.
     */
    @GetMapping("/domains/withoutPagination")
    public ResponseEntity<List<Domain>> getAllDomains() {
        log.debug("REST request to get a page of Domain");
        List<Domain> result = domainService.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /user-occupations/:id} : get the "id" domain.
     *
     * @param id the id of the domain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/domains/{id}")
    public ResponseEntity<Domain> getDomain(@PathVariable UUID id) {
        log.debug("REST request to get Domain : {}", id);
        Optional<Domain> domain = domainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domain);
    }

    /**
     * {@code DELETE  /domains/:id} : delete the "id" domain.
     *
     * @param id the id of the domain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/domains/{id}")
    public ResponseEntity<Void> deleteDomain(@PathVariable UUID id) {
        log.debug("REST request to delete Domain : {}", id);
        domainService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
