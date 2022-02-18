package com.mapsit.jamaity.association;

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

import java.io.IOException;
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
public class AssociationRessource {

    private final Logger log = LoggerFactory.getLogger(AssociationRessource.class);

    private static final String ENTITY_NAME = "association";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssociationService associationService;

    public AssociationRessource(AssociationService associationService) {

        this.associationService = associationService;
    }

    /**
     * {@code POST  /associations} : Create a new association.
     *
     * @param association the association to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new association, or with status {@code 400 (Bad Request)} if the association has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/associations")
    public ResponseEntity<Association> createAssociation(@RequestBody Association association) throws URISyntaxException, IOException {
        log.debug("REST request to save Association : {}", association);
        if (association.getId() != null) {
            throw new BadRequestAlertException("A new association cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Association result = associationService.save(association);
        return ResponseEntity.created(new URI("/api/associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /associations} : Updates an existing association.
     *
     * @param association the association to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated association,
     * or with status {@code 400 (Bad Request)} if the association is not valid,
     * or with status {@code 500 (Internal Server Error)} if the association couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/associations")
    public ResponseEntity<Association> updateAssociation(@RequestBody Association association) throws URISyntaxException, IOException {
        log.debug("REST request to update Association : {}", association);
        if (association.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Association result = associationService.update(association);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, association.getId().toString()))
            .body(result);
      //  return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /associations} : get all the association.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of association in body.
     */
    @GetMapping("/associations")
    public ResponseEntity<List<Association>> getAllAssociations(Pageable pageable) {
        log.debug("REST request to get a page of Association");
        Page<Association> page = associationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /associations} : get all the association.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of association in body.
     */
    @GetMapping("/associations/lang/{lang}")
    public ResponseEntity<List<Association>> getAllAssociations(@PathVariable String lang, Pageable pageable) {
        log.debug("REST request to get a page of Association");
        Page<Association> page = associationService.findAll(lang, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /associations} : get all the association.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of association in body.
     */
    @GetMapping("/associations/withoutPagination")
    public ResponseEntity<List<Association>> getAllAssociations() {
        log.debug("REST request to get a page of Association");
        List<Association> result = associationService.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    /**
     * {@code GET  /associations} : get all the association.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of association in body.
     */
    @GetMapping("/associations/search/{attribute}/{lang}")
    public ResponseEntity<List<Association>> getAllAssociations(@PathVariable String attribute, @PathVariable String lang, Pageable pageable) {
        log.debug("REST request to get a page of Association");
        Page<Association> page = associationService.searchAssociation(attribute, lang, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    /**
     * {@code GET  /associations/:id} : get the "id" association.
     *
     * @param id the id of the association to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the association, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/associations/{id}")
    public ResponseEntity<Association> getAssociation(@PathVariable UUID id) {
        log.debug("REST request to get Association : {}", id);
        Optional<Association> association = associationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(association);
    }



    /**
     * {@code GET  /associations/:id} : get the "id" association translated.
     *
     * @param id the id of the association to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the association, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/associations/lang/{id}/{lang}")
    public ResponseEntity<Association> getAssociation(@PathVariable UUID id, @PathVariable String lang) {
        log.debug("REST request to get Association : {}", id);
        Optional<Association> association = associationService.findOne(id, lang);
        return ResponseUtil.wrapOrNotFound(association);
    }



    /**
     * {@code DELETE  /associations/:id} : delete the "id" association.
     *
     * @param id the id of the association to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/associations/{id}")
    public ResponseEntity<Void> deleteAssociation(@PathVariable UUID id) {
        log.debug("REST request to delete Association : {}", id);
        associationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
