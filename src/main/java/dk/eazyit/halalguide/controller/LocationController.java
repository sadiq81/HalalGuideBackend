package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Picture;
import dk.eazyit.halalguide.domain.enums.LocationType;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    AWSFileService awsFileService;

    @RequestMapping(value = "/location", method = RequestMethod.GET, consumes = {"multipart/mixed"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> getLocationsFiltered(@RequestPart LocationType id) throws IOException {

        return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/location/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> getLocation(@PathVariable("id") String id) throws IOException {
        logger.debug("Fetching Location with id " + id);

        Location found = locationRepository.findOne(id);

        if (found == null) {
            logger.debug("Did not find Location with id " + id);
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        }

        List<Picture> pictureList = pictureRepository.findByLocation(found);

        if (pictureList != null && pictureList.size() > 0) {
            Set<Picture> pictures = new HashSet<Picture>(pictureList);
            found.setPictures(pictures);
        }

        return new ResponseEntity<Location>(found, HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(path = "/location", method = RequestMethod.POST, consumes = {"multipart/mixed"})
    public ResponseEntity<Void> putLocation(@RequestPart(name = "location", required = true) Location location,
                                            @RequestPart(name = "picture", required = false) MultipartFile[] pictures,
                                            UriComponentsBuilder ucBuilder) {

        Location created = locationRepository.save(location);

        logger.info("Created Location " + created.getName() + " with id: " + created.getId());

        if (pictures != null && pictures.length > 0) {

            Set<Picture> pictureObjects = new HashSet<Picture>(pictures.length);

            for (int i = 0; i < pictures.length; i++) {
                try {

                    MultipartFile file = pictures[i];
                    String ressourceUrl = awsFileService.uploadPicture(created, file);
                    Picture picture = new Picture(created, ressourceUrl);
                    pictureObjects.add(picture);
                } catch (Exception e) {
                    //TODO delete bucket folder
                    logger.error("Creating Message failed with exception: " + e);
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }
            pictureObjects = new HashSet<Picture>(pictureRepository.save(pictureObjects));
            logger.info("Saved " + pictureObjects.size() + " picture(s) for " + created.getName() + " with id: " + created.getId());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/location/{id}").buildAndExpand(created.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}
