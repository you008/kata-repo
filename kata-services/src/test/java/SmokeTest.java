import com.fasterxml.jackson.databind.ObjectMapper;
import com.socgen.controller.DepositController;
import com.socgen.dto.DepositDTO;
import com.socgen.service.DepositService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DepositController.class, ObjectMapper.class})
@EnableWebMvc
@AutoConfigureMockMvc
public class SmokeTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DepositService depositService;

    @Autowired
    private DepositController depositController;

    @Test
    public void contextLoads() {
        assertThat(depositController).isNotNull();
    }

    @Test
    void registrationWorksThroughAllLayers() throws Exception {
        Date dateDay = new Date();
        Long id = 1L;
        Long amount = 1000L;
        String description = "paiement par cart";

        DepositDTO depositDTO = DepositDTO.builder()
                .id(id)
                .amount(amount)
                .description(description)
                .date(dateDay)
                .build();
        String json = objectMapper.writeValueAsString(depositDTO);
        mockMvc.perform(post("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }
}
