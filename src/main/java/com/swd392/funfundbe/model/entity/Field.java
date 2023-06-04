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
@Table(name = "Field")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Field {
    @Id
    @Column(name="field_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fieldId;
    private String name;
    @Column(name="field_description")
    private String fieldDescription;
    private boolean status;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL)
    private List<Project> projectApprovedBy;
}
