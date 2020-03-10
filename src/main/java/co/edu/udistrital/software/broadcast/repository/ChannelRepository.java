package co.edu.udistrital.software.broadcast.repository;

import co.edu.udistrital.software.broadcast.domain.Channel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Channel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
