package co.edu.udistrital.software.broadcast.repository;

import co.edu.udistrital.software.broadcast.domain.Calendar;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Calendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
