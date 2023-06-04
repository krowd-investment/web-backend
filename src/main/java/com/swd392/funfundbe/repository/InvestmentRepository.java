package com.swd392.funfundbe.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swd392.funfundbe.model.entity.Investment;

public interface InvestmentRepository extends JpaRepository<Investment, UUID> {

}
