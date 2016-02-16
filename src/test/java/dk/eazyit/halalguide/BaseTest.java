package dk.eazyit.halalguide;

import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Review;
import dk.eazyit.halalguide.domain.User;
import dk.eazyit.halalguide.domain.enums.LocationType;
import dk.eazyit.halalguide.domain.enums.SocialMediaType;
import dk.eazyit.halalguide.util.IdGenerator;
import javafx.util.Pair;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 14/02/16
 * Time: 20.14
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:applicationContext-test.xml", "classpath:spring-servlet-test.xml"})
public abstract class BaseTest {

    protected String endpoint = "http://localhost:8080/";

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    protected User generateUser() {
        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setSocialMediaType(SocialMediaType.Facebook);
        user.setSocialMediaId("test");
        return user;
    }

    protected Review generateReview() {

        Review review = new Review();
        review.setRating(5);
        review.setReview("test review");
        return review;

    }

    protected Location generateLocation() {
        Location location = new Location();
        location.setName("test name");
        location.setRoad("test road");
        location.setRoadNumber("test roadNumber");
        location.setCity("test city");
        location.setPostalCode("test postalCode");
        location.setLatitude(56.001f);
        location.setLongitude(12.001f);
        location.setWebsite("www.test.site");
        location.setLocationType(LocationType.Dining);
        location.setTelephone("00000000");
        location.setPork(true);
        location.setAlcohol(true);
        location.setNonHalalFood(true);
        return location;
    }

    protected byte[] generateImageByteArray() throws Exception {
        File file = new File("src/test/resources/image.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        return IOUtils.toByteArray(fileInputStream);
    }

    protected MediaType generateMultiPartMixedMediaType() {
        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        contentTypeParams.put("boundary", IdGenerator.createId());
        MediaType mediaType = new MediaType("multipart", "mixed", contentTypeParams);
        return mediaType;
    }

}

