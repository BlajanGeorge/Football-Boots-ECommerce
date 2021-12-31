package com.example.footballbootswebapiis.model;

import com.example.footballbootswebapiis.enumlayer.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@AllArgsConstructor
@Validated
public class FootballBootsCreateRequest {
    @NotBlank(message = "Boots name can't be blank.")
    private String name;
    private String description;
    @NotNull
    private String brand;
    @NotNull
    private List<FootballBootsAttributesCreateRequest> footballBootsAttributesCreateRequestList;
}
