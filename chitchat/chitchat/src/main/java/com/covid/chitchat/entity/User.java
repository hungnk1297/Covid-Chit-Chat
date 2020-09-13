package com.covid.chitchat.entity;

import com.covid.chitchat.constant.RoleType;
import com.covid.chitchat.constant.converter.RoleTypeConverter;
import lombok.*;

import javax.persistence.*;

@Table(name = "AS2_USER")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long userID;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    @Convert(converter = RoleTypeConverter.class)
    private RoleType role = RoleType.USER;

}
