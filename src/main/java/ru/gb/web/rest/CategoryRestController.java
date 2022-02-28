package ru.gb.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.category.dto.CategoryDto;
import ru.gb.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategoryList() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") Long id) {
        CategoryDto categoryDto;
        if (id != null) {
            categoryDto = categoryService.findById(id);
            if (categoryDto != null) {
                return new ResponseEntity<>(categoryDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto) {
        categoryService.save(categoryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("categoryId") Long id,
                                          @Validated @RequestBody CategoryDto categoryDto) {
        categoryDto.setCategoryId(id);
        categoryService.save(categoryDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("categoryId") Long id) {
        categoryService.deleteById(id);
    }
}
