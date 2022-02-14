package ru.gb.external.api.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.manufacturer.api.ManufacturerGateway;
import ru.gb.api.manufacturer.dto.ManufacturerDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/manufacturer")
public class ManufacturerController {

    private final ManufacturerGateway manufacturerGateway;

    @GetMapping
    public List<ManufacturerDto> getManufacturerList() {
        return manufacturerGateway.getManufacturerList();
    }

    @GetMapping("/{manufacturerId}")
    public ResponseEntity<?> getManufacturer(@PathVariable("manufacturerId") Long id) {
        return manufacturerGateway.getManufacturer(id);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody ManufacturerDto manufacturerDto) {
        return manufacturerGateway.handlePost(manufacturerDto);
    }

    @PutMapping("/{manufacturerId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("manufacturerId") Long id,
                                          @Validated @RequestBody ManufacturerDto manufacturerDto) {
        log.info("Change manufacturer with id {}", id);
        return manufacturerGateway.handleUpdate(id, manufacturerDto);
    }

    @DeleteMapping("/{manufacturerId}")
    public void deleteById(@PathVariable("manufacturerId") Long id) {
        manufacturerGateway.deleteById(id);
    }
}
