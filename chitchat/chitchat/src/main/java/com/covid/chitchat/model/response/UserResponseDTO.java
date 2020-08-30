package com.covid.chitchat.model.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long userID;

    private String username;

    private String role;

    private LocalDateTime createdOn;
}
