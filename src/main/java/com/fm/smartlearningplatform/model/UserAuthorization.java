package com.fm.smartlearningplatform.model;

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
@IdClass(UserAuthorizationId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorization {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole userRole;
}