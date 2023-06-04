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
@Table(name = "Area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Area {
    @Id
    @Column(name = "area_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int areaId;
    private String city;
    private String district;
    private String details;
    private boolean status;
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private List<Project> projectApprovedBy;

}
