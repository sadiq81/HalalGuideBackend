package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Review;
import dk.eazyit.halalguide.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ReviewController {

//    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
//
//    @Autowired
//    private ReviewRepository reviewRepository;

    @RequestMapping(value = "/review/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Review> getReview(@PathVariable("id") long id) {
//        logger.info("Fetching Review with id " + id);
//
//        Review found = reviewRepository.findOne(id);
//        if (found == null) {
//            logger.info("Did not find Review with id " + id);
        return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(found, HttpStatus.OK);
//        }
    }

    @RequestMapping(path = "/review", method = RequestMethod.POST)
    public ResponseEntity<Void> putReview(@RequestParam Review review, @RequestParam("pictures") MultipartFile[] pictures, UriComponentsBuilder ucBuilder) {
//        logger.info("Creating Review: " + review);
//
//        Review created = reviewRepository.save(review);
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
//        headers.setLocation(ucBuilder.path("/picture/{id}").buildAndExpand(created.getId()).toUri());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
