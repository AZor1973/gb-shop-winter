package ru.gb.api.manufacturer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerDto {

    @JsonProperty(value = "id")
    private Long manufacturerId;
    private String name;

    @Override
    public String toString() {
        return "{name=" + name + ", id=" + manufacturerId + "}";
    }
}
