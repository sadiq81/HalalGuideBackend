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

import java.util.HashSet;
import java.util.Set;

@RestController
public class PictureController {

    private static final Logger logger = LoggerFactory.getLogger(PictureController.class);

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    AWSFileService awsFileService;

    @RequestMapping(value = "/picture/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Picture> getPicture(@PathVariable("id") String id) {
        logger.info("Fetching Picture with id " + id);

        Picture found = pictureRepository.findOne(id);
        if (found == null) {
            logger.info("Did not find Picture with id " + id);
            return new ResponseEntity<Picture>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Picture>(found, HttpStatus.OK);
        }

    }

    @Transactional
    @RequestMapping(path = "/picture", method = RequestMethod.POST, consumes = {"multipart/mixed"})
    public ResponseEntity<Void> putPicture(@RequestPart(name = "locationId", required = true) String locationId,
                                           @RequestPart(name = "reviewId", required = false) String reviewId,
                                           @RequestPart("picture") MultipartFile[] pictures,
                                           UriComponentsBuilder ucBuilder) {

        logger.info("Creating Picture: ");

        Location foundLocation = locationRepository.findOne(locationId);
        if (foundLocation == null) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        Review foundReview = null;
        if (reviewId != null) {
            foundReview = reviewRepository.findOne(reviewId);
        }

        if (pictures != null && pictures.length > 0) {

            Set<Picture> pictureObjects = new HashSet<Picture>(pictures.length);

            for (int i = 0; i < pictures.length; i++) {
                try {

                    MultipartFile file = pictures[i];
                    String ressourceUrl = awsFileService.uploadPicture(foundLocation, file);
                    Picture picture = new Picture(foundLocation, foundReview, ressourceUrl);
                    pictureObjects.add(picture);
                } catch (Exception e) {
                    //TODO delete bucket folder
                    logger.error("Creating Message failed with exception: " + e);
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
            pictureObjects = new HashSet<Picture>(pictureRepository.save(pictureObjects));
            logger.info("Saved " + pictureObjects.size() + " picture(s)");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/location/{id}").buildAndExpand(foundLocation.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}