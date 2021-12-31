package com.example.footballbootswebapiis.mappers;

import com.example.footballbootswebapiis.enumlayer.Brand;
import com.example.footballbootswebapiis.enumlayer.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BrandConverter implements AttributeConverter<Brand, String> {
    @Override
    public String convertToDatabaseColumn(Brand brand) {
        return brand.toString();
    }

    @Override
    public Brand convertToEntityAttribute(String s) {
        return Brand.valueOf(s);
    }
}
