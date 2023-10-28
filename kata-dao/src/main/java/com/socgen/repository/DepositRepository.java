package com.socgen.repository;

import com.socgen.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    List<Deposit> findDepositByDateGreaterThanEqualOrDateLessThanEqual(Date start, Date end);

}
