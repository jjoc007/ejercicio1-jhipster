package co.edu.udistrital.software.broadcast.web.rest;

import co.edu.udistrital.software.broadcast.domain.Channel;
import co.edu.udistrital.software.broadcast.repository.ChannelRepository;
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
 * REST controller for managing {@link co.edu.udistrital.software.broadcast.domain.Channel}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChannelResource {

    private final Logger log = LoggerFactory.getLogger(ChannelResource.class);

    private static final String ENTITY_NAME = "channel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChannelRepository channelRepository;

    public ChannelResource(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    /**
     * {@code POST  /channels} : Create a new channel.
     *
     * @param channel the channel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new channel, or with status {@code 400 (Bad Request)} if the channel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/channels")
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) throws URISyntaxException {
        log.debug("REST request to save Channel : {}", channel);
        if (channel.getId() != null) {
            throw new BadRequestAlertException("A new channel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Channel result = channelRepository.save(channel);
        return ResponseEntity.created(new URI("/api/channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /channels} : Updates an existing channel.
     *
     * @param channel the channel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channel,
     * or with status {@code 400 (Bad Request)} if the channel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the channel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/channels")
    public ResponseEntity<Channel> updateChannel(@RequestBody Channel channel) throws URISyntaxException {
        log.debug("REST request to update Channel : {}", channel);
        if (channel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Channel result = channelRepository.save(channel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /channels} : get all the channels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of channels in body.
     */
    @GetMapping("/channels")
    public List<Channel> getAllChannels() {
        log.debug("REST request to get all Channels");
        return channelRepository.findAll();
    }

    /**
     * {@code GET  /channels/:id} : get the "id" channel.
     *
     * @param id the id of the channel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the channel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/channels/{id}")
    public ResponseEntity<Channel> getChannel(@PathVariable Long id) {
        log.debug("REST request to get Channel : {}", id);
        Optional<Channel> channel = channelRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(channel);
    }

    /**
     * {@code DELETE  /channels/:id} : delete the "id" channel.
     *
     * @param id the id of the channel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/channels/{id}")
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        log.debug("REST request to delete Channel : {}", id);
        channelRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
