package dk.eazyit.halalguide.controller;

import com.amazonaws.util.json.Jackson;
import dk.eazyit.halalguide.BaseTest;
import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Review;
import dk.eazyit.halalguide.domain.enums.LocationType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 15/02/16
 * Time: 07.23
 * To change this template use File | Settings | File Templates.
 */
public class ReviewControllerTest extends BaseTest {

    private Location location = null;

    @Before
    public void setup() throws Exception {
        super.setup();

        location = generateLocation();
        String locationString = Jackson.toJsonPrettyString(location);

        MockMultipartFile jsonPart = new MockMultipartFile("location", "json", "application/json", locationString.getBytes());

        mockMvc.perform(fileUpload(endpoint + "location")
                .file(jsonPart)
                .contentType(generateMultiPartMixedMediaType()))
                .andExpect(header().string("location", endpoint + "location/" + location.getId()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostReviewWithOutFile() throws Exception {

        Review review = generateReview();
        String reviewString = Jackson.toJsonPrettyString(review);

        MockMultipartFile jsonPartlocation = new MockMultipartFile("locationId", "text", "text/plain", location.getId().getBytes());
        MockMultipartFile jsonPartReview = new MockMultipartFile("review", "json", "application/json", reviewString.getBytes());

        mockMvc.perform(fileUpload(endpoint + "review")
                .file(jsonPartlocation)
                .file(jsonPartReview)
                .contentType(generateMultiPartMixedMediaType()))
                .andExpect(header().string("location", endpoint + "review/" + review.getId()))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostReviewWithOneFile() throws Exception {

        Review review = generateReview();
        String reviewString = Jackson.toJsonPrettyString(review);

        MockMultipartFile jsonPartlocation = new MockMultipartFile("locationId", "text", "text/plain", location.getId().getBytes());
        MockMultipartFile jsonPart = new MockMultipartFile("review", "json", "application/json", reviewString.getBytes());
        MockMultipartFile filePart = new MockMultipartFile("picture", "image.jpg", "image/jpg", generateImageByteArray());

        mockMvc.perform(fileUpload(endpoint + "review")
                .file(jsonPartlocation)
                .file(jsonPart)
                .file(filePart)
                .contentType(generateMultiPartMixedMediaType()))
                .andExpect(header().string("location", endpoint + "review/" + review.getId()))
                .andExpect(status().isCreated());

    }

    @Test
    public void testPostReviewWithTwoFiles() throws Exception {

        Review review = generateReview();
        String reviewString = Jackson.toJsonPrettyString(review);

        MockMultipartFile jsonPartlocation = new MockMultipartFile("locationId", "text", "text/plain", location.getId().getBytes());
        MockMultipartFile jsonPart = new MockMultipartFile("review", "json", "application/json", reviewString.getBytes());
        MockMultipartFile filePart = new MockMultipartFile("picture", "image.jpg", "image/jpg", generateImageByteArray());
        MockMultipartFile filePart2 = new MockMultipartFile("picture", "image.jpg", "image/jpg", generateImageByteArray());

        mockMvc.perform(fileUpload(endpoint + "review")
                .file(jsonPartlocation)
                .file(jsonPart)
                .file(filePart)
                .file(filePart2)
                .contentType(generateMultiPartMixedMediaType()))
                .andExpect(header().string("location", endpoint + "review/" + review.getId()))
                .andExpect(status().isCreated());

    }


}
