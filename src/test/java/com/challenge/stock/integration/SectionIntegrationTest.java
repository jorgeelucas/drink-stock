package com.challenge.stock.integration;

import com.challenge.stock.domain.Section;
import com.challenge.stock.domain.enums.Category;
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

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SectionIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DrinkRepository drinkRepository;

    @MockBean
    SectionRepository sectionRepository;

    Section sectionResp;

    @BeforeEach
    void init() {
        sectionResp = new Section();
        sectionResp.setId("idsection");
        sectionResp.setFillment(0L);
        sectionResp.setCapacity(500L);
        sectionResp.setType(Category.ALCOHOLIC);
        sectionResp.setName("section created by test");
    }

    @Test
    void getAmountPerType() throws Exception {

        sectionResp.setFillment(44L);

        Section other = new Section();
        other.setFillment(66L);
        BDDMockito.given(sectionRepository.findAllByType(Mockito.any())).willReturn(List.of(sectionResp, other));

        String type = "ALCOHOLIC";

        mockMvc.perform(get("/v1/sections/amount/"+ type +"/type")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value(type))
                .andExpect(jsonPath("$.amount").value(110));
    }

    @Test
    void getAmountPerTypeShouldReturnExceptionWhenTypeInvalid() throws Exception {
        String type = "invalid-type";

        mockMvc.perform(get("/v1/sections/amount/"+ type +"/type")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("Invalid category: " + type));
    }

    @Test
    void getAmountPerSection() throws Exception {
        BDDMockito.given(sectionRepository.findById(Mockito.any())).willReturn(Optional.of(sectionResp));
        String section = "sectionId";
        mockMvc.perform(get("/v1/sections/amount/"+ section +"/section")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAmountPerSectionShouldReturnExceptionWhenInvalidIdIsInformed() throws Exception {
        BDDMockito.given(sectionRepository.findById(Mockito.any())).willReturn(Optional.empty());
        String section = "sectionId";
        mockMvc.perform(get("/v1/sections/amount/"+ section +"/section")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.msg").value("Section not found: " + section));
    }

    @Test
    void getAvailableByAmountAndType() throws Exception {
        Section newSection = new Section();
        newSection.setId("idsection2");
        newSection.setFillment(480L);
        newSection.setCapacity(500L);
        newSection.setType(Category.ALCOHOLIC);
        newSection.setName("section created by test");

        Section newSection2 = new Section();
        newSection2.setId("idsection3");
        newSection2.setFillment(460L);
        newSection2.setCapacity(500L);
        newSection2.setType(Category.ALCOHOLIC);
        newSection2.setName("section created by test");

        BDDMockito.given(sectionRepository.findAllByType(Mockito.any())).willReturn(List.of(sectionResp, newSection, newSection2));

        String type = "alcoholic";
        Integer amount = 40;
        mockMvc.perform(get("/v1/sections/available/"+amount+"/"+ type)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getAvailableByAmountAndTypeShouldThrowExceptionIfTypeInvalid() throws Exception {
        String type = "invalid-type";
        Integer amount = 40;
        mockMvc.perform(get("/v1/sections/available/"+amount+"/"+ type)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("Invalid category: " + type));
    }

}
