package com.fm.smartlearningplatform.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name= "user_social_links",
        uniqueConstraints = {
        @UniqueConstraint(name= "uk_user_platform",
                        columnNames = {"user_id", "platform"}
        )
        }
)
@IdClass(UserSocialLinkId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "user")
public class UserSocialLink {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name= "platform")
    private Platform platform;

    @Column(name= "url")
    private String url;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name= "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
