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

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getLocation(@PathVariable("id") String id) throws IOException {
        logger.debug("Fetching User with id " + id);

        User found = userRepository.findOne(id);

        if (found == null) {
            logger.debug("Did not find User with id " + id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<User>(found, HttpStatus.OK);

    }

    @Transactional
    @RequestMapping(path = "/user", method = RequestMethod.POST, consumes = {"application/json"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> putLocation(@RequestBody User user, UriComponentsBuilder ucBuilder) {

        User created = userRepository.save(user);

        logger.info("Created User with id: " + created.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(created.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

}
