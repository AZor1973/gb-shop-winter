package ru.gb.web.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.api.manufacturer.dto.ManufacturerDto;
import ru.gb.service.ManufacturerService;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerControllerTest {
    @Mock
    ManufacturerService manufacturerService;

    @InjectMocks
    ManufacturerRestController manufacturerController;

    MockMvc mockMvc;

    List<ManufacturerDto> manufacturerDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        manufacturerDtoList.add(new ManufacturerDto(1L, "Apple"));
        manufacturerDtoList.add(new ManufacturerDto(2L, "Microsoft"));

        mockMvc = MockMvcBuilders.standaloneSetup(manufacturerController).build();
    }

    @Test
    public void mockMvcGetManufacturerListTest() throws Exception {
        // given
        given(manufacturerService.findAll()).willReturn(manufacturerDtoList);

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value("Apple"))
                .andExpect(jsonPath("$.[1].name").value("Microsoft"));
    }

    @Test
    public void createManufacturerTest() throws Exception {

        given(manufacturerService.save(any())).willReturn(new ManufacturerDto(3L, "Microsoft"));

        mockMvc.perform(post("/api/v1/manufacturer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"name\": \"Microsoft\"" +
                        "}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void getManufacturerListTest() {
        // given
        given(manufacturerService.findAll()).willReturn(manufacturerDtoList);

        // when
        List<ManufacturerDto> manufacturerList = manufacturerController.getManufacturerList();

        // then
        then(manufacturerService).should().findAll();

        assertAll(
                () -> assertEquals(2, manufacturerList.size(), "Size "),
                () -> assertEquals("Apple", manufacturerList.get(0).getName())
        );
    }
}