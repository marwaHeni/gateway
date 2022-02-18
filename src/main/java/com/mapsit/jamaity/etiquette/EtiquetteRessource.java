package com.mapsit.jamaity.etiquette;

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
public class EtiquetteRessource {

    private final Logger log = LoggerFactory.getLogger(EtiquetteRessource.class);

    private static final String ENTITY_NAME = "etiquette";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtiquetteService etiquetteService;

    public EtiquetteRessource(EtiquetteService etiquetteService) {

        this.etiquetteService = etiquetteService;
    }

    /**
     * {@code POST  /etiquettes} : Create a new etiquette.
     *
     * @param etiquette the etiquette to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etiquette, or with status {@code 400 (Bad Request)} if the etiquette has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etiquettes")
    public ResponseEntity<Etiquette> createEtiquette(@RequestBody Etiquette etiquette) throws URISyntaxException {
        log.debug("REST request to save Etiquette : {}", etiquette);
        if (etiquette.getId() != null) {
            throw new BadRequestAlertException("A new etiquette cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etiquette result = etiquetteService.save(etiquette);
        return ResponseEntity.created(new URI("/api/etiquettes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /etiquettes} : Updates an existing etiquette.
     *
     * @param etiquette the etiquette to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etiquette,
     * or with status {@code 400 (Bad Request)} if the etiquette is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etiquette couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etiquettes")
    public ResponseEntity<Etiquette> updateEtiquette(@RequestBody Etiquette etiquette) throws URISyntaxException {
        log.debug("REST request to update Etiquette : {}", etiquette);
        if (etiquette.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Etiquette result = etiquetteService.save(etiquette);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etiquette.getId().toString()))
            .body(result);
      //  return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /etiquettes} : get all the etiquette.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etiquette in body.
     */
    @GetMapping("/etiquettes")
    public ResponseEntity<List<Etiquette>> getAllEtiquettes(Pageable pageable) {
        log.debug("REST request to get a page of Etiquette");
        Page<Etiquette> page = etiquetteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/etiquettes/withoutPagination")
    public ResponseEntity<List<Etiquette>> getAllEtiquettes() {
        log.debug("REST request to get a page of Etiquette");
        List<Etiquette> result = etiquetteService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /etiquettes/:id} : get the "id" etiquette.
     *
     * @param id the id of the etiquette to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etiquette, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etiquettes/{id}")
    public ResponseEntity<Etiquette> getEtiquette(@PathVariable UUID id) {
        log.debug("REST request to get Etiquette : {}", id);
        Optional<Etiquette> etiquette = etiquetteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etiquette);
    }

    /**
     * {@code DELETE  /etiquettes/:id} : delete the "id" etiquette.
     *
     * @param id the id of the etiquette to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etiquettes/{id}")
    public ResponseEntity<Void> deleteEtiquette(@PathVariable UUID id) {
        log.debug("REST request to delete Etiquette : {}", id);
        etiquetteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
