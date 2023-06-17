package com.swd392.funfundbe.model.Response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private int projectId;
    private String projectName;
    private String description;
    private String areaName;
    private String fieldName;
    private String image;
    private Date createAt;
}
