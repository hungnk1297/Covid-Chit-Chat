package com.covid.chitchat.model.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLogResponseDTO {

    private String username;

    private String logType;

    private String logTime;
}
