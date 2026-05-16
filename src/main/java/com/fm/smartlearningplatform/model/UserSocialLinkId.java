package com.fm.smartlearningplatform.model;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSocialLinkId  implements Serializable {

    private Long  user;

    private  Platform platform;


}
