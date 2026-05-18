package com.fm.smartlearningplatform.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name= "user_preferences")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "user")
@Builder
public class UserPreference {

    @Id
    @Column(name = "user_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "languages_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    @Column(name= "notification_enabled")
    @Builder.Default
    private boolean notificationEnabled = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
