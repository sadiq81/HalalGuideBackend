package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class MessageController {

//    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
//
//    @Autowired
//    private MessageRepository messageRepository;

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

    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getMessage(@PathVariable("id") long id) {
//        logger.info("Fetching Message with id " + id);
//
//        Message found = messageRepository.findOne(id);
//        if (found == null) {
//            logger.info("Did not find Message with id " + id);
        return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(found, HttpStatus.OK);
//        }

    }

    @RequestMapping(path = "/message", method = RequestMethod.POST)
    public ResponseEntity<Void> putMessage(@RequestBody Message message, @RequestParam("picture") MultipartFile picture, UriComponentsBuilder ucBuilder) {
//        logger.info("Creating Message: " + message);
//
//        Message created = messageRepository.save(message);
//
//        if (picture != null && picture.getSize() > 0) {
//            try {
//                //TODO save file to aws
//            } catch (Exception e) {
//                logger.error("Creating Message failed with exception: " + e);
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/message/{id}").buildAndExpand(created.getId()).toUri());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
