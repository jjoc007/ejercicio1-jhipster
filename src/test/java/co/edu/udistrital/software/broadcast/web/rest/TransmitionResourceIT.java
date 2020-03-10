package co.edu.udistrital.software.broadcast.web.rest;

import co.edu.udistrital.software.broadcast.BroadcastApp;
import co.edu.udistrital.software.broadcast.domain.Transmition;
import co.edu.udistrital.software.broadcast.repository.TransmitionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TransmitionResource} REST controller.
 */
@SpringBootTest(classes = BroadcastApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TransmitionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TransmitionRepository transmitionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransmitionMockMvc;

    private Transmition transmition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transmition createEntity(EntityManager em) {
        Transmition transmition = new Transmition()
            .name(DEFAULT_NAME);
        return transmition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Transmition createUpdatedEntity(EntityManager em) {
        Transmition transmition = new Transmition()
            .name(UPDATED_NAME);
        return transmition;
    }

    @BeforeEach
    public void initTest() {
        transmition = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransmition() throws Exception {
        int databaseSizeBeforeCreate = transmitionRepository.findAll().size();

        // Create the Transmition
        restTransmitionMockMvc.perform(post("/api/transmitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transmition)))
            .andExpect(status().isCreated());

        // Validate the Transmition in the database
        List<Transmition> transmitionList = transmitionRepository.findAll();
        assertThat(transmitionList).hasSize(databaseSizeBeforeCreate + 1);
        Transmition testTransmition = transmitionList.get(transmitionList.size() - 1);
        assertThat(testTransmition.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTransmitionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transmitionRepository.findAll().size();

        // Create the Transmition with an existing ID
        transmition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransmitionMockMvc.perform(post("/api/transmitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transmition)))
            .andExpect(status().isBadRequest());

        // Validate the Transmition in the database
        List<Transmition> transmitionList = transmitionRepository.findAll();
        assertThat(transmitionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTransmitions() throws Exception {
        // Initialize the database
        transmitionRepository.saveAndFlush(transmition);

        // Get all the transmitionList
        restTransmitionMockMvc.perform(get("/api/transmitions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transmition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTransmition() throws Exception {
        // Initialize the database
        transmitionRepository.saveAndFlush(transmition);

        // Get the transmition
        restTransmitionMockMvc.perform(get("/api/transmitions/{id}", transmition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transmition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingTransmition() throws Exception {
        // Get the transmition
        restTransmitionMockMvc.perform(get("/api/transmitions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransmition() throws Exception {
        // Initialize the database
        transmitionRepository.saveAndFlush(transmition);

        int databaseSizeBeforeUpdate = transmitionRepository.findAll().size();

        // Update the transmition
        Transmition updatedTransmition = transmitionRepository.findById(transmition.getId()).get();
        // Disconnect from session so that the updates on updatedTransmition are not directly saved in db
        em.detach(updatedTransmition);
        updatedTransmition
            .name(UPDATED_NAME);

        restTransmitionMockMvc.perform(put("/api/transmitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTransmition)))
            .andExpect(status().isOk());

        // Validate the Transmition in the database
        List<Transmition> transmitionList = transmitionRepository.findAll();
        assertThat(transmitionList).hasSize(databaseSizeBeforeUpdate);
        Transmition testTransmition = transmitionList.get(transmitionList.size() - 1);
        assertThat(testTransmition.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTransmition() throws Exception {
        int databaseSizeBeforeUpdate = transmitionRepository.findAll().size();

        // Create the Transmition

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransmitionMockMvc.perform(put("/api/transmitions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transmition)))
            .andExpect(status().isBadRequest());

        // Validate the Transmition in the database
        List<Transmition> transmitionList = transmitionRepository.findAll();
        assertThat(transmitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransmition() throws Exception {
        // Initialize the database
        transmitionRepository.saveAndFlush(transmition);

        int databaseSizeBeforeDelete = transmitionRepository.findAll().size();

        // Delete the transmition
        restTransmitionMockMvc.perform(delete("/api/transmitions/{id}", transmition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Transmition> transmitionList = transmitionRepository.findAll();
        assertThat(transmitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
