package com.example.footballbootswebapiis.mappers;

import com.example.footballbootswebapiis.enumlayer.Brand;
import com.example.footballbootswebapiis.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FootballBootsMapper {

    public static FootballBoots mapFromCreateRequestToModel(FootballBootsCreateRequest footballBootsCreateRequest) {
        List<FootballBootsAttributes> footballBootsAttributesList = new ArrayList<>();
        footballBootsCreateRequest.getFootballBootsAttributesCreateRequestList().stream()
                .forEach(football -> footballBootsAttributesList.add(FootballBootsMapper.mapFromCreateRequestToAttributesModel(football)));
        return new FootballBoots(footballBootsCreateRequest.getName(), footballBootsCreateRequest.getDescription(), Brand.valueOf(footballBootsCreateRequest.getBrand()),
                footballBootsCreateRequest.getPhotoPath(), footballBootsCreateRequest.getBigPhotoPath(), footballBootsAttributesList);
    }

    public static List<FootballBootsDefaultSizeResponse> mapFromDefaultResponseToArrayResponse(List<FootballBoots> footballBoots) {
        List<FootballBootsDefaultSizeResponse> footballBootsDefaultSizeResponseList = new ArrayList<>();
        for (FootballBoots f : footballBoots) {
            footballBootsDefaultSizeResponseList.add(FootballBootsMapper.mapFromModelToDefaultResponse(f));
        }
        return footballBootsDefaultSizeResponseList;
    }

    public static FootballBootsDefaultSizeResponse mapFromModelToDefaultResponse(FootballBoots footballBoots) {
        List<FootballBootsAttributes> footballBootsAttributes =
                footballBoots.getFootballBootsAttributesList().stream().
                        filter(attribute -> attribute.getSize() == 40).collect(Collectors.toList());
        return new FootballBootsDefaultSizeResponse(footballBoots.getId(), footballBoots.getName(),
                footballBootsAttributes.get(0).getPrice(),
                footballBootsAttributes.get(0).getQuantity(), footballBootsAttributes.get(0).getSize(),
                footballBoots.getDescription(), footballBoots.getBrand().name(), footballBoots.getPhotoPath(), footballBoots.getBigPhotoPath());
    }

    public static FootballBootsDefaultSizeResponse mapFromModelToSizeResponse(FootballBoots footballBoots, int size) {
        List<FootballBootsAttributes> footballBootsAttributes =
                footballBoots.getFootballBootsAttributesList().stream().
                        filter(attribute -> attribute.getSize() == size).collect(Collectors.toList());
        return new FootballBootsDefaultSizeResponse(footballBoots.getId(), footballBoots.getName(),
                footballBootsAttributes.get(0).getPrice(),
                footballBootsAttributes.get(0).getQuantity(), footballBootsAttributes.get(0).getSize(),
                footballBoots.getDescription(), footballBoots.getBrand().name(), footballBoots.getPhotoPath(), footballBoots.getBigPhotoPath());
    }

    public static FootballBootsAttributes mapFromCreateRequestToAttributesModel(FootballBootsAttributesCreateRequest footballBootsAttributesCreateRequest) {
        return new FootballBootsAttributes(footballBootsAttributesCreateRequest.getSize(), footballBootsAttributesCreateRequest.getQuantity(),
                footballBootsAttributesCreateRequest.getPrice());
    }

    public static FootballBootsAttributesUpdateResponse mapFromAttributesModelToUpdateResponse(FootballBootsAttributes footballBootsAttributes) {
        return new FootballBootsAttributesUpdateResponse(footballBootsAttributes.getId(), footballBootsAttributes.getSize(), footballBootsAttributes.getPrice(),
                footballBootsAttributes.getQuantity());
    }

    public static FootballBootsUpdateResponse mapFromModelToUpdateResponse(FootballBoots footballBoots) {
        List<FootballBootsAttributesUpdateResponse> footballBootsAttributesUpdateResponseList = new ArrayList<>();
        footballBoots.getFootballBootsAttributesList().forEach(atttribute -> footballBootsAttributesUpdateResponseList.add(FootballBootsMapper.mapFromAttributesModelToUpdateResponse(atttribute)));
        return new FootballBootsUpdateResponse(footballBoots.getId(), footballBoots.getName(), footballBoots.getDescription(), footballBoots.getBrand().name(), footballBootsAttributesUpdateResponseList);
    }

    public static FootballBootsCreateResponse mapFromModelToCreateResponse(FootballBoots footballBoots) {
        List<FootbalBootsAttributesCreateResponse> footballBootsAttributesCreateResponseList = new ArrayList<>();
        footballBoots.getFootballBootsAttributesList().forEach(attribute -> footballBootsAttributesCreateResponseList.add(FootballBootsMapper.mapFromModelAttributesToCreateResponse(attribute)));
        return new FootballBootsCreateResponse(footballBoots.getName(), footballBoots.getDescription(), footballBoots.getBrand(), footballBoots.getPhotoPath(), footballBoots.getBigPhotoPath(), footballBootsAttributesCreateResponseList);
    }

    public static FootbalBootsAttributesCreateResponse mapFromModelAttributesToCreateResponse(FootballBootsAttributes footballBootsAttributes) {
        return new FootbalBootsAttributesCreateResponse(footballBootsAttributes.getSize(), footballBootsAttributes.getPrice(),
                footballBootsAttributes.getQuantity());
    }
}
