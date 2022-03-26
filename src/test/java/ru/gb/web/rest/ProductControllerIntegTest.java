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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.gb.api.category.dto.CategoryDto;
import ru.gb.api.common.enums.Status;
import ru.gb.api.product.dto.ProductDto;
import ru.gb.dao.CategoryDao;
import ru.gb.dao.ManufacturerDao;
import ru.gb.dao.ProductDao;
import ru.gb.entity.Category;
import ru.gb.entity.Manufacturer;
import ru.gb.service.ManufacturerService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class ProductControllerIntegTest {

    final private static String PRODUCT_TITLE = "Apple";
    final private static BigDecimal PRODUCT_COST = BigDecimal.valueOf(100.0);
    final private static LocalDate MANUFACTURE_DATE = LocalDate.of(2022, 1, 10);
    final private static String STRING_MANUFACTURE_DATE = MANUFACTURE_DATE.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    final private static Status PRODUCT_STATUS = Status.ACTIVE;
    final private static String UPDATED_PRODUCT_TITLE = "Apple";
    final private static String MANUFACTURER_NAME = "Farmer";
    final private static String FIRST_CATEGORY_TITLE = "Fruit";
    final private static String SECOND_CATEGORY_TITLE = "Vegetables";
    final private static Set<String> CATEGORIES = Set.of(
            FIRST_CATEGORY_TITLE, SECOND_CATEGORY_TITLE);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Autowired
    private ManufacturerService manufacturerService;

    @Test
    @Order(1)
    public void handleWithNonExistsManufacturerTest() throws Exception {
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title(PRODUCT_TITLE)
                                        .cost(PRODUCT_COST)
                                        .manufactureDate(MANUFACTURE_DATE)
                                        .status(PRODUCT_STATUS)
                                        .manufacturer(MANUFACTURER_NAME)
                                        .categories(CATEGORIES)
                                        .build()
                                )
                        ))
                .andExpect(status().isConflict());

        assertAll(
                () -> assertEquals(0, manufacturerDao.count(), "Manufacturer count mustn't change"),
                () -> assertEquals(0, categoryDao.count(), "Category count mustn't change"),
                () -> assertEquals(0, productDao.count(), "Product count mustn't change")
        );

    }

    @Test
    @Order(2)
    public void handlePostTest() throws Exception {
        // given

        categoryDao.save(Category.builder().title(FIRST_CATEGORY_TITLE).build());
        categoryDao.save(Category.builder().title(SECOND_CATEGORY_TITLE).build());
        manufacturerDao.save(Manufacturer.builder().name(MANUFACTURER_NAME).build());

        // when
        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .title(PRODUCT_TITLE)
                                        .cost(PRODUCT_COST)
                                        .manufactureDate(MANUFACTURE_DATE)
                                        .status(PRODUCT_STATUS)
                                        .manufacturer(MANUFACTURER_NAME)
                                        .categories(CATEGORIES)
                                        .build()
                                )
                        ))
                .andExpect(status().isCreated());

        System.out.println("after fourth: " + categoryDao.count());

        assertAll(
                () -> assertEquals(1, productDao.count()),
                () -> assertEquals(1, manufacturerDao.count()),
                () -> assertEquals(2, categoryDao.count()),
                () -> assertEquals(MANUFACTURER_NAME, manufacturerService.findById(1L).getName())
        );
    }

    @Test
    @Order(3)
    public void getProductListTest() throws Exception {
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(PRODUCT_TITLE))
                .andExpect(jsonPath("$.[0].cost").value(PRODUCT_COST))
                .andExpect(jsonPath("$.[0].manufactureDate").value(STRING_MANUFACTURE_DATE))
                .andExpect(jsonPath("$.[0].status").value(PRODUCT_STATUS.toString()))
                .andExpect(jsonPath("$.[0].manufacturer").value(MANUFACTURER_NAME))
                .andExpect(jsonPath("$.[0].categories.length()").value(2));
    }

    @Test
    @Order(4)
    public void getInfoProductListTest() throws Exception {
        mockMvc.perform(get("/api/v1/product/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].title").value(PRODUCT_TITLE))
                .andExpect(jsonPath("$.[0].cost").value(PRODUCT_COST))
                .andExpect(jsonPath("$.[0].manufactureDate").value(STRING_MANUFACTURE_DATE));
    }

    @Test
    @Order(5)
    public void handleUpdateTest() throws Exception {
        mockMvc.perform(put("/api/v1/product/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper
                                .writeValueAsString(ProductDto.builder()
                                        .id(1L)
                                        .title(UPDATED_PRODUCT_TITLE)
                                        .cost(PRODUCT_COST)
                                        .manufactureDate(MANUFACTURE_DATE)
                                        .status(PRODUCT_STATUS)
                                        .manufacturer(MANUFACTURER_NAME)
                                        .categories(CATEGORIES)
                                        .build()
                                )
                        ))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void getProductTest() throws Exception {
        mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("title").value(UPDATED_PRODUCT_TITLE))
                .andExpect(jsonPath("cost").value(PRODUCT_COST))
                .andExpect(jsonPath("manufactureDate").value(STRING_MANUFACTURE_DATE))
                .andExpect(jsonPath("status").value(PRODUCT_STATUS.toString()))
                .andExpect(jsonPath("manufacturer").value(MANUFACTURER_NAME))
                .andExpect(jsonPath("categories.length()").value(2));
    }

    @Test
    @Order(7)
    public void deleteByIdTest() throws Exception {
        mockMvc.perform(delete("/api/v1/product/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(8)
    public void getZeroCategoryListTest() throws Exception {
        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }
}