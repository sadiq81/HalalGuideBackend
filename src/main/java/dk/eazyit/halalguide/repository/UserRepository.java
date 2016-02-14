package dk.eazyit.halalguide.repository;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 30/01/16
 * Time: 23.31
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository extends JpaRepository<User, String> {

    User findByParseId(String parseId);

}
