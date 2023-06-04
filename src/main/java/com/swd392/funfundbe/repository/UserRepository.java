package com.swd392.funfundbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swd392.funfundbe.model.entity.UserTbl;

@Repository
public interface UserRepository extends JpaRepository<UserTbl, Integer> {

}
