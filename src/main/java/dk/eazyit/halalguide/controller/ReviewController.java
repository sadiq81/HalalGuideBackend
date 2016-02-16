package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Picture;
import dk.eazyit.halalguide.domain.Review;
import dk.eazyit.halalguide.repository.LocationRepository;
import dk.eazyit.halalguide.repository.PictureRepository;
import dk.eazyit.halalguide.repository.ReviewRepository;
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

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    AWSFileService awsFileService;

    @RequestMapping(value = "/review/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> getLocation(@PathVariable("id") String id) throws IOException {
        logger.debug("Fetching Review with id " + id);

        Review found = reviewRepository.findOne(id);

        if (found == null) {
            logger.debug("Did not find Review with id " + id);
            return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
        }

        List<Picture> pictureList = pictureRepository.findByReview(found);

        if (pictureList != null && pictureList.size() > 0) {
            Set<Picture> pictures = new HashSet<Picture>(pictureList);
            found.setPictures(pictures);
        }

        return new ResponseEntity<Review>(found, HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(path = "/review", method = RequestMethod.POST, consumes = {"multipart/mixed"})
    public ResponseEntity<Void> putLocation(@RequestPart(name = "locationId", required = true) String locationId,
                                            @RequestPart(name = "review", required = true) Review review,
                                            @RequestPart(name = "picture", required = false) MultipartFile[] pictures,
                                            UriComponentsBuilder ucBuilder) {

        Location found = locationRepository.findOne(locationId);

        if (found == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        Review created = reviewRepository.save(review);

        logger.info("Created Review with id: " + created.getId());

        if (pictures != null && pictures.length > 0) {

            Set<Picture> pictureObjects = new HashSet<Picture>(pictures.length);

            for (int i = 0; i < pictures.length; i++) {
                try {

                    MultipartFile file = pictures[i];
                    String ressourceUrl = awsFileService.uploadPicture(found, file);
                    Picture picture = new Picture(found, created, ressourceUrl);
                    pictureObjects.add(picture);
                } catch (Exception e) {
                    //TODO delete bucket folder
                    logger.error("Creating Message failed with exception: " + e);
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
            pictureObjects = new HashSet<Picture>(pictureRepository.save(pictureObjects));
            logger.info("Saved " + pictureObjects.size() + " picture(s) for Review with id: " + created.getId());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/review/{id}").buildAndExpand(created.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}
