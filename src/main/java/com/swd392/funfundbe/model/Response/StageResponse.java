package com.swd392.funfundbe.model.Response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StageResponse {
    private int stageId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean isOverDue;
    private int projectId;
    private String stageStatus;
}
