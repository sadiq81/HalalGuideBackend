package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.repository.LocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class LocationController {

    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);
//
    @Autowired
    private LocationRepository locationRepository;

    /*@Autowired
    AwsTransferService transferService;*/

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

        Location found = new Location();
        if (found == null) {
            logger.info("Did not find Location with id " + id);
            return new ResponseEntity<Location>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Location>(found, HttpStatus.OK);
        }

    }

    @RequestMapping(path = "/location", method = RequestMethod.POST)
    public ResponseEntity<Void> putLocation(@RequestParam Location location, @RequestParam("pictures") MultipartFile[] pictures, UriComponentsBuilder ucBuilder) {
//        logger.info("Creating Location: " + location);

//        Location created = locationRepository.save(location);
//
//        if (pictures != null && pictures.length > 0) {
//            for (int i = 0; i < pictures.length; i++) {
//                try {
//                    //TODO save file to aws
//                } catch (Exception e) {
//                    logger.error("Creating Message failed with exception: " + e);
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//            }
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/location/{id}").buildAndExpand(created.getId()).toUri());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
