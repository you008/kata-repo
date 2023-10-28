package com.socgen.repository;

import com.socgen.model.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class DepositRepositoryImpl {

    @Autowired
    DepositRepository depositRepository;

    @Transactional(readOnly = true)
    public List<Deposit> getDepositInRange(Date begin, Date end) {
        return depositRepository.findDepositByDateGreaterThanEqualOrDateLessThanEqual(begin, end);
    }

    @Transactional
    public Deposit save(Deposit deposit) {
       return depositRepository.save(deposit);
    }

}
