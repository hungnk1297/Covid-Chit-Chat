package com.covid.chitchat.entity;

import lombok.*;

import javax.persistence.*;

@Table(name = "AS2_SCREEN_SHOT")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScreenShot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCREEN_SHOT_ID", unique = true, nullable = false)
    private Long screenShotID;

    @Column(name = "URL", unique = true, nullable = false)
    private String url;

    @Column(name = "SCREEN_SHOT_NAME")
    private String screenShotName;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Lob
    @Column(name = "BINARY_CONTENT", columnDefinition = "BLOB")
    private byte[] binaryContent;
}
