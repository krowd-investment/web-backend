package com.swd392.funfundbe.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swd392.funfundbe.model.entity.Project;
import com.swd392.funfundbe.model.entity.ProjectWallet;

public interface ProjectWalletRepository extends JpaRepository<ProjectWallet, UUID> {
    ProjectWallet findByProject(Project project);
    Optional<ProjectWallet> findByProject_ProjectIdAndWalletType_WalletTypeId(int projectId, String walletTypeId);
}
