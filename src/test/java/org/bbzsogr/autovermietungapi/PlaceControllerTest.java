package org.bbzsogr.autovermietungapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.dto.PlaceDTO;
import org.bbzsogr.autovermietungapi.model.Place;
import org.bbzsogr.autovermietungapi.repository.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class PlaceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlaceRepository placeRepository;
    @Autowired
    private JWTTokenDecoder tokenDecoder;

    /**
     * T28
     */
    @Test
    public void testCreatePlaceValid() throws Exception {
        Claims claims = Claims.builder()
                .permissions(new String[]{"create:place"})
                .build();

        PlaceDTO placeDTO = PlaceDTO.builder().name("Test").plz(4500).build();

        Place place = placeDTO.intoEntity();
        place.setId(1);

        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));
        Mockito.when(placeRepository.save(any())).thenReturn(place);

        mockMvc.perform(post("/places")
                        .content(new ObjectMapper().writeValueAsString(placeDTO))
                        .contentType("application/json")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isCreated());
    }

    /**
     * T29
     */
    @Test
    public void testCreatePlaceInvalid() throws Exception {
        Claims claims = Claims.builder()
                .permissions(new String[]{"create:place"})
                .build();

        Place place = Place.builder().id(1).name("Test").plz(4500).build();
        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));
        Mockito.when(placeRepository.save(any())).thenReturn(place);

        Map<String, String> invalidData = new HashMap<>();
        invalidData.put("Test", "Test");

        mockMvc.perform(post("/places")
                        .content(new ObjectMapper().writeValueAsString(invalidData))
                        .contentType("application/json")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isBadRequest());
    }

    /**
     * T30
     */
    @Test
    public void testEditPlaceValid() throws Exception {
        Claims claims = Claims.builder()
                .permissions(new String[]{"edit:place"})
                .build();

        PlaceDTO placeDTO = PlaceDTO.builder().name("Test").plz(4500).build();
        Place place = placeDTO.intoEntity();
        place.setId(1);

        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));
        Mockito.when(placeRepository.save(any())).thenReturn(place);
        Mockito.when(placeRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(put("/places/1")
                        .content(new ObjectMapper().writeValueAsString(placeDTO))
                        .contentType("application/json")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk());
    }

    /**
     * T31
     */
    @Test
    public void testEditPlaceInvalid() throws Exception {
        Claims claims = Claims.builder()
                .permissions(new String[]{"edit:place"})
                .build();

        PlaceDTO placeDTO = PlaceDTO.builder().name("Test").plz(4500).build();
        Place place = placeDTO.intoEntity();
        place.setId(1);

        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));
        Mockito.when(placeRepository.save(any())).thenReturn(place);
        Mockito.when(placeRepository.existsById(1)).thenReturn(false);

        Map<String, String> invalidData = new HashMap<>();
        invalidData.put("Test", "Test");

        mockMvc.perform(put("/places/1")
                        .content(new ObjectMapper().writeValueAsString(invalidData))
                        .contentType("application/json")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isBadRequest());
    }

    /**
     * T32
     */
    @Test
    public void testDeletePlaceValid() throws Exception {
        Claims claims = Claims.builder()
                .permissions(new String[]{"delete:place"})
                .build();

        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));
        Mockito.doNothing().when(placeRepository).deleteById(1);
        Mockito.when(placeRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete("/places/1")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk());
    }

    /**
     * T33
     */
    @Test
    public void testDeletePlaceInvalid() throws Exception {
        Claims claims = Claims.builder()
                .permissions(new String[]{"delete:place"})
                .build();

        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));
        Mockito.doNothing().when(placeRepository).deleteById(1);
        Mockito.when(placeRepository.existsById(1)).thenReturn(false);

        mockMvc.perform(delete("/places/1")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isNotFound());
    }

    /**
     * T34
     */
    @Test
    public void testViewPlaceValid() {

    }

    /**
     * T35
     */
    @Test
    public void testViewPlaceInvalid() {

    }
}
