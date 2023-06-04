package com.swd392.funfundbe.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RiskType")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskType {
    @Id
    @Column(name = "risk_type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int riskTypeId;
    private String name;
    @Column(name = "risk_type_description")
    private String riskTypeDescription;
    private boolean status;
    @OneToMany(mappedBy = "riskType", cascade = CascadeType.ALL)
    private List<Risk> riskList;
}
