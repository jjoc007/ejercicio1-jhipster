package co.edu.udistrital.software.broadcast.web.rest;

import co.edu.udistrital.software.broadcast.BroadcastApp;
import co.edu.udistrital.software.broadcast.domain.Calendar;
import co.edu.udistrital.software.broadcast.repository.CalendarRepository;

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
 * Integration tests for the {@link CalendarResource} REST controller.
 */
@SpringBootTest(classes = BroadcastApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CalendarResourceIT {

    private static final String DEFAULT_INITIAL_HOUR = "AAAAAAAAAA";
    private static final String UPDATED_INITIAL_HOUR = "BBBBBBBBBB";

    private static final String DEFAULT_FINAL_HOUR = "AAAAAAAAAA";
    private static final String UPDATED_FINAL_HOUR = "BBBBBBBBBB";

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalendarMockMvc;

    private Calendar calendar;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendar createEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .initialHour(DEFAULT_INITIAL_HOUR)
            .finalHour(DEFAULT_FINAL_HOUR);
        return calendar;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calendar createUpdatedEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .initialHour(UPDATED_INITIAL_HOUR)
            .finalHour(UPDATED_FINAL_HOUR);
        return calendar;
    }

    @BeforeEach
    public void initTest() {
        calendar = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendar() throws Exception {
        int databaseSizeBeforeCreate = calendarRepository.findAll().size();

        // Create the Calendar
        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isCreated());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate + 1);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getInitialHour()).isEqualTo(DEFAULT_INITIAL_HOUR);
        assertThat(testCalendar.getFinalHour()).isEqualTo(DEFAULT_FINAL_HOUR);
    }

    @Test
    @Transactional
    public void createCalendarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarRepository.findAll().size();

        // Create the Calendar with an existing ID
        calendar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCalendars() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList
        restCalendarMockMvc.perform(get("/api/calendars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].initialHour").value(hasItem(DEFAULT_INITIAL_HOUR)))
            .andExpect(jsonPath("$.[*].finalHour").value(hasItem(DEFAULT_FINAL_HOUR)));
    }
    
    @Test
    @Transactional
    public void getCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get the calendar
        restCalendarMockMvc.perform(get("/api/calendars/{id}", calendar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calendar.getId().intValue()))
            .andExpect(jsonPath("$.initialHour").value(DEFAULT_INITIAL_HOUR))
            .andExpect(jsonPath("$.finalHour").value(DEFAULT_FINAL_HOUR));
    }

    @Test
    @Transactional
    public void getNonExistingCalendar() throws Exception {
        // Get the calendar
        restCalendarMockMvc.perform(get("/api/calendars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar
        Calendar updatedCalendar = calendarRepository.findById(calendar.getId()).get();
        // Disconnect from session so that the updates on updatedCalendar are not directly saved in db
        em.detach(updatedCalendar);
        updatedCalendar
            .initialHour(UPDATED_INITIAL_HOUR)
            .finalHour(UPDATED_FINAL_HOUR);

        restCalendarMockMvc.perform(put("/api/calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalendar)))
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getInitialHour()).isEqualTo(UPDATED_INITIAL_HOUR);
        assertThat(testCalendar.getFinalHour()).isEqualTo(UPDATED_FINAL_HOUR);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Create the Calendar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarMockMvc.perform(put("/api/calendars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        int databaseSizeBeforeDelete = calendarRepository.findAll().size();

        // Delete the calendar
        restCalendarMockMvc.perform(delete("/api/calendars/{id}", calendar.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
