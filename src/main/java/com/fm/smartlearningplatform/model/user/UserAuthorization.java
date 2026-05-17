package com.fm.smartlearningplatform.model.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "user_authorization",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_role",
                        columnNames = {"user_id", "role"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthorization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;
}