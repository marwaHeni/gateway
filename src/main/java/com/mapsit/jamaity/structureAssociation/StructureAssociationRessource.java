package com.mapsit.jamaity.structureAssociation;

import com.mapsit.jamaity.domain.Domain;
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
public class StructureAssociationRessource {

    private final Logger log = LoggerFactory.getLogger(StructureAssociationRessource.class);

    private static final String ENTITY_NAME = "structureAssociation";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StructureAssociationService structureAssociationService;

    public StructureAssociationRessource(StructureAssociationService structureAssociationService) {

        this.structureAssociationService = structureAssociationService;
    }

    /**
     * {@code POST  /structure-associations} : Create a new structureAssociation.
     *
     * @param structureAssociation the structureAssociation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new structureAssociation, or with status {@code 400 (Bad Request)} if the structureAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/structure-associations")
    public ResponseEntity<StructureAssociation> createStructureAssociation(@RequestBody StructureAssociation structureAssociation) throws URISyntaxException {
        log.debug("REST request to save StructureAssociation : {}", structureAssociation);
        if (structureAssociation.getId() != null) {
            throw new BadRequestAlertException("A new structureAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StructureAssociation result = structureAssociationService.save(structureAssociation);
        return ResponseEntity.created(new URI("/api/structure-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /structure-associations} : Updates an existing structureAssociation.
     *
     * @param structureAssociation the structureAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated structureAssociation,
     * or with status {@code 400 (Bad Request)} if the structureAssociation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the structureAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/structure-associations")
    public ResponseEntity<StructureAssociation> updateStructureAssociation(@RequestBody StructureAssociation structureAssociation) throws URISyntaxException {
        log.debug("REST request to update StructureAssociation : {}", structureAssociation);
        if (structureAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StructureAssociation result = structureAssociationService.save(structureAssociation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, structureAssociation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /structure-associations} : get all the structureAssociation.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of structureAssociation in body.
     */
    @GetMapping("/structure-associations")
    public ResponseEntity<List<StructureAssociation>> getAllStructureAssociations(Pageable pageable) {
        log.debug("REST request to get a page of StructureAssociation");
        Page<StructureAssociation> page = structureAssociationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }



    /**
     * {@code GET  /structure-associations} : get all the structureAssociation without pagination.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of structureAssociation in body.
     */
    @GetMapping("/structure-associations/withoutPagination")
    public ResponseEntity<List<StructureAssociation>> getAllStructureAssociations() {
        log.debug("REST request to get a page of StructureAssociation");
        List<StructureAssociation> result = structureAssociationService.findAll();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /user-occupations/:id} : get the "id" structureAssociation.
     *
     * @param id the id of the structureAssociation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the structureAssociation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/structure-associations/{id}")
    public ResponseEntity<StructureAssociation> getStructureAssociation(@PathVariable UUID id) {
        log.debug("REST request to get StructureAssociation : {}", id);
        Optional<StructureAssociation> structureAssociation = structureAssociationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(structureAssociation);
    }

    /**
     * {@code DELETE  /structure-associations/:id} : delete the "id" structureAssociation.
     *
     * @param id the id of the structureAssociation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/structure-associations/{id}")
    public ResponseEntity<Void> deleteStructureAssociation(@PathVariable UUID id) {
        log.debug("REST request to delete StructureAssociation : {}", id);
        structureAssociationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
