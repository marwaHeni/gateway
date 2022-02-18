package com.mapsit.jamaity.prixAssociation;

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
public class PrixAssociationRessource {

    private final Logger log = LoggerFactory.getLogger(PrixAssociationRessource.class);

    private static final String ENTITY_NAME = "prixAssociation";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrixAssociationService prixAssociationService;

    public PrixAssociationRessource(PrixAssociationService prixAssociationService) {

        this.prixAssociationService = prixAssociationService;
    }

    /**
     * {@code POST  /prix-associations} : Create a new prixAssociation.
     *
     * @param prixAssociation the prixAssociation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prixAssociation, or with status {@code 400 (Bad Request)} if the prixAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prix-associations")
    public ResponseEntity<PrixAssociation> createPrixAssociation(@RequestBody PrixAssociation prixAssociation) throws URISyntaxException {
        log.debug("REST request to save PrixAssociation : {}", prixAssociation);
        if (prixAssociation.getId() != null) {
            throw new BadRequestAlertException("A new prixAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrixAssociation result = prixAssociationService.save(prixAssociation);
        return ResponseEntity.created(new URI("/api/prix-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /prix-associations} : Updates an existing prixAssociation.
     *
     * @param prixAssociation the prixAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prixAssociation,
     * or with status {@code 400 (Bad Request)} if the prixAssociation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prixAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prix-associations")
    public ResponseEntity<PrixAssociation> updatePrixAssociation(@RequestBody PrixAssociation prixAssociation) throws URISyntaxException {
        log.debug("REST request to update PrixAssociation : {}", prixAssociation);
        if (prixAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrixAssociation result = prixAssociationService.save(prixAssociation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, prixAssociation.getId().toString()))
            .body(result);
      //  return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /prix-associations} : get all the prixAssociation.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prixAssociation in body.
     */
    @GetMapping("/prix-associations")
    public ResponseEntity<List<PrixAssociation>> getAllPrixAssociations(Pageable pageable) {
        log.debug("REST request to get a page of PrixAssociation");
        Page<PrixAssociation> page = prixAssociationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /prix-associations} : get all the prixAssociation.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prixAssociation in body.
     */
    @GetMapping("/prix-associations/withoutPagination")
    public ResponseEntity<List<PrixAssociation>> getAllPrixAssociations() {
        log.debug("REST request to get a page of PrixAssociation");
        List<PrixAssociation> result = prixAssociationService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * {@code GET  /prix-associations/:id} : get the "id" prixAssociation.
     *
     * @param id the id of the prixAssociation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prixAssociation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prix-associations/{id}")
    public ResponseEntity<PrixAssociation> getPrixAssociation(@PathVariable UUID id) {
        log.debug("REST request to get PrixAssociation : {}", id);
        Optional<PrixAssociation> prixAssociation = prixAssociationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prixAssociation);
    }

    /**
     * {@code DELETE  /prix-associations/:id} : delete the "id" prixAssociation.
     *
     * @param id the id of the prixAssociation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prix-associations/{id}")
    public ResponseEntity<Void> deletePrixAssociation(@PathVariable UUID id) {
        log.debug("REST request to delete PrixAssociation : {}", id);
        prixAssociationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
