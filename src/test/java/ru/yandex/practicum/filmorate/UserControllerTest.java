package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"vasilisaKo\",\"name\":\"vasilisa\",\"email\":\"vasilisa@gmail.com\"," +
                        "\"birthday\":\"1986-10-20\"}"));
    }

    @Test
    void emptyOrIncorrectEmailExceptionTest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"vasilisaKo\",\"name\":\"vasilisa\",\"email\":\"vasilisalgmail.com\"," +
                                "\"birthday\":\"1986-10-20\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void emptyOrContainSpaceLoginExceptionTest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"vasilisaKo\",\"name\":\"vasilisa\",\"email\":\"vasilisagmail.com\"," +
                                "\"birthday\":\"1986-10-20\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void incorrectBirthdayExceptionTest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"vasilisaKo\",\"name\":\"vasilisa\",\"email\":\"vasilisa@gmail.com\"," +
                                "\"birthday\":\"2030-10-20\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void emptyNameShouldreplaceByLogenTest() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\":\"vasilisa\",\"email\":\"vasilisa@gmail.com\"," +
                                "\"birthday\":\"1986-10-20\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("vasilisa"));
    }
}
