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
import ru.gb.api.product.api.ProductGateway;
import ru.gb.api.product.dto.ProductDto;

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
public class ProductControllerTest {
    @Mock
    ProductGateway productGateway;

    @InjectMocks
    ProductController productController;

    MockMvc mockMvc;

    List<ProductDto> productDtoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        productDtoList.add(ProductDto.builder().id(1L).title("TestProduct1").build());
        productDtoList.add(ProductDto.builder().id(2L).title("TestProduct2").build());

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void productListTest() throws Exception {

        given(productGateway.getProductList()).willReturn(productDtoList);

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value("TestProduct1"))
                .andExpect(jsonPath("$.[1].title").value("TestProduct2"));
    }

    @Test
    public void createProductTest() throws Exception {
        given(productGateway.handlePost(any(ProductDto.class))).willReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"name\": \"TestProduct3\"" +
                                "}"))
                .andExpect(status().isCreated());

    }

    @Test
    public void getProductListTest() {

        given(productGateway.getProductList()).willReturn(productDtoList);

        List<ProductDto> productList = productController.getProductList();

        then(productGateway).should().getProductList();

        assertAll(
                () -> assertEquals(2, productList.size(), "Size "),
                () -> assertEquals("TestProduct1", productList.get(0).getTitle())
        );
    }
}