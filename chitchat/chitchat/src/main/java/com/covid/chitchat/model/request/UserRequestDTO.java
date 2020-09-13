package com.covid.chitchat.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO implements Serializable {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String roleType;
}
