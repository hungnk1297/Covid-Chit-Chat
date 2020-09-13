package com.covid.chitchat.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRequestDTO {

    private String username;
    private String chatContent;
}
