package com.swd392.funfundbe.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Risk")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Risk {
    @Id
    @Column(name = "risk_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int riskId;

    @ManyToOne
    @JoinColumn(name = "risk_type_id", referencedColumnName = "risk_type_id")
    private RiskType riskType;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;

    private String name;
    private String description;
}
