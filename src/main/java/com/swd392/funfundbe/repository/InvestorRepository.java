package com.swd392.funfundbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swd392.funfundbe.model.entity.Investor;

@Repository
public interface InvestorRepository extends JpaRepository<Investor, Integer> {

}
