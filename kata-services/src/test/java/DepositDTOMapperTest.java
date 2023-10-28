import com.fasterxml.jackson.databind.ObjectMapper;
import com.socgen.dto.DepositDTO;
import com.socgen.mapper.DepositDTOMapper;
import com.socgen.model.Deposit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DepositDTOMapper.class, ModelMapper.class, ObjectMapper.class})
public class DepositDTOMapperTest {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    DepositDTOMapper depositService;

    @Test
    public void test_mapDepositsToDepositDTOs() {
        //Given
        List<Deposit> deposits = new ArrayList<>();
        Date dateDay = new Date();
        java.sql.Date date = new java.sql.Date(dateDay.getTime());
        Long id = 1L;
        Long amount = 1000L;
        String description = "paiement par cart";

        Deposit deposit = Deposit.builder()
                .id(id)
                .date(date)
                .amount(amount)
                .description(description)
                .build();
        deposits.add(deposit);


        // when
        List<DepositDTO> depositDTOs = depositService.depositsToDepositDTOs(deposits);

        //then
        assertNotNull(depositDTOs);
        assertEquals(1, depositDTOs.size());
        DepositDTO result = depositDTOs.get(0);
        assertEquals(result.getId(), id);
        assertEquals(result.getDate(), date);
        assertEquals(result.getAmount(), amount);
        assertEquals(result.getDescription(), description);
    }

    @Test
    public void test_mapDepositToDepositDTO() {
        //Given
        Date dateDay = new Date();
        java.sql.Date date = new java.sql.Date(dateDay.getTime());
        Long id = 1L;
        Long amount = 1000L;
        String description = "paiement par cart";
        Deposit deposit = Deposit.builder()
                .id(id)
                .date(date)
                .amount(amount)
                .description(description)
                .build();

        //when
        DepositDTO depositDTO = depositService.depositToDepositDTO(deposit);

        //Then
        assertNotNull(depositDTO);
        assertEquals(id, depositDTO.getId());
        assertEquals(amount, depositDTO.getAmount());
        assertEquals(description, depositDTO.getDescription());
        assertEquals(date, deposit.getDate());
    }

    @Test
    public void test_mapDepositDTOTODeposit() {
        //Given
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

        //when
        Deposit deposit = depositService.depositDTOTODeposit(depositDTO);

        //then
        assertNotNull(deposit);
        assertEquals(id, deposit.getId());
        assertEquals(amount, deposit.getAmount());
        assertEquals(description, deposit.getDescription());
        assertEquals(dateDay, deposit.getDate());
    }
}
