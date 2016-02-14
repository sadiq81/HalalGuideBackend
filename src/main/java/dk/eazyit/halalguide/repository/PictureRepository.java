package dk.eazyit.halalguide.repository;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Picture;
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
//@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {

    Picture findByParseId(String parseId);

    List<Picture> findByLocation(Location location);

    List<Picture> findByReview(Review Review);
}
