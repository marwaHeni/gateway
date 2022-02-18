package com.mapsit.jamaity.subDomain;

import com.mapsit.jamaity.errors.BadRequestAlertException;
import com.mapsit.jamaity.etiquette.Etiquette;
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
public class SubDomainRessource {

    private final Logger log = LoggerFactory.getLogger(SubDomainRessource.class);

    private static final String ENTITY_NAME = "subDomain";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubDomainService subDomainService;

    public SubDomainRessource(SubDomainService subDomainService) {

        this.subDomainService = subDomainService;
    }

    /**
     * {@code POST  /sub-domains} : Create a new subDomain.
     *
     * @param subDomain the subDomain to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subDomain, or with status {@code 400 (Bad Request)} if the subDomain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-domains")
    public ResponseEntity<SubDomain> createSubDomain(@RequestBody SubDomain subDomain) throws URISyntaxException {
        log.debug("REST request to save SubDomain : {}", subDomain);
        if (subDomain.getId() != null) {
            throw new BadRequestAlertException("A new SubDomain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubDomain result = subDomainService.save(subDomain);
        return ResponseEntity.created(new URI("/api/sub-domains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /sub-domains} : Updates an existing subDomain.
     *
     * @param subDomain the subDomain to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subDomain,
     * or with status {@code 400 (Bad Request)} if the subDomain is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subDomain couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-domains")
    public ResponseEntity<SubDomain> updateSubDomain(@RequestBody SubDomain subDomain) throws URISyntaxException {
        log.debug("REST request to update SubDomain : {}", subDomain);
        if (subDomain.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubDomain result = subDomainService.save(subDomain);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subDomain.getId().toString()))
            .body(result);
      //  return  new ResponseEntity<>(result, HttpStatus.OK);
    }

//    /**
//     * {@code GET  /sub-domains} : get all the subDomain.
//     *
//     * @param pageable the pagination information.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subDomain in body.
//     */
//    @GetMapping("/sub-domains")
//    public ResponseEntity<List<SubDomain>> getAllSubDomains(Pageable pageable) {
//        log.debug("REST request to get a page of SubDomain");
//        Page<SubDomain> page = subDomainService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }



    /**
     * {@code GET  /sub-domains} : get all the subDomain.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subDomain in body.
     */
    @GetMapping("/sub-domains")
    public ResponseEntity<List<SubDomain>> getAllSubDomains() {
        log.debug("REST request to get a page of SubDomain");
        List<SubDomain> result = subDomainService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    /**
     * {@code GET  /sub-domains} : get all the subDomain translated.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subDomain in body.
     */
    @GetMapping("/sub-domains/lang/{lang}")
    public ResponseEntity<List<SubDomain>> getAllSubDomains(@PathVariable String lang) {
        log.debug("REST request to get a page of SubDomain");
        List<SubDomain> result = subDomainService.findAll(lang);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    /**
     * {@code GET  /sub-domains/:id} : get the "id" subDomain.
     *
     * @param id the id of the subDomain to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subDomain, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-domains/{id}")
    public ResponseEntity<SubDomain> getSubDomain(@PathVariable UUID id) {
        log.debug("REST request to get SubDomain : {}", id);
        Optional<SubDomain> subDomain = subDomainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subDomain);
    }

    /**
     * {@code DELETE  /sub-domains/:id} : delete the "id" subDomain.
     *
     * @param id the id of the subDomain to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-domains/{id}")
    public ResponseEntity<Void> deleteSubDomain(@PathVariable UUID id) {
        log.debug("REST request to delete SubDomain : {}", id);
        subDomainService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
