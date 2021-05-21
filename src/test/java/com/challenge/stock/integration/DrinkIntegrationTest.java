package com.challenge.stock.integration;

import com.challenge.stock.domain.Drink;
import com.challenge.stock.domain.Section;
import com.challenge.stock.domain.enums.Category;
import com.challenge.stock.dto.BookDTO;
import com.challenge.stock.repository.DrinkRepository;
import com.challenge.stock.repository.SectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DrinkIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DrinkRepository drinkRepository;

    @MockBean
    SectionRepository sectionRepository;

    @BeforeEach
    void init() {
        Section sectionResp = new Section();
        sectionResp.setId("idsection");
        sectionResp.setFillment(0L);
        sectionResp.setCapacity(500L);
        sectionResp.setType(Category.ALCOHOLIC);
        sectionResp.setName("section created by test");

        Drink drink = new Drink();
        drink.setId(1);
        drink.setName("amstel-test");
        drink.setType(Category.ALCOHOLIC);
        drink.setSection(sectionResp);
        drink.setCreatedAt(new Date());
        drink.setQuantity(44L);
        drink.setCreatedBy("tester");

        BDDMockito.given(drinkRepository.findAll()).willReturn(List.of(drink));
        BDDMockito.given(drinkRepository.findById(Mockito.any())).willReturn(Optional.of(drink));
        BDDMockito.given(sectionRepository.findById(Mockito.any())).willReturn(Optional.of(sectionResp));
        BDDMockito.given(sectionRepository.save(Mockito.any())).willReturn(sectionResp);
    }

    @Test
    void findDrinkById() throws Exception {
        Integer id = 1;
        mockMvc.perform(get("/v1/drinks/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void ShouldReturnErrorIffindDrinkByIdDoesNotFind() throws Exception {
        BDDMockito.given(drinkRepository.findById(Mockito.any())).willReturn(Optional.empty());
        Integer id = 1;
        mockMvc.perform(get("/v1/drinks/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.msg").value("Drink not found: "+id));
    }

    @Test
    void getPaginated() throws Exception {
        mockMvc.perform(get("/v1/drinks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void bookADrinkSuccessfully() throws Exception {
        Section section = new Section();
        section.setId("idsection");

        BookDTO dto = new BookDTO();
        dto.setType(Category.ALCOHOLIC);
        dto.setDrinkName("amstel-test");
        dto.setSection(section);
        dto.setQuantity(33L);
        dto.setCreatedBy("test");

        mockMvc.perform(post("/v1/drinks/book")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnCustomErroIfInsufficientCapacity() throws Exception {
        Section section = new Section();
        section.setId("idsection");

        BookDTO dto = new BookDTO();
        dto.setType(Category.ALCOHOLIC);
        dto.setDrinkName("amstel-test");
        dto.setSection(section);
        dto.setQuantity(600L);
        dto.setCreatedBy("test");

        mockMvc.perform(post("/v1/drinks/book")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("Insufficient capacity"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnCustomErroIfDifferentCategoryIsInformed() throws Exception {
        Section section = new Section();
        section.setId("idsection");

        BookDTO dto = new BookDTO();
        dto.setType(Category.NON_ALCOHOLIC);
        dto.setDrinkName("juice-test");
        dto.setSection(section);
        dto.setQuantity(300L);
        dto.setCreatedBy("tester");

        mockMvc.perform(post("/v1/drinks/book")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("Invalid type"))
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void shouldReturnErroIfRequiredParamIsNotInformed() throws Exception {
        Section section = new Section();
        section.setId("idsection");

        BookDTO dto = new BookDTO();
        dto.setDrinkName("juice-test");
        dto.setQuantity(44L);
        dto.setSection(section);
        dto.setCreatedBy("tester");

        mockMvc.perform(post("/v1/drinks/book")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        dto.setType(Category.ALCOHOLIC);
        dto.setQuantity(null);

        mockMvc.perform(post("/v1/drinks/book")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        dto.setType(Category.ALCOHOLIC);
        dto.setQuantity(44L);
        dto.setSection(null);

        mockMvc.perform(post("/v1/drinks/book")
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
