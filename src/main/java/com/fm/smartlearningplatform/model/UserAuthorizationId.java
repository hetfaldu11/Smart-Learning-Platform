package com.fm.smartlearningplatform.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserAuthorizationId implements Serializable {

    private Long user;

    private UserRole userRole;
}