package com.swd392.funfundbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swd392.funfundbe.model.entity.PersonalWalletTransaction;

import java.util.List;

public interface PWalletTransactionRepository extends JpaRepository<PersonalWalletTransaction, Integer> {
    List<PersonalWalletTransaction> findByFromWallet_PersonalwalletOf_UserId(int userId);
}
