package dk.eazyit.halalguide.repository;

import dk.eazyit.halalguide.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 30/01/16
 * Time: 23.31
 * To change this template use File | Settings | File Templates.
 */
public interface SubjectRepository extends JpaRepository<Subject, String> {
}
