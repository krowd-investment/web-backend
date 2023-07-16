package com.swd392.funfundbe.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "PersonalWalletTransaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonalWalletTransaction {
    @Id
    @Column(name = "personal_wallet_transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personalWalletTransactionId;

    @ManyToOne
    @JoinColumn(name = "from_wallet_id", referencedColumnName = "wallet_id")
    private PersonalWallet fromWallet;

    @ManyToOne
    @JoinColumn(name = "to_wallet_id", referencedColumnName = "wallet_id")
    private PersonalWallet toWallet;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private UserTbl pWTransactionrcreatedBy;
    @Column(name = "created_at")
    private Date createdAt;
    private BigDecimal amount;
    private BigDecimal fee;
    @Column(name = "personal_wallet_description")
    private String personalWalletDescription;
}
