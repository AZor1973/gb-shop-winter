package ru.gb.api.manufacturer.api;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.api.manufacturer.dto.ManufacturerDto;

import java.util.List;

@FeignClient(url = "http://127.0.0.1:8080/internal/api/v1/manufacturer", name = "ManufacturerGateway")
public interface ManufacturerGateway {

    @GetMapping
    List<ManufacturerDto> getManufacturerList();

    @GetMapping("/{manufacturerId}")
    ResponseEntity<?> getManufacturer(@PathVariable("manufacturerId") Long id);

    @PostMapping
    ResponseEntity<?> handlePost(@Validated @RequestBody ManufacturerDto manufacturerDto);

    @PutMapping("/{manufacturerId}")
    ResponseEntity<?> handleUpdate(@PathVariable("manufacturerId") Long id,
                                          @Validated @RequestBody ManufacturerDto manufacturerDto);

    @DeleteMapping("/{manufacturerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable("manufacturerId") Long id);
}
