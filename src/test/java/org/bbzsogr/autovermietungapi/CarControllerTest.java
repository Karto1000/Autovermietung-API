package org.bbzsogr.autovermietungapi;

import org.bbzsogr.autovermietungapi.authentication.JWTTokenDecoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTTokenDecoder tokenDecoder;

    /**
     * T17
     */
    @Test
    public void testCreateRentableCarValid() throws Exception {

    }

    /**
     * T18
     */
    @Test
    public void testCreateRentableCarInvalid() throws Exception {

    }

    /**
     * T19
     */
    @Test
    public void testDeleteCarValid() throws Exception {

    }


    /**
     * T20
     */
    @Test
    public void testDeleteCarInvalid() throws Exception {

    }

    /**
     * T21
     */
    @Test
    public void testEditCarValid() throws Exception {

    }

    /**
     * T22
     */
    @Test
    public void testEditCarInvalid() throws Exception {

    }

    /**
     * T23
     */
    @Test
    public void testSearchForCarValid() throws Exception {

    }

    /**
     * T24
     */
    @Test
    public void testSearchForCarInvalid() throws Exception {

    }

    /**
     * T25
     */
    @Test
    public void testRentCarValid() throws Exception {

    }

    /**
     * T26
     */
    @Test
    public void testRentCarInvalid() throws Exception {

    }
}
