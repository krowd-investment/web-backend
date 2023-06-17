package com.swd392.funfundbe.service;

import com.swd392.funfundbe.exception.BadRequestException;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.enums.LoginStatus;
import com.swd392.funfundbe.model.enums.Role;
import com.swd392.funfundbe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public void approvePO(Integer poId) {
        String poRole = Role.PO.toString();
        UserTbl po = userRepository.findByUserIdAndRole_RoleId(poId, poRole)
                .orElseThrow(() -> new BadRequestException("PO ID not found"));
        if (po.getStatus().equals(LoginStatus.FILLING_REQUIRED))
            throw new BadRequestException("PO needs to register information before approval");
        if (po.getStatus().equals(LoginStatus.DELETED))
            throw new BadRequestException("PO has been banned");

        po.setStatus(LoginStatus.APPROVED);
        po.setEnabled(true);
    }

    public void rejectPO(Integer poId) {
        String poRole = Role.PO.toString();
        UserTbl po = userRepository.findByUserIdAndRole_RoleId(poId, poRole)
                .orElseThrow(() -> new BadRequestException("PO ID not found"));
        if (po.getStatus().equals(LoginStatus.FILLING_REQUIRED))
            throw new BadRequestException("PO needs to register information before rejection");
        if (po.getStatus().equals(LoginStatus.DELETED))
            throw new BadRequestException("PO has been banned");

        po.setStatus(LoginStatus.REJECTED);
        po.setEnabled(false);
    }
}
