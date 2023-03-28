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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"HarryPotter\",\"description\":\"Wizard Boy\",\"releaseDate\":\"2000-01-01\"," +
                        "\"duration\":100}"));
    }

    @Test
    void emptyNameExceptionTest() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"description\":\"Wizard Boy\",\"releaseDate\":\"2000-01-01\"," +
                                "\"duration\":200}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void longDescriptionExceptionTest() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Гарри Поттер\",\"description\":\"Жизнь десятилетнего Гарри Поттера нельзя " +
                                "назвать сладкой: родители умерли, едва ему исполнился год, а от дяди и тёти, взявших" +
                                "сироту на воспитание, достаются лишь тычки да подзатыльники. Но в одиннадцатый день " +
                                "рождения Гарри всё меняется. Странный гость, неожиданно появившийся на пороге, " +
                                "приносит письмо, из которого мальчик узнаёт, что на самом деле он - волшебник и " +
                                "зачислен в школу магии под названием Хогвартс. А уже через пару недель Гарри будет " +
                                "мчаться в поезде Хогвартс-экспресс навстречу новой жизни, где его ждут невероятные " +
                                "приключения, верные друзья и самое главное — ключ к разгадке тайны смерти его " +
                                "родителей.\",\"releaseDate\":\"2000-01-01\",\"duration\":200}"))
                .andExpect(status().is4xxClientError());
    }


    @Test
    void releaseDayExceptionTest() throws Exception {
        mockMvc.perform(post("/films").
                        contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"HarryPotter\"," +
                                "\"description\":\"Wizard Boy\",\"releaseDate\":\"1895-12-27\",\"duration\":100}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void notPositiveDurationExceptionTest() throws Exception {
        mockMvc.perform(post("/films").
                        contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"HarryPotter\"," +
                                "\"description\":\"Wizard Boy\",\"releaseDate\":\"2000-01-01\",\"duration\":-100}"))
                .andExpect(status().is4xxClientError());
    }
}


