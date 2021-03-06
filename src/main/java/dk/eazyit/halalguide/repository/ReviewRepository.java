package dk.eazyit.halalguide.repository;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 30/01/16
 * Time: 23.31
 * To change this template use File | Settings | File Templates.
 */
public interface ReviewRepository extends JpaRepository<Review, String> {

    Review findByParseId(String parseId);
}
