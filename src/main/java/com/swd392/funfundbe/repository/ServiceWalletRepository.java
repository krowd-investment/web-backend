package com.swd392.funfundbe.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swd392.funfundbe.model.entity.ServiceWallet;

@Repository
public interface ServiceWalletRepository extends JpaRepository<ServiceWallet, UUID> {

}
