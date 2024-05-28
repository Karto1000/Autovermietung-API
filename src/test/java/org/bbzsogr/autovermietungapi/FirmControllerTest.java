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
class FirmControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JWTTokenDecoder tokenDecoder;

    /**
     * T36
     */
    @Test
    public void testViewFirmsValid() throws Exception {

    }

    /**
     * T37
     */
    @Test
    public void testViewFirmsInvalid() throws Exception {

    }

    /**
     * T38
     */
    @Test
    public void testDeleteFirmsValid() throws Exception {

    }


    /**
     * T39
     */
    @Test
    public void testDeleteFirmsInvalid() throws Exception {

    }
}
