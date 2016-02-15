package dk.eazyit.halalguide.controller;

import com.amazonaws.util.json.Jackson;
import dk.eazyit.halalguide.BaseTest;
import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.enums.LocationType;
import dk.eazyit.halalguide.util.IdGenerator;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 15/02/16
 * Time: 07.23
 * To change this template use File | Settings | File Templates.
 */
public class LocationControllerTest extends BaseTest {

    @Test
    public void testPutLocation() throws Exception {

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

        String locationString = Jackson.toJsonPrettyString(location);

        File f = new File("README");
        FileInputStream fi1 = new FileInputStream(f);

        HashMap<String, String> contentTypeParams = new HashMap<String, String>();
        contentTypeParams.put("boundary", IdGenerator.createId());
        MediaType mediaType = new MediaType("multipart", "mixed", contentTypeParams);

        MockMultipartFile jsonPart = new MockMultipartFile("location", "json", "application/json", locationString.getBytes());

        mockMvc.perform(fileUpload("http://localhost:8080/location")
                .file(jsonPart)
                .contentType(mediaType))
                .andExpect(status().isCreated());

    }
}
