package co.edu.udistrital.software.broadcast.repository;

import co.edu.udistrital.software.broadcast.domain.Transmition;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Transmition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransmitionRepository extends JpaRepository<Transmition, Long> {
}
