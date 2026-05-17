package com.fm.smartlearningplatform.model.user;
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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "user")
@Builder
public class UserSocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "user_id")
    private User user;

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
