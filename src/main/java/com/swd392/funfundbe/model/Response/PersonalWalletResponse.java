package com.swd392.funfundbe.model.Response;

import com.swd392.funfundbe.model.entity.PersonalWallet;
import com.swd392.funfundbe.model.entity.UserTbl;
import com.swd392.funfundbe.model.entity.WalletType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class PersonalWalletResponse {
    private UUID walletId;
    private int userId;
    private String walletTypeId;
    private BigDecimal balance;
    private Date createdAt;

    public PersonalWalletResponse(PersonalWallet personalWallet) {
        this.walletId = personalWallet.getWalletId();
        this.userId = personalWallet.getPersonalwalletOf().getUserId();
        this.walletTypeId = personalWallet.getWalletType().getWalletTypeId();
        this.balance = personalWallet.getBalance();
        this.createdAt = personalWallet.getCreatedAt();
    }
}
