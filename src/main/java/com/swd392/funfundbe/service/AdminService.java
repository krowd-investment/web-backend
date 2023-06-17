package com.swd392.funfundbe.service;

import com.swd392.funfundbe.controller.api.exception.custom.CustomBadRequestException;
import com.swd392.funfundbe.controller.api.exception.custom.CustomNotFoundException;
import com.swd392.funfundbe.model.CustomError;
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

    private UserTbl checkPOForConfirmation(Integer poId) throws CustomNotFoundException, CustomBadRequestException {
        String poRole = Role.PO.toString();
        UserTbl po = userRepository.findByUserIdAndRole_RoleId(poId, poRole)
                .orElseThrow(() -> new CustomNotFoundException(
                        CustomError.builder().code("404").message("PO ID not found").build()
                ));
        if (po.getStatus().equals(LoginStatus.FILLING_REQUIRED))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400")
                            .message("PO needs to register information before approval").build()
            );
        if (po.getStatus().equals(LoginStatus.DELETED))
            throw new CustomBadRequestException(
                    CustomError.builder()
                            .code("400")
                            .message("PO has been banned").build()
            );
        return po;
    }
    public void approvePO(Integer poId) throws CustomNotFoundException, CustomBadRequestException {
        UserTbl po = checkPOForConfirmation(poId);
        po.setStatus(LoginStatus.APPROVED);
        po.setEnabled(true);
    }

    public void rejectPO(Integer poId) throws CustomNotFoundException, CustomBadRequestException {
        UserTbl po = checkPOForConfirmation(poId);
        po.setStatus(LoginStatus.REJECTED);
        po.setEnabled(false);
    }
}
