package co.edu.udistrital.software.broadcast.web.rest;

import co.edu.udistrital.software.broadcast.domain.Day;
import co.edu.udistrital.software.broadcast.repository.DayRepository;
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
 * REST controller for managing {@link co.edu.udistrital.software.broadcast.domain.Day}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DayResource {

    private final Logger log = LoggerFactory.getLogger(DayResource.class);

    private static final String ENTITY_NAME = "day";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DayRepository dayRepository;

    public DayResource(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    /**
     * {@code POST  /days} : Create a new day.
     *
     * @param day the day to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new day, or with status {@code 400 (Bad Request)} if the day has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/days")
    public ResponseEntity<Day> createDay(@RequestBody Day day) throws URISyntaxException {
        log.debug("REST request to save Day : {}", day);
        if (day.getId() != null) {
            throw new BadRequestAlertException("A new day cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Day result = dayRepository.save(day);
        return ResponseEntity.created(new URI("/api/days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /days} : Updates an existing day.
     *
     * @param day the day to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated day,
     * or with status {@code 400 (Bad Request)} if the day is not valid,
     * or with status {@code 500 (Internal Server Error)} if the day couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/days")
    public ResponseEntity<Day> updateDay(@RequestBody Day day) throws URISyntaxException {
        log.debug("REST request to update Day : {}", day);
        if (day.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Day result = dayRepository.save(day);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, day.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /days} : get all the days.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of days in body.
     */
    @GetMapping("/days")
    public List<Day> getAllDays() {
        log.debug("REST request to get all Days");
        return dayRepository.findAll();
    }

    /**
     * {@code GET  /days/:id} : get the "id" day.
     *
     * @param id the id of the day to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the day, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/days/{id}")
    public ResponseEntity<Day> getDay(@PathVariable Long id) {
        log.debug("REST request to get Day : {}", id);
        Optional<Day> day = dayRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(day);
    }

    /**
     * {@code DELETE  /days/:id} : delete the "id" day.
     *
     * @param id the id of the day to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/days/{id}")
    public ResponseEntity<Void> deleteDay(@PathVariable Long id) {
        log.debug("REST request to delete Day : {}", id);
        dayRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
