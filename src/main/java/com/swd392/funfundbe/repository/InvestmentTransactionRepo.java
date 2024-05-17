package com.swd392.funfundbe.repository;

import com.swd392.funfundbe.model.entity.InvestmentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InvestmentTransactionRepo extends JpaRepository<InvestmentTransaction, Integer> {
    List<InvestmentTransaction> findByPersonalWallet_PersonalwalletOf_UserId(int userId);
}
