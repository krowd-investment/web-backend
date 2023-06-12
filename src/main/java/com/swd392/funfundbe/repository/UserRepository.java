package com.swd392.funfundbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swd392.funfundbe.model.entity.UserTbl;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserTbl, Integer> {
    Optional<UserTbl> findByEmail(String email);
}
