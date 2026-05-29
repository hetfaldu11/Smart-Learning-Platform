package com.fm.smartlearningplatform.repository.user;


import com.fm.smartlearningplatform.model.user.UserDevice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDeviceRepository
        extends JpaRepository<
        UserDevice,
        Long
        > {

    Optional<UserDevice>
    findByDeviceIdAndActiveTrue(
            String deviceId
    );

    List<UserDevice>
    findByUserId(
            Long userId
    );
}
