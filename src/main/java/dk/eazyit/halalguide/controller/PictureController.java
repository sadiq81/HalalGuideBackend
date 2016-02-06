package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Picture;
import dk.eazyit.halalguide.domain.Review;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PictureController {

//    private static final Logger logger = LoggerFactory.getLogger(PictureController.class);
//
//    @Autowired
//    private PictureRepository pictureRepository;

    @RequestMapping(value = "/picture/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Picture> getPicture(@PathVariable("id") long id) {
//        logger.info("Fetching Picture with id " + id);
//
//        Picture found = pictureRepository.findOne(id);
//        if (found == null) {
//            logger.info("Did not find Picture with id " + id);
        return new ResponseEntity<Picture>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(found, HttpStatus.OK);
//        }

    }

    @RequestMapping(path = "/picture", method = RequestMethod.POST)
    public ResponseEntity<Void> putPicture(@RequestParam Location location, @RequestParam Review review, @RequestParam("pictures") MultipartFile[] pictures) {
//        logger.info("Creating Picture: ");
//
//        if (pictures != null && pictures.length > 0) {
//            for (int i = 0; i < pictures.length; i++) {
//
//                try {
//
//                    //TODO save file to aws
//                } catch (Exception e) {
//                    logger.error("Creating Message failed with exception: " + e);
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//
//                Picture picture = new Picture(location, review);
//
//                Picture created = pictureRepository.save(picture);
//            }
//
//        }
//
//        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
