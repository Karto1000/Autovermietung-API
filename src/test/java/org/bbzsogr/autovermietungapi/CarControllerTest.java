package org.bbzsogr.autovermietungapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.dto.CarDTO;
import org.bbzsogr.autovermietungapi.dto.RentDTO;
import org.bbzsogr.autovermietungapi.model.*;
import org.bbzsogr.autovermietungapi.repository.CarRepository;
import org.bbzsogr.autovermietungapi.repository.FirmRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @MockBean
    private JWTTokenDecoder tokenDecoder;

    @Autowired
    @MockBean
    private CarRepository carRepository;

    @Autowired
    @MockBean
    private FirmRepository firmRepository;

    @Autowired
    @MockBean
    private UserRepository userRepository;

    @Autowired
    @MockBean
    private RentalRepository rentalRepository;

    /**
     * T17
     */
    @Test
    public void testCreateRentableCarValid() throws Exception {
        Claims claims = Claims.builder()
                .email("firm@firm.com")
                .permissions(new String[]{"create:car"})
                .build();

        Firm firm = Firm.builder()
                .id(1)
                .name("Firm")
                .build();

        CarDTO carDTO = CarDTO.builder()
                .brand("BMW")
                .model("X5")
                .pricePerHour(10.)
                .build();

        Mockito.when(firmRepository.findByEmail(claims.getEmail())).thenReturn(Optional.of(firm));
        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.save(Mockito.any())).thenReturn(null);

        mockMvc.perform(post("/cars")
                        .header("Authorization", "Bearer test")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(carDTO)))
                .andExpect(status().isCreated());
    }

    /**
     * T18
     */
    @Test
    public void testCreateRentableCarInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("firm@firm.com")
                .permissions(new String[]{"create:car"})
                .build();

        Firm firm = Firm.builder()
                .id(1)
                .name("Firm")
                .build();

        CarDTO carDTO = CarDTO.builder()
                .model("X5")
                .pricePerHour(10.)
                .build();

        Mockito.when(firmRepository.findByEmail(claims.getEmail())).thenReturn(Optional.of(firm));
        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.save(Mockito.any())).thenReturn(null);

        mockMvc.perform(post("/cars")
                        .header("Authorization", "Bearer test")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(carDTO)))
                .andExpect(status().isBadRequest());
    }

    /**
     * T19
     */
    @Test
    public void testDeleteCarValid() throws Exception {
        Claims claims = Claims.builder()
                .email("firm@firm.com")
                .permissions(new String[]{"delete:car"})
                .build();

        Firm firm = Firm.builder()
                .id(1)
                .name("Firm")
                .build();

        Mockito.when(firmRepository.findByEmail(claims.getEmail())).thenReturn(Optional.of(firm));
        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.existsById(1)).thenReturn(true);

        mockMvc.perform(delete(String.format("/cars/%d", 1))
                        .header("Authorization", "Bearer test")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }


    /**
     * T20
     */
    @Test
    public void testDeleteCarInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("firm@firm.com")
                .permissions(new String[]{"delete:car"})
                .build();

        Firm firm = Firm.builder()
                .id(1)
                .name("Firm")
                .build();

        Mockito.when(firmRepository.findByEmail(claims.getEmail())).thenReturn(Optional.of(firm));
        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.existsById(2)).thenReturn(false);

        mockMvc.perform(delete(String.format("/cars/%d", 2))
                        .header("Authorization", "Bearer test")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * T21
     */
    @Test
    public void testEditCarValid() throws Exception {
        Claims claims = Claims.builder()
                .email("firm@firm.com")
                .permissions(new String[]{"edit:car"})
                .build();

        Firm firm = Firm.builder()
                .id(1)
                .name("Firm")
                .build();

        CarDTO carDTO = CarDTO.builder()
                .brand("BMW")
                .model("X5")
                .pricePerHour(10.)
                .build();

        Car car = carDTO.intoEntity();
        car.setId(1);
        car.setFirm(firm);

        Mockito.when(firmRepository.findByEmail(claims.getEmail())).thenReturn(Optional.of(firm));
        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.existsById(1)).thenReturn(true);
        Mockito.when(carRepository.save(Mockito.any())).thenReturn(car);

        mockMvc.perform(put(String.format("/cars/%d", 1))
                        .header("Authorization", "Bearer test")
                        .content(new ObjectMapper().writeValueAsString(carDTO))
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    /**
     * T22
     */
    @Test
    public void testEditCarInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("firm@firm.com")
                .permissions(new String[]{"edit:car"})
                .build();

        Firm firm = Firm.builder()
                .id(1)
                .name("Firm")
                .build();

        CarDTO carDTO = CarDTO.builder()
                .brand("BMW")
                .model("X5")
                .pricePerHour(10.)
                .build();

        Car car = carDTO.intoEntity();
        car.setId(1);
        car.setFirm(firm);

        Mockito.when(firmRepository.findByEmail(claims.getEmail())).thenReturn(Optional.of(firm));
        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.existsById(2)).thenReturn(false);
        Mockito.when(carRepository.save(Mockito.any())).thenReturn(car);

        mockMvc.perform(put(String.format("/cars/%d", 2))
                        .header("Authorization", "Bearer test")
                        .content(new ObjectMapper().writeValueAsString(carDTO))
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    /**
     * T23
     */
    @Test
    public void testSearchForCarValid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"view:car"})
                .build();

        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .pricePerHour(10.)
                .build();

        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.search("BMW", "X5")).thenReturn(Collections.singletonList(car));

        MvcResult result = mockMvc.perform(get("/cars?brand=BMW&model=X5")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andReturn();

        Car[] cars = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Car[].class);

        assert cars.length == 1;
        assert cars[0].equals(car);
    }

    /**
     * T24
     */
    @Test
    public void testSearchForCarInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"view:car"})
                .build();

        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.search("BMW", "X5")).thenReturn(Collections.emptyList());

        MvcResult result = mockMvc.perform(get("/cars?brand=BMW&model=X5")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andReturn();

        Car[] cars = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Car[].class);

        assert cars.length == 0;
    }

    /**
     * T25
     */
    @Test
    public void testRentCarValid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"rent:car"})
                .build();

        User user = User.builder()
                .id(1)
                .email("admin@admin.com")
                .role(Role.builder().name("admin").id(1).build())
                .build();

        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .pricePerHour(10.)
                .build();

        RentDTO rentDTO = RentDTO.builder()
                .start(1)
                .end(2)
                .build();

        Rental rental = rentDTO.intoEntity();
        rental.setId(1);
        rental.setCar(car);
        rental.setUser(user);

        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.existsById(1)).thenReturn(true);
        Mockito.when(userRepository.findByEmail(claims.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findById(1)).thenReturn(Optional.of(car));
        Mockito.when(rentalRepository.existsByCarId(1)).thenReturn(false);
        Mockito.when(rentalRepository.save(Mockito.any())).thenReturn(rental);

        mockMvc.perform(post(String.format("/cars/%d/rent", 1))
                        .header("Authorization", "Bearer test")
                        .content(new ObjectMapper().writeValueAsString(rentDTO))
                        .contentType("application/json")
                )
                .andExpect(status().isOk());
    }

    /**
     * T26
     */
    @Test
    public void testRentCarInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"rent:car"})
                .build();

        User user = User.builder()
                .id(1)
                .email("admin@admin.com")
                .role(Role.builder().name("admin").id(1).build())
                .build();

        Car car = Car.builder()
                .id(1)
                .brand("BMW")
                .model("X5")
                .pricePerHour(10.)
                .build();

        RentDTO rentDTO = RentDTO.builder()
                .start(1)
                .end(2)
                .build();

        Rental rental = rentDTO.intoEntity();
        rental.setId(1);
        rental.setCar(car);
        rental.setUser(user);

        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(carRepository.existsById(1)).thenReturn(false);
        Mockito.when(userRepository.findByEmail(claims.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(carRepository.findById(1)).thenReturn(Optional.of(car));
        Mockito.when(rentalRepository.existsByCarId(1)).thenReturn(false);
        Mockito.when(rentalRepository.save(Mockito.any())).thenReturn(rental);

        mockMvc.perform(post(String.format("/cars/%d/rent", 1))
                        .header("Authorization", "Bearer test")
                        .content(new ObjectMapper().writeValueAsString(rentDTO))
                        .contentType("application/json")
                )
                .andExpect(status().isNotFound());
    }
}
