package org.bbzsogr.autovermietungapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bbzsogr.autovermietungapi.authentication.Claims;
import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.bbzsogr.autovermietungapi.model.Firm;
import org.bbzsogr.autovermietungapi.model.Role;
import org.bbzsogr.autovermietungapi.model.User;
import org.bbzsogr.autovermietungapi.repository.FirmRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class FirmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTTokenDecoder tokenDecoder;
    @Autowired
    @MockBean
    private UserRepository userRepository;
    @Autowired
    @MockBean
    private FirmRepository firmRepository;

    /**
     * T36
     */
    @Test
    public void testViewFirmsValid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"view:firm"})
                .build();

        Firm firm = Firm.builder().id(1).name("Test").build();

        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(firmRepository.search("Test")).thenReturn(Collections.singletonList(firm));

        MvcResult result = mockMvc.perform(get("/firms?name=Test")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andReturn();

        Firm[] firms = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Firm[].class);

        assert firms.length == 1;
        assert firms[0].equals(firm);
    }

    /**
     * T37
     */
    @Test
    public void testViewFirmsInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"view:firm"})
                .build();

        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(firmRepository.search("Awd")).thenReturn(Collections.emptyList());

        MvcResult result = mockMvc.perform(get("/firms?name=Awd")
                        .header("Authorization", "Bearer test"))
                .andExpect(status().isOk())
                .andReturn();

        Firm[] firms = new ObjectMapper().readValue(result.getResponse().getContentAsString(), Firm[].class);

        assert firms.length == 0;
    }

    /**
     * T38
     */
    @Test
    public void testDeleteFirmsValid() throws Exception {
        Claims claims = Claims.builder()
                .email("admin@admin.com")
                .permissions(new String[]{"delete:firm"})
                .build();

        User user = User.builder()
                .id(1)
                .email("admin@admin.com")
                .role(Role.builder().id(1).name("admin").build())
                .build();

        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(userRepository.findByEmail("admin@admin.com")).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).deleteById(1);

        mockMvc.perform(delete("/firms/1").header("Authorization", "Bearer test"))
                .andExpect(status().isNoContent());
    }


    /**
     * T39
     */
    @Test
    public void testDeleteFirmsInvalid() throws Exception {
        Claims claims = Claims.builder()
                .email("firm@firm.com")
                .permissions(new String[]{"delete:firm"})
                .build();

        User user = User.builder()
                .id(1)
                .email("firm@firm.com")
                .role(Role.builder().id(1).name("firm").build())
                .build();

        Mockito.when(tokenDecoder.decode("Bearer test")).thenReturn(Optional.of(claims));
        Mockito.when(userRepository.findByEmail("firm@firm.com")).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).deleteById(2);

        mockMvc.perform(delete("/firms/2").header("Authorization", "Bearer test"))
                .andExpect(status().isForbidden());
    }
}
