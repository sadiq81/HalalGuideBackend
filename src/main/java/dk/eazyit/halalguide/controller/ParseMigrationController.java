package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Picture;
import dk.eazyit.halalguide.domain.Review;
import dk.eazyit.halalguide.domain.User;
import dk.eazyit.halalguide.repository.LocationRepository;
import dk.eazyit.halalguide.repository.PictureRepository;
import dk.eazyit.halalguide.repository.ReviewRepository;
import dk.eazyit.halalguide.repository.UserRepository;
import dk.eazyit.halalguide.service.AWSFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

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

    @Autowired
    AWSFileService awsFileService;

    @Transactional
    @RequestMapping(path = "/parseReview", method = RequestMethod.POST, consumes = {"multipart/mixed"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putLocation(@RequestPart(name = "parseLocationId", required = true) String locationId,
                                            @RequestPart(name = "review", required = true) Review review,
                                            UriComponentsBuilder ucBuilder) {

        Location found = locationRepository.findByParseId(locationId);

        if (found == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        Review created = reviewRepository.save(review);

        logger.info("Created Review with id: " + created.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/review/{id}").buildAndExpand(created.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @Transactional
    @RequestMapping(path = "/parsePicture", method = RequestMethod.POST, consumes = {"multipart/mixed"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putPicture(@RequestPart(name = "parsePicture", required = true) Picture parsePicture,
                                           @RequestPart(name = "parseLocationId", required = true) String parseLocationId,
                                           @RequestPart(name = "parseReviewId",required = false) String parseReviewId,
                                           @RequestPart MultipartFile picture,
                                           UriComponentsBuilder ucBuilder) {

        logger.info("Creating Picture: ");

        Location foundLocation = locationRepository.findByParseId(parseLocationId);
        if (foundLocation == null) {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        Review foundReview = reviewRepository.findByParseId(parseReviewId);

        Picture created = null;
        try {
            String ressourceUrl = awsFileService.uploadPicture(foundLocation, picture);
            created = new Picture(foundLocation, foundReview, ressourceUrl);
            created.setCreatedAt(parsePicture.getCreatedAt());
            created.setUpdatedAt(parsePicture.getUpdatedAt());
            created.setSubmitterId(parsePicture.getSubmitterId());
            created.setParseId(parsePicture.getParseId());
            pictureRepository.save(created);
        } catch (Exception e) {
            logger.error("Creating Message failed with exception: " + e);
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/picture/{id}").buildAndExpand(created.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

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
