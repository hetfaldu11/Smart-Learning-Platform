package com.fm.smartlearningplatform.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizationId implements Serializable {

    private Long user;

    private UserRole userRole;
}