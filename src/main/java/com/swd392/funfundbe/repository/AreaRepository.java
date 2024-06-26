package com.swd392.funfundbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swd392.funfundbe.model.entity.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    Area findByAreaId(int areaId);
}
