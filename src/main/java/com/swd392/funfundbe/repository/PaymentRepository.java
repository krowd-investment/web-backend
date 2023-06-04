package com.swd392.funfundbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swd392.funfundbe.model.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
