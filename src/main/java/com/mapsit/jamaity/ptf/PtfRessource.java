package com.mapsit.jamaity.ptf;

import com.mapsit.jamaity.association.Association;
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
public class PtfRessource {

    private final Logger log = LoggerFactory.getLogger(PtfRessource.class);

    private static final String ENTITY_NAME = "ptf";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PtfService ptfService;

    public PtfRessource(PtfService ptfService) {

        this.ptfService = ptfService;
    }

    /**
     * {@code POST  /ptfs} : Create a new ptf.
     *
     * @param ptf the ptf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ptf, or with status {@code 400 (Bad Request)} if the ptf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ptfs")
    public ResponseEntity<Ptf> createPtf(@RequestBody Ptf ptf) throws URISyntaxException, IOException {
        log.debug("REST request to save Ptf : {}", ptf);
        if (ptf.getId() != null) {
            throw new BadRequestAlertException("A new ptf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ptf result = ptfService.save(ptf);
        return ResponseEntity.created(new URI("/api/ptfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /ptfs} : Updates an existing ptf.
     *
     * @param ptf the ptf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ptf,
     * or with status {@code 400 (Bad Request)} if the ptf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ptf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ptfs")
    public ResponseEntity<Ptf> updatePtf(@RequestBody Ptf ptf) throws URISyntaxException, IOException {
        log.debug("REST request to update Ptf : {}", ptf);
        if (ptf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ptf result = ptfService.save(ptf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ptf.getId().toString()))
            .body(result);
      //  return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /ptfs} : get all the ptf.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ptf in body.
     */
    @GetMapping("/ptfs")
    public ResponseEntity<List<Ptf>> getAllPtfs(Pageable pageable) {
        log.debug("REST request to get a page of Ptf");
        Page<Ptf> page = ptfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /ptfs} : get all the ptf translated.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ptf in body.
     */
    @GetMapping("/ptfs/lang/{lang}")
    public ResponseEntity<List<Ptf>> getAllAssociations(@PathVariable String lang, Pageable pageable) {
        log.debug("REST request to get a page of Ptf translated");
        Page<Ptf> page = ptfService.findAll(lang, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/ptfs/withoutPagination")
    public ResponseEntity<List<Ptf>> getAllPtfs() {
        log.debug("REST request to get a page of Ptf");
        List<Ptf> result = ptfService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /ptfs/:id} : get the "id" ptf.
     *
     * @param id the id of the ptf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ptf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ptfs/{id}")
    public ResponseEntity<Ptf> getPtf(@PathVariable UUID id) {
        log.debug("REST request to get Ptf : {}", id);
        Optional<Ptf> ptf = ptfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ptf);
    }


    /**
     * {@code GET  /ptfs/:id} : get the "id" ptf.
     *
     * @param id the id of the ptf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ptf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ptfs/lang/{id}/{lang}")
    public ResponseEntity<Ptf> getPtf(@PathVariable UUID id, @PathVariable String lang) {
        log.debug("REST request to get Ptf : {}", id);
        Optional<Ptf> ptf = ptfService.findOne(id, lang);
        return ResponseUtil.wrapOrNotFound(ptf);
    }



    /**
     * {@code DELETE  /ptfs/:id} : delete the "id" ptf.
     *
     * @param id the id of the ptf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ptfs/{id}")
    public ResponseEntity<Void> deletePtf(@PathVariable UUID id) {
        log.debug("REST request to delete Ptf : {}", id);
        ptfService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }



    /**
     * {@code GET  /ptfs} : get all the ptf.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ptf in body.
     */
    @GetMapping("/ptfs/search/{attribute}/{lang}")
    public ResponseEntity<List<Ptf>> getAllPtfs(@PathVariable String attribute, @PathVariable String lang, Pageable pageable) {
        log.debug("REST request to get a page of Association");
        Page<Ptf> page = ptfService.searchPtf(attribute, lang, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
