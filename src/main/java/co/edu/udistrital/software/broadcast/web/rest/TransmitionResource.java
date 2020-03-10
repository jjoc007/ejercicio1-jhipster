package co.edu.udistrital.software.broadcast.web.rest;

import co.edu.udistrital.software.broadcast.domain.Transmition;
import co.edu.udistrital.software.broadcast.repository.TransmitionRepository;
import co.edu.udistrital.software.broadcast.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.udistrital.software.broadcast.domain.Transmition}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TransmitionResource {

    private final Logger log = LoggerFactory.getLogger(TransmitionResource.class);

    private static final String ENTITY_NAME = "transmition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransmitionRepository transmitionRepository;

    public TransmitionResource(TransmitionRepository transmitionRepository) {
        this.transmitionRepository = transmitionRepository;
    }

    /**
     * {@code POST  /transmitions} : Create a new transmition.
     *
     * @param transmition the transmition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transmition, or with status {@code 400 (Bad Request)} if the transmition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transmitions")
    public ResponseEntity<Transmition> createTransmition(@RequestBody Transmition transmition) throws URISyntaxException {
        log.debug("REST request to save Transmition : {}", transmition);
        if (transmition.getId() != null) {
            throw new BadRequestAlertException("A new transmition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Transmition result = transmitionRepository.save(transmition);
        return ResponseEntity.created(new URI("/api/transmitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transmitions} : Updates an existing transmition.
     *
     * @param transmition the transmition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transmition,
     * or with status {@code 400 (Bad Request)} if the transmition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transmition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transmitions")
    public ResponseEntity<Transmition> updateTransmition(@RequestBody Transmition transmition) throws URISyntaxException {
        log.debug("REST request to update Transmition : {}", transmition);
        if (transmition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Transmition result = transmitionRepository.save(transmition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transmition.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transmitions} : get all the transmitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transmitions in body.
     */
    @GetMapping("/transmitions")
    public List<Transmition> getAllTransmitions() {
        log.debug("REST request to get all Transmitions");
        return transmitionRepository.findAll();
    }

    /**
     * {@code GET  /transmitions/:id} : get the "id" transmition.
     *
     * @param id the id of the transmition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transmition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transmitions/{id}")
    public ResponseEntity<Transmition> getTransmition(@PathVariable Long id) {
        log.debug("REST request to get Transmition : {}", id);
        Optional<Transmition> transmition = transmitionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(transmition);
    }

    /**
     * {@code DELETE  /transmitions/:id} : delete the "id" transmition.
     *
     * @param id the id of the transmition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transmitions/{id}")
    public ResponseEntity<Void> deleteTransmition(@PathVariable Long id) {
        log.debug("REST request to delete Transmition : {}", id);
        transmitionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
