package com.mapsit.jamaity.typePtf;

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
public class TypePtfRessource {

    private final Logger log = LoggerFactory.getLogger(TypePtfRessource.class);

    private static final String ENTITY_NAME = "typePtf";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypePtfService typePtfService;

    public TypePtfRessource(TypePtfService typePtfService) {

        this.typePtfService = typePtfService;
    }

    /**
     * {@code POST  /type-ptfs} : Create a new typePtf.
     *
     * @param typePtf the typePtf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typePtf, or with status {@code 400 (Bad Request)} if the typePtf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-ptfs")
    public ResponseEntity<TypePtf> createTypePtf(@RequestBody TypePtf typePtf) throws URISyntaxException {
        log.debug("REST request to save TypePtf : {}", typePtf);
        if (typePtf.getId() != null) {
            throw new BadRequestAlertException("A new typePtf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypePtf result = typePtfService.save(typePtf);
        return ResponseEntity.created(new URI("/api/type-ptfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /type-ptfs} : Updates an existing typePtf.
     *
     * @param typePtf the typePtf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typePtf,
     * or with status {@code 400 (Bad Request)} if the typePtf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typePtf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-ptfs")
    public ResponseEntity<TypePtf> updateTypePtf(@RequestBody TypePtf typePtf) throws URISyntaxException {
        log.debug("REST request to update TypePtf : {}", typePtf);
        if (typePtf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypePtf result = typePtfService.save(typePtf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typePtf.getId().toString()))
            .body(result);
      //  return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /type-ptfs} : get all the typePtf.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typePtf in body.
     */
    @GetMapping("/type-ptfs")
    public ResponseEntity<List<TypePtf>> getAllTypePtfs(Pageable pageable) {
        log.debug("REST request to get a page of TypePtf");
        Page<TypePtf> page = typePtfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @GetMapping("/type-ptfs/withoutPagination")
    public ResponseEntity<List<TypePtf>> getAllTypePtfs() {
        log.debug("REST request to get a page of TypePtf");
        List<TypePtf> result = typePtfService.findAll();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /type-ptfs/:id} : get the "id" typePtf.
     *
     * @param id the id of the typePtf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typePtf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-ptfs/{id}")
    public ResponseEntity<TypePtf> getTypePtf(@PathVariable UUID id) {
        log.debug("REST request to get TypePtf : {}", id);
        Optional<TypePtf> typePtf = typePtfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typePtf);
    }

    /**
     * {@code DELETE  /type-ptfs/:id} : delete the "id" typePtf.
     *
     * @param id the id of the typePtf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-ptfs/{id}")
    public ResponseEntity<Void> deleteTypePtf(@PathVariable UUID id) {
        log.debug("REST request to delete TypePtf : {}", id);
        typePtfService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
