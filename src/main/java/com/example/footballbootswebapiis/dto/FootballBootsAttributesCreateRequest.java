package com.example.footballbootswebapiis.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Validated
public class FootballBootsAttributesCreateRequest {
    @Range(min = 35, max = 45, message = "Size for football boots must be between 35 and 45.")
    @NotNull
    private Integer size;
    @Min(value = 0, message = "Price can't be negative.")
    @NotNull
    private Integer price;
    @Min(value = 0, message = "Quantity can't be negative")
    @NotNull
    private Integer quantity;
}
