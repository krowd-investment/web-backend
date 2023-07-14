package com.swd392.funfundbe.repository;

import com.swd392.funfundbe.model.entity.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Integer> {
    Optional<DepositTransaction> findByOrderIdAndVerifiedFalse(String orderId);
}
