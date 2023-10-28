package com.socgen.service;

import com.socgen.dto.DepositDTO;
import com.socgen.mapper.DepositDTOMapper;
import com.socgen.repository.DepositRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DepositService {

    SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");

    @Autowired
    DepositRepositoryImpl depositRepositoryImpl;

    @Autowired
    DepositDTOMapper depositDTOMapper;

    public DepositDTO saveDeposit(DepositDTO depositDTO) {
        var deposit = depositDTOMapper.depositDTOTODeposit(depositDTO);
        var savedDeposit = depositRepositoryImpl.save(deposit);
        return  depositDTOMapper.depositToDepositDTO(savedDeposit);
    }

    public List<DepositDTO> findDepositByDateBetween(String begin, String end) {
        try {
            Date beginDate = formatter.parse(begin);
            Date endDate = formatter.parse(end);
            var deposits = depositRepositoryImpl.getDepositInRange(beginDate, endDate);
            List<DepositDTO> depositDTOS = depositDTOMapper.depositsToDepositDTOs(deposits);;
            populateBalance(depositDTOS);
            return  depositDTOS;
        } catch (ParseException e) {
            //TODO add logger
            return null;
        }
    }

    public void populateBalance(List<DepositDTO> depositDTOS) {
        if(depositDTOS.stream().findFirst().isPresent()) {
            DepositDTO previousDTO = depositDTOS.get(0);
            previousDTO.setBalance(previousDTO.getAmount());
            for(DepositDTO depositDTO: depositDTOS) {
                if(depositDTO != previousDTO) {
                    depositDTO.setBalance(previousDTO.getBalance() + depositDTO.getAmount());
                    previousDTO = depositDTO;
                }
            }
        }
    }
}
