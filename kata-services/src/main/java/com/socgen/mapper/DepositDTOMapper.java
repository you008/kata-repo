package com.socgen.mapper;

import com.socgen.dto.DepositDTO;
import com.socgen.model.Deposit;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepositDTOMapper {

    @Autowired
    ModelMapper modelMapper;
    public List<DepositDTO> depositsToDepositDTOs(List<Deposit> deposits) {
        if(deposits != null) {
            List<DepositDTO> depositDTOS = new ArrayList<>();
            deposits.forEach(deposit -> {
                DepositDTO depositDTO = new DepositDTO();
                modelMapper.map(deposit, depositDTO);
                depositDTOS.add(depositDTO);
            });
            return depositDTOS;
        }
        return null;
    }

    public DepositDTO depositToDepositDTO(Deposit deposit) {
        DepositDTO depositDTO = new DepositDTO();
        modelMapper.map(deposit, depositDTO);
        return depositDTO;
    }

    public Deposit depositDTOTODeposit(DepositDTO depositDTO) {
        Deposit deposit = new Deposit();
        modelMapper.map(depositDTO, deposit);
        return deposit;
    }
}
