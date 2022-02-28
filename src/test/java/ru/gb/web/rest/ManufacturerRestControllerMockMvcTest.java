package ru.gb.web.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.api.manufacturer.dto.ManufacturerDto;
import ru.gb.service.ManufacturerService;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManufacturerRestController.class)
class ManufacturerRestControllerMockMvcTest {

    @MockBean
    ManufacturerService manufacturerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void findAllTest() throws Exception {
        ArrayList<ManufacturerDto> manufacturerDtoList = new ArrayList<>() {{
            add(new ManufacturerDto(1L, "Apple"));
            add(new ManufacturerDto(2L, "Microsoft"));
        }};

        Mockito.when(manufacturerService.findAll()).thenReturn(manufacturerDtoList);

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

}