package com.example.footballbootswebapiis.dto;

import com.example.footballbootswebapiis.dto.FootballBootsAttributesCreateRequest;
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
    private String photoPath;
    @NotNull
    private String bigPhotoPath;
    @NotNull
    private List<FootballBootsAttributesCreateRequest> footballBootsAttributesCreateRequestList;
}
