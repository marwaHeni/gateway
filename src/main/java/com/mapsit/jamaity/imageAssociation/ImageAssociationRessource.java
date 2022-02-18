package com.mapsit.jamaity.imageAssociation;

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
public class ImageAssociationRessource {

    private final Logger log = LoggerFactory.getLogger(ImageAssociationRessource.class);

    private static final String ENTITY_NAME = "imageAssociation";

 //   @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImageAssociationService imageAssociationService;

    public ImageAssociationRessource(ImageAssociationService imageAssociationService) {

        this.imageAssociationService = imageAssociationService;
    }

    /**
     * {@code POST  /image-associations} : Create a new imageAssociation.
     *
     * @param imageAssociation the imageAssociation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imageAssociation, or with status {@code 400 (Bad Request)} if the imageAssociation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/image-associations")
    public ResponseEntity<ImageAssociation> createImageAssociation(@RequestBody ImageAssociation imageAssociation) throws URISyntaxException {
        log.debug("REST request to save ImageAssociation : {}", imageAssociation);
        if (imageAssociation.getId() != null) {
            throw new BadRequestAlertException("A new imageAssociation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImageAssociation result = imageAssociationService.save(imageAssociation);
        return ResponseEntity.created(new URI("/api/image-associations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
     //   return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code PUT  /image-associations} : Updates an existing imageAssociation.
     *
     * @param imageAssociation the imageAssociation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imageAssociation,
     * or with status {@code 400 (Bad Request)} if the imageAssociation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imageAssociation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/image-associations")
    public ResponseEntity<ImageAssociation> updateImageAssociation(@RequestBody ImageAssociation imageAssociation) throws URISyntaxException {
        log.debug("REST request to update ImageAssociation : {}", imageAssociation);
        if (imageAssociation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImageAssociation result = imageAssociationService.save(imageAssociation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, imageAssociation.getId().toString()))
            .body(result);
      //  return  new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * {@code GET  /image-associations} : get all the imageAssociation.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imageAssociation in body.
     */
    @GetMapping("/image-associations")
    public ResponseEntity<List<ImageAssociation>> getAllImageAssociations(Pageable pageable) {
        log.debug("REST request to get a page of ImageAssociation");
        Page<ImageAssociation> page = imageAssociationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /image-associations/:id} : get the "id" imageAssociation.
     *
     * @param id the id of the imageAssociation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imageAssociation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/image-associations/{id}")
        public ResponseEntity<ImageAssociation> getImageAssociation(@PathVariable Long id) {
        log.debug("REST request to get ImageAssociation : {}", id);
        Optional<ImageAssociation> imageAssociation = imageAssociationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imageAssociation);
    }

    /**
     * {@code DELETE  /image-associations/:id} : delete the "id" imageAssociation.
     *
     * @param id the id of the imageAssociation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/image-associations/{id}")
    public ResponseEntity<Void> deleteImageAssociation(@PathVariable Long id) {
        log.debug("REST request to delete ImageAssociation : {}", id);
        imageAssociationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
