package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.editor.LocationEditor;
import dk.eazyit.halalguide.repository.LocationRepository;
import dk.eazyit.halalguide.service.AwsTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
    //
    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    AwsTransferService transferService;

//    @RequestMapping(path = "/location", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<Location>> getLocations(@RequestParam(value = "maxDistance", defaultValue = "5") Integer maxDistance,
//                                                       @RequestParam(value = "skip", defaultValue = "0") int skip,
//                                                       @RequestParam(value = "limit", defaultValue = "20") int limit,
//                                                       Location predicate) {
//
//        logger.info("Fetching Locations with catteries: " +
//                ", Predicate: " + predicate + '\'' +
//                ", Max distance: " + maxDistance + '\'' +
//                ", Skip: " + skip + '\'' +
//                ", Limit: " + limit
//        );
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);//
//    }

    @RequestMapping(value = "/location/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Location> getLocation(@PathVariable("id") long id) {
        logger.info("Fetching Location with id " + id);

        Location found = locationRepository.findOne(id);
        if (found == null) {
            logger.info("Did not find Location with id " + id);
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Location>(found, HttpStatus.OK);
        }

    }

    @Transactional
    @RequestMapping(path = "/location", method = RequestMethod.POST, consumes = {"multipart/mixed"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putLocation(@RequestPart(name = "location", required = false) Location location, @RequestParam(name = "picture", required = false) MultipartFile[] pictures, UriComponentsBuilder ucBuilder) {
        logger.info("Creating Location: " + location);

        Location created = locationRepository.save(location);

        logger.info("Created Location " + created.getName() + " with id: " + created.getId());

        if (pictures != null && pictures.length > 0) {
            for (int i = 0; i < pictures.length; i++) {
                try {
                    transferService.uploadPicture(created, pictures[i]);
                } catch (Exception e) {
                    logger.error("Creating Message failed with exception: " + e);
                    return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/location/{id}").buildAndExpand(created.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Location.class, new LocationEditor());
    }
}
