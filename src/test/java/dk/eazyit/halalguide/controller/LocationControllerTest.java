package dk.eazyit.halalguide.controller;

import com.amazonaws.util.json.Jackson;
import dk.eazyit.halalguide.BaseTest;
import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.enums.LocationType;
import dk.eazyit.halalguide.util.IdGenerator;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testPostLocationWithOutFile() throws Exception {

        Location location = generateLocation();
        String locationString = Jackson.toJsonPrettyString(location);

        MockMultipartFile jsonPart = new MockMultipartFile("location", "json", "application/json", locationString.getBytes());

        mockMvc.perform(fileUpload(endpoint + "location")
                .file(jsonPart)
                .contentType(generateMultiPartMixedMediaType()))
                .andExpect(header().string("location", endpoint + "location/" + location.getId()))
                .andExpect(status().isCreated());

    }

    @Test
    public void testPostLocationWithOneFile() throws Exception {

        Location location = generateLocation();
        String locationString = Jackson.toJsonPrettyString(location);

        MockMultipartFile jsonPart = new MockMultipartFile("location", "json", "application/json", locationString.getBytes());
        MockMultipartFile filePart = new MockMultipartFile("picture", "image.jpg", "image/jpg", generateImageByteArray());

        mockMvc.perform(fileUpload(endpoint + "location")
                .file(jsonPart)
                .file(filePart)
                .contentType(generateMultiPartMixedMediaType()))
                .andExpect(header().string("location", endpoint + "location/" + location.getId()))
                .andExpect(status().isCreated());

    }

    @Test
    public void testPostLocationWithTwoFiles() throws Exception {

        Location location = generateLocation();
        String locationString = Jackson.toJsonPrettyString(location);

        MockMultipartFile jsonPart = new MockMultipartFile("location", "json", "application/json", locationString.getBytes());
        MockMultipartFile filePart = new MockMultipartFile("picture", "image.jpg", "image/jpg", generateImageByteArray());
        MockMultipartFile filePart2 = new MockMultipartFile("picture", "image.jpg", "image/jpg", generateImageByteArray());

        mockMvc.perform(fileUpload(endpoint + "location")
                .file(jsonPart)
                .file(filePart)
                .file(filePart2)
                .contentType(generateMultiPartMixedMediaType()))
                .andExpect(header().string("location", endpoint + "location/" + location.getId()))
                .andExpect(status().isCreated());

    }

}
