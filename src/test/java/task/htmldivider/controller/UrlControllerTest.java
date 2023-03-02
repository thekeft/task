package task.htmldivider.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import task.htmldivider.service.UrlService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UrlService urlService;

    @Test
    void shouldSuccessToHtmlDivide() throws Exception{
        final var result = mvc.perform(MockMvcRequestBuilders
                        .get("/url")
                         .param("url", "http://localhost:8080")
                         .param("extractType", "EXCLUDE")
                         .param("number", "100")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    void shouldFailToHtmlDivideByUrl() throws Exception{
        final var result = mvc.perform(MockMvcRequestBuilders
                .get("/url")
                .param("url", "localhost:8080")
                .param("extractType", "EXCLUDE")
                .param("number", "100")
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().is4xxClientError());
    }
}