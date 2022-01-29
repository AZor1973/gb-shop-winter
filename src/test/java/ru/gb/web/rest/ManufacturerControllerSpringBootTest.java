package ru.gb.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.api.manufacturer.dto.ManufacturerDto;
import ru.gb.dao.ManufacturerDao;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManufacturerControllerSpringBootTest {

    final private static String MANUFACTURER_NAME = "Apple";
    final private static String UPDATED_MANUFACTURER_NAME = "Microsoft";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ManufacturerDao manufacturerDao;

    @Test
    @Order(1)
    public void saveManufacturerTest() throws Exception {


        mockMvc.perform(post("/api/v1/manufacturer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(ManufacturerDto.builder()
                                .name(MANUFACTURER_NAME)
                                .build()
                        )
                ))
                .andExpect(status().isCreated());

        assertEquals(1, manufacturerDao.count());
    }

    @Test
    @Order(2)
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value(MANUFACTURER_NAME));
    }

    @Test
    @Order(3)
    public void handleUpdateTest() throws Exception {
        mockMvc.perform(put("/api/v1/manufacturer/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ManufacturerDto.builder()
                                        .manufacturerId(1L)
                                        .name(UPDATED_MANUFACTURER_NAME)
                                        .build()
                                )
                        ))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(4)
    public void getManufacturerTest() throws Exception {
        mockMvc.perform(get("/api/v1/manufacturer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("name").value(UPDATED_MANUFACTURER_NAME));
    }

    @Test
    @Order(5)
    public void deletedByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/manufacturer/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void getZeroCategoryListTest() throws Exception {
        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }
}