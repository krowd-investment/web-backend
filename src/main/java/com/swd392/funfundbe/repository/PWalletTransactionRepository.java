package com.swd392.funfundbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swd392.funfundbe.model.entity.PersonalWalletTransaction;

public interface PWalletTransactionRepository extends JpaRepository<PersonalWalletTransaction, Integer> {

}
