package dk.eazyit.halalguide.controller;

import dk.eazyit.halalguide.domain.Message;
import dk.eazyit.halalguide.repository.MessageRepository;
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
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getMessage(@PathVariable("id") String id) {
        logger.info("Fetching Message with id " + id);

        Message found = messageRepository.findOne(id);
        if (found == null) {
            logger.info("Did not find Message with id " + id);
        return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Message>(found, HttpStatus.OK);
        }

    }
}
