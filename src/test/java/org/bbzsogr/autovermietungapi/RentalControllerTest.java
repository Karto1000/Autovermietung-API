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
class RentalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTTokenDecoder tokenDecoder;

    /**
     * T40
     */
    @Test
    public void testCancelRentalValid() throws Exception {

    }

    /**
     * T41
     */
    @Test
    public void testCancelRentalInvalid() throws Exception {

    }

    /**
     * T42
     */
    @Test
    public void testViewRentalsValid() throws Exception {

    }


    /**
     * T43
     */
    @Test
    public void testViewRentalsInvalid() throws Exception {

    }
}
