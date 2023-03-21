package com.gxenSoft.mall.sns.api.kakao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@ToString
@Getter
public class OAuthToken implements Serializable {
    private static final long serialVersionUID = -78432876483724L;

    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String idToken;
    private Integer expiresIn;
    private String scope;
    private Integer refreshTokenExpiresIn;

    @JsonCreator
    public OAuthToken(@JsonProperty("access_token") String accessToken,
                      @JsonProperty("token_type") String tokenType,
                      @JsonProperty("refresh_token") String refreshToken,
                      @JsonProperty("id_token") String idToken,
                      @JsonProperty("expires_in") Integer expiresIn,
                      @JsonProperty("scope") String scope,
                      @JsonProperty("refresh_token_expires_in") Integer refreshTokenExpiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.idToken = idToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }
}
