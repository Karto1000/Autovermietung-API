package org.bbzsogr.autovermietungapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.model.Car;
import org.bbzsogr.autovermietungapi.model.Rental;
import org.bbzsogr.autovermietungapi.model.Role;
import org.bbzsogr.autovermietungapi.model.User;
import org.bbzsogr.autovermietungapi.repository.CarRepository;
import org.bbzsogr.autovermietungapi.repository.RentalRepository;
import org.bbzsogr.autovermietungapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class RentalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTTokenDecoder tokenDecoder;
    @Autowired
    @MockBean
    private RentalRepository rentalRepository;
    @Autowired
    @MockBean
    private UserRepository userRepository;
    @Autowired
    @MockBean
    private CarRepository carRepository;

    /**
     * T40
     */
    @Test
    public void testCancelRentalValid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"delete:rental"})
                .build();

        User user = User.builder()
                .id(1)
                .email("admin@admin.com")
                .role(Role.builder().id(1).name("admin").build())
                .build();

        Car car = Car.builder()
                .id(1)
                .model("Test")
                .pricePerHour(123.)
                .brand("Test")
                .build();

        Rental rental = Rental.builder()
                .id(1)
                .end(1)
                .start(2)
                .car(car)
                .user(user)
                .build();

        Mockito.doNothing().when(rentalRepository).deleteById(1);
        Mockito.when(rentalRepository.findById(1)).thenReturn(Optional.of(rental));
        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));

        mockMvc.perform(put(String.format("/rentals/%d/cancel", car.getId()))
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk());
    }

    /**
     * T41
     */
    @Test
    public void testCancelRentalInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"delete:rental"})
                .build();

        Car car = Car.builder()
                .id(1)
                .model("Test")
                .pricePerHour(123.)
                .brand("Test")
                .build();

        Mockito.doNothing().when(rentalRepository).deleteById(1);
        Mockito.when(rentalRepository.findById(1)).thenReturn(Optional.empty());
        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));

        mockMvc.perform(put(String.format("/rentals/%d/cancel", car.getId()))
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isNotFound());
    }

    /**
     * T42
     */
    @Test
    public void testViewRentalsValid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"view:rental"})
                .build();

        User user = User.builder()
                .id(1)
                .email("admin@admin.com")
                .role(Role.builder().id(1).name("admin").build())
                .build();

        Rental rental = Rental.builder()
                .id(1)
                .end(1)
                .start(2)
                .car(Car.builder().model("Test").build())
                .user(user)
                .build();

        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));
        Mockito.when(userRepository.findByEmail("admin@admin.com")).thenReturn(Optional.of(user));
        Mockito.when(rentalRepository.search("Test", 1)).thenReturn(Collections.singletonList(rental));

        MvcResult result = mockMvc.perform(get("/rentals")
                        .param("q", "Test")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andReturn();

        Rental[] rentals = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Rental[].class);

        assert rentals.length == 1;
        assert rentals[0].equals(rental);
    }


    /**
     * T43
     */
    @Test
    public void testViewRentalsInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"view:rental"})
                .build();

        User user = User.builder()
                .id(1)
                .email("admin@admin.com")
                .role(Role.builder().id(1).name("admin").build())
                .build();

        Mockito.when(tokenDecoder.decode(any())).thenReturn(Optional.of(claims));
        Mockito.when(userRepository.findByEmail("admin@admin.com")).thenReturn(Optional.of(user));
        Mockito.when(rentalRepository.search("Test", 1)).thenReturn(Collections.emptyList());

        MvcResult result = mockMvc.perform(get("/rentals")
                        .param("q", "Test")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andReturn();

        Rental[] rentals = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Rental[].class);

        assert rentals.length == 0;
    }
}
