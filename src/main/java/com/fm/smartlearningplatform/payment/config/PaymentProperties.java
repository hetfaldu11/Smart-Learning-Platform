package com.fm.smartlearningplatform.payment.config;

import com.cloudinary.Url;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {
    
    private String currency;

    private String successUrl;

    private String failureUrl;
}