package com.swd392.funfundbe.model.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {
    private int userId;
    private String title;
    private String body;
    private String url;
    private String image;
    private String type;
}
