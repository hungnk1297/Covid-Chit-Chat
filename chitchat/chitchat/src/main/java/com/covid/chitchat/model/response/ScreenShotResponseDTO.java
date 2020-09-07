package com.covid.chitchat.model.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenShotResponseDTO {

    private Long screenShotID;

    private String username;

    private String screenShotName;

    private String tempURL;
}
