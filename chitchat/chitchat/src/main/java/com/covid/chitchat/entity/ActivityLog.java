package com.covid.chitchat.entity;

import com.covid.chitchat.constant.LogType;
import com.covid.chitchat.constant.converter.LogTypeConverter;
import lombok.*;

import javax.persistence.*;

@Table(name = "AS2_ACTIVITY_LOG")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_LOG_ID", unique = true, nullable = false)
    private Long activityLogID;

    @Convert(converter = LogTypeConverter.class)
    @Column(name = "LOG_TYPE", unique = true, nullable = false)
    private LogType logType;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;
}
