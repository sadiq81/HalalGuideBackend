package dk.eazyit.halalguide.controller;

import com.amazonaws.util.json.Jackson;
import dk.eazyit.halalguide.BaseTest;
import dk.eazyit.halalguide.domain.Location;
import dk.eazyit.halalguide.domain.Review;
import dk.eazyit.halalguide.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 15/02/16
 * Time: 07.23
 * To change this template use File | Settings | File Templates.
 */
public class UserControllerTest extends BaseTest {

    @Test
    public void testPostUser() throws Exception {

        User user = generateUser();
        String userString = Jackson.toJsonPrettyString(user);

        mockMvc.perform(post(endpoint + "user")
                .content(userString)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("location", endpoint + "user/" + user.getId()))
                .andExpect(status().isCreated());

        mockMvc.perform(get(endpoint + "user/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(user.getId())))
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                //.andExpect(jsonPath("$.socialMediaType", is(user.getSocialMediaType())))
                .andExpect(jsonPath("$.socialMediaId", is(user.getSocialMediaId())));
    }
}
