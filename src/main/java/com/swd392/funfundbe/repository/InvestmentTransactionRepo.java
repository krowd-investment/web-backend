package com.swd392.funfundbe.repository;

import com.swd392.funfundbe.model.entity.InvestmentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InvestmentTransactionRepo extends JpaRepository<InvestmentTransaction, Integer> {

}
