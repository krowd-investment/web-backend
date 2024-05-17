package com.swd392.funfundbe.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ProjectOwner")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOwner {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserTbl projectOwner;
}
