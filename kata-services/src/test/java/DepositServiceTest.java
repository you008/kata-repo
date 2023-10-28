import com.socgen.dto.DepositDTO;
import com.socgen.mapper.DepositDTOMapper;
import com.socgen.model.Deposit;
import com.socgen.repository.DepositRepositoryImpl;
import com.socgen.service.DepositService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class DepositServiceTest {

    @Mock
    DepositRepositoryImpl depositRepositoryImpl;

    @Mock
    ModelMapper modelMapper;

    @Mock
    DepositDTOMapper depositDTOMapper;

    @InjectMocks
    DepositService depositService;

    @Test
    public void test_saveDeposit() {
        //given
        Date dateDay = new Date();
        java.sql.Date date = new java.sql.Date(dateDay.getTime());
        Long id = 1L;
        Long amount = 1000L;
        String description = "paiement par cart";
        DepositDTO depositDTOToSave = DepositDTO.builder()
                //whithout id
                .amount(amount)
                .description(description)
                .date(dateDay)
                .build();

        Deposit depositToSave = Deposit.builder()
                //whithout id
                .date(date)
                .amount(amount)
                .description(description)
                .build();

        Deposit depositToReturn = Deposit.builder()
                .id(id)
                .date(date)
                .amount(amount)
                .description(description)
                .build();

        DepositDTO depositDTO = DepositDTO.builder()
                .id(id)
                .amount(amount)
                .description(description)
                .date(dateDay)
                .build();


        when(depositDTOMapper.depositDTOTODeposit(depositDTOToSave)).thenReturn(depositToSave);
        when(depositRepositoryImpl.save(any())).thenReturn(depositToReturn);
        when(depositDTOMapper.depositToDepositDTO(depositToReturn)).thenReturn(depositDTO);

        //when
        DepositDTO depositDTOSaved = depositService.saveDeposit(depositDTOToSave);

        //then
        verify(depositDTOMapper, times(1)).depositDTOTODeposit(depositDTOToSave);
        verify(depositDTOMapper, times(1)).depositToDepositDTO(depositToReturn);

        assertEquals(id, depositDTOSaved.getId());
        assertEquals(amount, depositDTOSaved.getAmount());
        assertEquals(description, depositDTOSaved.getDescription());
        assertEquals(dateDay, depositDTOSaved.getDate());
    }

    @Test
    public void test_findDepositByDateBetween() {
        //Given
        var deposits = new ArrayList<Deposit>();

        var stringStartDate = "01022022";
        var stringEndDate = "01012023";

        var recordDate = Calendar.getInstance();
        recordDate.set(2023, Calendar.JANUARY, 1, 0, 0);

        var date = new java.sql.Date(recordDate.getTime().getTime());
        Long id = 1L;
        Long amount = 1000L;
        String description = "paiement par cart";

        var deposit = Deposit.builder()
                .id(id)
                .date(date)
                .amount(amount)
                .description(description)
                .build();
        deposits.add(deposit);

        var depositdto = DepositDTO.builder()
                .id(id)
                .date(recordDate.getTime())
                .amount(amount)
                .description(description)
                .balance(amount)
                .build();
        var depositDTOSToReturn = new ArrayList<DepositDTO>();
        depositDTOSToReturn.add(depositdto);

        when(depositRepositoryImpl.getDepositInRange(any(), any())).thenReturn(deposits);
        when(depositDTOMapper.depositsToDepositDTOs(deposits)).thenReturn(depositDTOSToReturn);

        //when
        var depositDTOS = depositService.findDepositByDateBetween(stringStartDate, stringEndDate);

        //then
        assertEquals(1, depositDTOS.size());
        verify(depositRepositoryImpl, times(1)).getDepositInRange(any(), any());
        verify(depositDTOMapper, times(1)).depositsToDepositDTOs(deposits);

        var depositDTO = depositDTOS.get(0);

        assertEquals(id, depositDTO.getId());
        assertEquals(amount, depositDTO.getAmount());
        assertEquals(description, depositDTO.getDescription());
        assertEquals(date, depositDTO.getDate());
        assertEquals(amount, depositDTO.getAmount());
    }

    @Test
    public void test_populateBalance() {
        //Given
        var startDate = Calendar.getInstance();
        ;
        Long id = 1L;
        Long amount = 1000L;
        String description = "paiement par cart";
        var depositdto1 = DepositDTO.builder()
                .id(id)
                .date(startDate.getTime())
                .amount(amount)
                .description(description)
                .build();
        var date2 = Calendar.getInstance();
        date2.set(Calendar.DAY_OF_MONTH, startDate.get(Calendar.DAY_OF_MONTH) - 1);
        var depositdto2 = DepositDTO.builder()
                .id(id)
                .date(date2.getTime())
                .amount(amount)
                .description(description)
                .build();
        var date3 = Calendar.getInstance();
        date3.set(Calendar.DAY_OF_MONTH, startDate.get(Calendar.DAY_OF_MONTH) - 5);
        var depositdto3 = DepositDTO.builder()
                .id(id)
                .date(date3.getTime())
                .amount(amount)
                .description(description)
                .build();

        List<DepositDTO> depositDtos = new ArrayList<DepositDTO>();
        depositDtos.add(depositdto1);
        depositDtos.add(depositdto2);
        depositDtos.add(depositdto3);

        //when
        depositService.populateBalance(depositDtos);

        //then

        assertEquals(depositdto1.getBalance(), depositdto1.getAmount());
        assertEquals(depositdto2.getBalance(), depositdto1.getBalance() + depositdto2.getAmount());
        assertEquals(depositdto3.getBalance(), depositdto2.getBalance() + depositdto3.getAmount());
    }
}
