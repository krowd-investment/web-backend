package com.swd392.funfundbe.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<UserTbl> users;
}
