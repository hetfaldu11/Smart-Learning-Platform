package com.fm.smartlearningplatform.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSocialLinkId  implements Serializable {

    private Long  user;

    private  Platform platform;


}
