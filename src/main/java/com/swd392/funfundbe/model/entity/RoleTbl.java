package com.swd392.funfundbe.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Roletbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleTbl {
    @Id
    @Column(name ="role_id")
    private String roleId;
    @Column(name ="role_name")
    private String roleName;
    @Column(name ="description")
    private String description;
}
