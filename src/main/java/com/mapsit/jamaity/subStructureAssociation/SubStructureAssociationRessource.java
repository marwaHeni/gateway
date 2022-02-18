package com.mapsit.jamaity.subStructureAssociation;

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
public class SubStructureAssociationRessource {

    private final Logger log = LoggerFactory.getLogger(SubStructureAssociationRessource.class);

    private static final String ENTITY_NAME = "subStructureAssociation";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubStructureAssociationService subStructureAssociationService;

    public SubStructureAssociationRessource(SubStructureAssociationService subStructureAssociationService) {
        this.subStructureAssociationService = subStructureAssociationService;
    }

    /**
     * {@code POST  /sub-structure-associations} : Create a new subStructureAssociation.
     *
     * @param subStructureAssociation the subStructureAssociation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subStructureAssociation, or with status {@code 400 (Bad Request)} if the subStructureAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sub-structure-associations")
    public ResponseEntity<SubStructureAssociation> createSubStructureAssociation(@RequestBody SubStructureAssociation subStructureAssociation) throws URISyntaxException {
        log.debug("REST request to save SubStructureAssociation : {}", subStructureAssociation);
        if (subStructureAssociation.getId() != null) {
            throw new BadRequestAlertException("A new SubStructureAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubStructureAssociation result = subStructureAssociationService.save(subStructureAssociation);
        return ResponseEntity.created(new URI("/api/sub-structure-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /sub-structure-associations} : Updates an existing subStructureAssociation.
     *
     * @param subStructureAssociation the subStructureAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subStructureAssociation,
     * or with status {@code 400 (Bad Request)} if the subStructureAssociation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subStructureAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sub-structure-associations")
    public ResponseEntity<SubStructureAssociation> updateSubStructureAssociation(@RequestBody SubStructureAssociation subStructureAssociation) throws URISyntaxException {
        log.debug("REST request to update SubStructureAssociation : {}", subStructureAssociation);
        if (subStructureAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubStructureAssociation result = subStructureAssociationService.save(subStructureAssociation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subStructureAssociation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sub-structure-associations} : get all the subStructureAssociation.
     *
 //    * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subStructureAssociation in body.
     */
    @GetMapping("/sub-structure-associations")
    public ResponseEntity<List<SubStructureAssociation>> getAllSubStructureAssociations() {
        log.debug("REST request to get a page of SubStructureAssociation");
        List<SubStructureAssociation> result = subStructureAssociationService.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }



    /**
     * {@code GET  /sub-structure-associations} : get all the subStructureAssociation translated.
     *
     //    * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subStructureAssociation in body.
     */
    @GetMapping("/sub-structure-associations/lang/{lang}")
    public ResponseEntity<List<SubStructureAssociation>> getAllSubStructureAssociations(@PathVariable String lang) {
        log.debug("REST request to get a page of SubStructureAssociation translated");
        List<SubStructureAssociation> result = subStructureAssociationService.findAll(lang);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * {@code GET  /sub-structure-associations/:id} : get the "id" subStructureAssociation.
     *
     * @param id the id of the subStructureAssociation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subStructureAssociation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sub-structure-associations/{id}")
    public ResponseEntity<SubStructureAssociation> getSubStructureAssociation(@PathVariable UUID id) {
        log.debug("REST request to get SubStructureAssociation : {}", id);
        Optional<SubStructureAssociation> subStructureAssociation = subStructureAssociationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subStructureAssociation);
    }

    /**
     * {@code DELETE  /sub-structure-associations/:id} : delete the "id" subStructureAssociation.
     *
     * @param id the id of the subStructureAssociation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sub-structure-associations/{id}")
    public ResponseEntity<Void> deleteSubStructureAssociation(@PathVariable UUID id) {
        log.debug("REST request to delete SubStructureAssociation : {}", id);
        subStructureAssociationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
