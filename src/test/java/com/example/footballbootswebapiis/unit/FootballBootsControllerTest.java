package com.example.footballbootswebapiis.unit;

import com.example.footballbootswebapiis.controller.FootballBootsController;
import com.example.footballbootswebapiis.dto.*;
import com.example.footballbootswebapiis.enumlayer.Brand;
import com.example.footballbootswebapiis.mappers.FootballBootsMapper;
import com.example.footballbootswebapiis.model.*;
import com.example.footballbootswebapiis.service.FootballBootsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FootballBootsControllerTest {
    private final static String DESCRIPTION = "Description.";
    private final static String BRAND = "NIKE";

    private List<FootballBoots> list;

    @InjectMocks
    private FootballBootsController footballBootsController;
    @Mock
    private FootballBootsService footballBootsService;
    private FootballBootsCreateRequest footballBootsCreateRequest;
    private FootballBootsUpdateRequest footballBootsUpdateRequest;


    @Before
    public void setup() {
        list = new ArrayList<>();

        FootballBoots footballBoots1 = new FootballBoots();
        footballBoots1.setId(1);
        footballBoots1.setName("Boots1");
        footballBoots1.setDescription(DESCRIPTION);
        footballBoots1.setBrand(Brand.valueOf(BRAND));
        footballBoots1.setFootballBootsAttributesList(Arrays.asList(new FootballBootsAttributes(40, 100, 100)));
        footballBoots1.setPhotoPath(null);
        footballBoots1.setBigPhotoPath(null);

        FootballBoots footballBoots2 = new FootballBoots();
        footballBoots2.setId(2);
        footballBoots2.setName("Boots2");
        footballBoots2.setDescription(DESCRIPTION);
        footballBoots2.setBrand(Brand.valueOf(BRAND));
        footballBoots2.setFootballBootsAttributesList(Arrays.asList(new FootballBootsAttributes(40, 100, 100)));
        footballBoots2.setPhotoPath(null);
        footballBoots2.setBigPhotoPath(null);

        FootballBoots footballBoots3 = new FootballBoots();
        footballBoots3.setId(3);
        footballBoots3.setName("Boots3");
        footballBoots3.setDescription(DESCRIPTION);
        footballBoots3.setBrand(Brand.valueOf(BRAND));
        footballBoots3.setFootballBootsAttributesList(Arrays.asList(new FootballBootsAttributes(40, 100, 100)));

        footballBoots3.setPhotoPath(null);
        footballBoots3.setBigPhotoPath(null);

        list.addAll(Arrays.asList(footballBoots1, footballBoots2, footballBoots3));
        footballBootsCreateRequest = new FootballBootsCreateRequest("name", "desc", "NIKE", null, null, new ArrayList<>());
        footballBootsUpdateRequest = new FootballBootsUpdateRequest("name", "desc", "NIKE");

        when(footballBootsService.getFootballBoots()).thenReturn(list);
        when(footballBootsService.getBootsById(1)).thenReturn(Optional.of(list.get(0)));
        when(footballBootsService.getBootsById(2)).thenReturn(Optional.of(list.get(1)));
        when(footballBootsService.getBootsById(3)).thenReturn(Optional.of(list.get(2)));
        when(footballBootsService.createFootballBoots(FootballBootsMapper.mapFromCreateRequestToModel(footballBootsCreateRequest))).thenReturn(new FootballBootsCreateResponse(footballBoots1.getName(),
                footballBoots1.getDescription(),
                footballBoots1.getBrand(),
                null,
                null,
                new ArrayList<>()));
        when(footballBootsService.updateFootballBootsById(2, footballBootsUpdateRequest)).thenReturn(footballBoots2);
    }

    @Test
    public void getAllBoots() {
        ResponseEntity<List<FootballBootsDefaultSizeResponse>> responseEntity = footballBootsController.getAllFootballBoots();

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().size(), list.size());

        FootballBootsDefaultSizeResponse footballBootsDefaultSizeResponse = responseEntity.getBody().get(0);
        assertEquals(footballBootsDefaultSizeResponse.getId(), list.get(0).getId());
        assertEquals(footballBootsDefaultSizeResponse.getName(), list.get(0).getName());
        assertEquals(footballBootsDefaultSizeResponse.getDescription(), list.get(0).getDescription());
        assertEquals(footballBootsDefaultSizeResponse.getBrand(), list.get(0).getBrand().name());

        footballBootsDefaultSizeResponse = responseEntity.getBody().get(1);
        assertEquals(footballBootsDefaultSizeResponse.getId(), list.get(1).getId());
        assertEquals(footballBootsDefaultSizeResponse.getName(), list.get(1).getName());
        assertEquals(footballBootsDefaultSizeResponse.getDescription(), list.get(1).getDescription());
        assertEquals(footballBootsDefaultSizeResponse.getBrand(), list.get(1).getBrand().name());

        footballBootsDefaultSizeResponse = responseEntity.getBody().get(2);
        assertEquals(footballBootsDefaultSizeResponse.getId(), list.get(2).getId());
        assertEquals(footballBootsDefaultSizeResponse.getName(), list.get(2).getName());
        assertEquals(footballBootsDefaultSizeResponse.getDescription(), list.get(2).getDescription());
        assertEquals(footballBootsDefaultSizeResponse.getBrand(), list.get(2).getBrand().name());
    }

    @Test
    public void getBootsByIdAndSize() {
        ResponseEntity<FootballBootsDefaultSizeResponse> responseEntity = footballBootsController.getFootballBootsByIdAndSize(1, 40);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(responseEntity.getBody());

        assertEquals(responseEntity.getBody().getId(), list.get(0).getId());
        assertEquals(responseEntity.getBody().getName(), list.get(0).getName());
        assertEquals(responseEntity.getBody().getDescription(), list.get(0).getDescription());
        assertEquals(responseEntity.getBody().getBrand(), list.get(0).getBrand().name());

        responseEntity = footballBootsController.getFootballBootsByIdAndSize(2, 40);
        assertEquals(responseEntity.getBody().getId(), list.get(1).getId());
        assertEquals(responseEntity.getBody().getName(), list.get(1).getName());
        assertEquals(responseEntity.getBody().getDescription(), list.get(1).getDescription());
        assertEquals(responseEntity.getBody().getBrand(), list.get(1).getBrand().name());


        responseEntity = footballBootsController.getFootballBootsByIdAndSize(3, 40);
        assertEquals(responseEntity.getBody().getId(), list.get(2).getId());
        assertEquals(responseEntity.getBody().getName(), list.get(2).getName());
        assertEquals(responseEntity.getBody().getDescription(), list.get(2).getDescription());
        assertEquals(responseEntity.getBody().getBrand(), list.get(2).getBrand().name());
    }

    @Test
    public void deleteBootsById() {
        ResponseEntity entity = footballBootsController.deleteFootballBootsById(1);
        verify(footballBootsService, times(1)).deleteFootballBootsById(1);

        assertTrue(entity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void createBoots() {
        ResponseEntity<FootballBootsCreateResponse> responseEntity = this.footballBootsController.createFootballBoots(
                footballBootsCreateRequest);

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getName(), list.get(0).getName());
        assertEquals(responseEntity.getBody().getBrand(), list.get(0).getBrand());
        assertEquals(responseEntity.getBody().getDescription(), list.get(0).getDescription());
        verify(footballBootsService).createFootballBoots(FootballBootsMapper.mapFromCreateRequestToModel(footballBootsCreateRequest));
    }

    @Test
    public void updateFootballBoots() {
        ResponseEntity<FootballBootsUpdateResponse> footballBootsUpdateResponseResponseEntity = this.footballBootsController
                .updateFootballBootsById(footballBootsUpdateRequest, 2);

        assertTrue(footballBootsUpdateResponseResponseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(footballBootsUpdateResponseResponseEntity.getBody());
        verify(footballBootsService).updateFootballBootsById(2, footballBootsUpdateRequest);
        assertEquals(list.get(1).getName(), footballBootsUpdateResponseResponseEntity.getBody().getName());
        assertEquals(list.get(1).getDescription(), footballBootsUpdateResponseResponseEntity.getBody().getDescription());
        assertEquals(list.get(1).getBrand().name(), footballBootsUpdateResponseResponseEntity.getBody().getBrand());
    }
}
