package com.wku.mandi.controller;

import com.wku.mandi.SpringBoot;
import com.wku.mandi.db.Address;
import com.wku.mandi.db.User;
import com.wku.mandi.service.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Arrays;
import com.wku.mandi.util.WebTestUtil;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;


/**
 * Created by srujangopu on 7/28/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBoot.class)
@WebAppConfiguration
public class ProfileControllerTest {

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8")
    );

    @Autowired
    private ProfileService profileService;

    private MockMvc mockMvc;

    private User fakeUser = new User();
    private Address homeAddress = new Address();

    private static final String JOHN_DOE = "JohnDoe";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProfileController(profileService))
                .build();
    }

    @Test
    public void testAPI() throws Exception{
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk());
    }

    @Test
    public void saveUser() throws Exception{

        fakeUser.setFirstName("John");
        fakeUser.setLastName("Doe");
        fakeUser.setSex("M");
        fakeUser.setUserId(JOHN_DOE);
        injectHomeAddress();

        mockMvc.perform(post("/profile/save")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(WebTestUtil.convertObjectToJsonBytes(fakeUser))
                       )

                .andExpect(status().isCreated());
    }


    @Test
    public void findUser() throws Exception{

        mockMvc.perform(get("/profile/{id}", JOHN_DOE))
                .andExpect(status().isOk());
    }


    @Test
    public void deleteUser() throws Exception{

        fakeUser.setFirstName("John");
        fakeUser.setLastName("Doe");
        fakeUser.setSex("M");
        fakeUser.setUserId(JOHN_DOE);
        injectHomeAddress();

        mockMvc.perform(delete("/profile/{id}", JOHN_DOE))
                .andExpect(status().isOk());
    }

    @Test
    public void validateFetchAddressService() throws Exception{

        mockMvc.perform(get("/profile/address/{zipCode}", "42101"))
                .andExpect(jsonPath("$.country", is("United States")));
    }

    private void injectHomeAddress() {
        homeAddress.setAddressLine1("123 Some Street");
        homeAddress.setAddressLine2("Apartment 1");
        homeAddress.setCity("Nashville");
        homeAddress.setState("TN");
        homeAddress.setZipCode(37027);
        homeAddress.setType("Home");

        fakeUser.setAddresses(Arrays.asList(homeAddress));
    }
}
