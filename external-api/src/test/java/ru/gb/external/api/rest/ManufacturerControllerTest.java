package ru.gb.external.api.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gb.api.manufacturer.api.ManufacturerGateway;
import ru.gb.api.manufacturer.dto.ManufacturerDto;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Николай Новицкий
 */
@ExtendWith(MockitoExtension.class)
public class ManufacturerControllerTest {
    @Mock
    ManufacturerGateway manufacturerGateway;

    @InjectMocks
    ManufacturerController manufacturerController;

    MockMvc mockMvc;

    List<ManufacturerDto> manufacturerDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        manufacturerDtoList.add(new ManufacturerDto(1L, "TestManufacturer1"));
        manufacturerDtoList.add(new ManufacturerDto(2L, "TestManufacturer2"));

        mockMvc = MockMvcBuilders.standaloneSetup(manufacturerController).build();
    }

    @Test
    public void mockMvcGetManufacturerListTest() throws Exception {

        given(manufacturerGateway.getManufacturerList()).willReturn(manufacturerDtoList);

        mockMvc.perform(get("/api/v1/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].name").value("TestManufacturer1"))
                .andExpect(jsonPath("$.[1].name").value("TestManufacturer2"));
    }

    @Test
    public void createManufacturerTest() throws Exception {
        given(manufacturerGateway.handlePost(any(ManufacturerDto.class))).willReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post("/api/v1/manufacturer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"TestManufacturer3\"" +
                                "}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void getManufacturerListTest() {

        given(manufacturerGateway.getManufacturerList()).willReturn(manufacturerDtoList);

        List<ManufacturerDto> manufacturerList = manufacturerController.getManufacturerList();

        then(manufacturerGateway).should().getManufacturerList();

        assertAll(
                () -> assertEquals(2, manufacturerList.size(), "Size "),
                () -> assertEquals("TestManufacturer1", manufacturerList.get(0).getName())
        );
    }
}