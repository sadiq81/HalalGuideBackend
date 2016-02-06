package dk.eazyit.halalguide.repository;

import dk.eazyit.halalguide.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 30/01/16
 * Time: 23.31
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
