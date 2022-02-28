package ru.gb.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.manufacturer.dto.ManufacturerDto;
import ru.gb.service.ManufacturerService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manufacturer")
public class ManufacturerRestController {

    private final ManufacturerService manufacturerService;

    @GetMapping
    public List<ManufacturerDto> getManufacturerList() {
        return manufacturerService.findAll();
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<?> getManufacturer(@PathVariable("manufacturerId") Long id) {
        ManufacturerDto manufacturerDto;
        if (id != null) {
            manufacturerDto = manufacturerService.findById(id);
            if (manufacturerDto != null) {
                return new ResponseEntity<>(manufacturerDto, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody ManufacturerDto manufacturerDto) {
        manufacturerService.save(manufacturerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("manufacturerId") Long id,
                                          @Validated @RequestBody ManufacturerDto manufacturerDto) {
        log.info("Change manufacturer with id {}", id);
        manufacturerDto.setManufacturerId(id);
        manufacturerService.save(manufacturerDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{manufacturerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("manufacturerId") Long id) {
        manufacturerService.deleteById(id);
    }
}
