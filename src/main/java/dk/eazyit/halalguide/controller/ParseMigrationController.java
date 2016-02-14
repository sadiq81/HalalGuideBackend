package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Picture;
import dk.eazyit.halalguide.domain.Review;
import dk.eazyit.halalguide.domain.User;
import dk.eazyit.halalguide.repository.LocationRepository;
import dk.eazyit.halalguide.repository.PictureRepository;
import dk.eazyit.halalguide.repository.ReviewRepository;
import dk.eazyit.halalguide.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 14/02/16
 * Time: 11.52
 * To change this template use File | Settings | File Templates.
 */
@RestController
public class ParseMigrationController {

    private static final Logger logger = LoggerFactory.getLogger(ParseMigrationController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @RequestMapping(value = "/parseMigration", method = RequestMethod.GET)
    public ResponseEntity<Void> migrateData() {
        logger.debug("Starting to migrate parse relations");


        List<User> users = userRepository.findAll();
        Map<String, User> userMap = new HashMap<String, User>();
        for (User user : users) {
            userMap.put(user.getParseId(), user);
        }

        List<Location> locations = locationRepository.findAll();
        for (Location location : locations) {

            User user = userMap.get(location.getSubmitterId());
            location.setUser(user);
            locationRepository.save(location);
        }

        List<Review> reviews = reviewRepository.findAll();
        for (Review review : reviews) {

            User user = userMap.get(review.getSubmitterId());
            review.setUser(user);
            reviewRepository.save(review);
        }

        List<Picture> pictures = pictureRepository.findAll();
        for (Picture picture : pictures) {

            User user = userMap.get(picture.getSubmitterId());
            picture.setUser(user);
            pictureRepository.save(picture);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);

    }
}
