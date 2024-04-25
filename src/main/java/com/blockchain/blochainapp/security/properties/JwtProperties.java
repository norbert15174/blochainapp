package com.blockchain.blochainapp.security.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String key;
    private Integer accessTokenExpirationTime;
    private Integer refreshTokenExpirationTime;
    private String audience;
    private String issuer;

}
