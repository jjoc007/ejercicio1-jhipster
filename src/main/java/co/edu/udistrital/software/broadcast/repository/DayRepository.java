package co.edu.udistrital.software.broadcast.repository;

import co.edu.udistrital.software.broadcast.domain.Day;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Day entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DayRepository extends JpaRepository<Day, Long> {
}
