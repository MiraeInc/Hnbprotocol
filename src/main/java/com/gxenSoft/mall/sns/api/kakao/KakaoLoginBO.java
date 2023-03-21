package com.gxenSoft.mall.sns.api.kakao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.gxenSoft.message.SpringMessage;
import com.gxenSoft.method.PathUtil;
import com.gxenSoft.snsApi.KakaoLoginApi;
import org.apache.http.client.ClientProtocolException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class KakaoLoginBO {
    private static final String CLIENT_ID = SpringMessage.getMessage("kakao.clientId");
    private static final String REDIRECT_URL = SpringMessage.getMessage("kakao.redirectUrl");
    private static final String PROFILE_API_URL = SpringMessage.getMessage("kakao.profileApiUrl");

    private static final String KAKAO_CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    public static String getAuthorizationUrl(HttpSession session) throws Exception {
        String snsMode = (String)session.getAttribute("snsMode");
        String path = PathUtil.getDevice();
        String snsAuth = (session.getAttribute("SS_MY_SNS_AUTH") == null ? "" : String.valueOf(session.getAttribute("SS_MY_SNS_AUTH")));
        String snsReferer = (session.getAttribute("SS_SNS_REFERER") == null ? "" : String.valueOf(session.getAttribute("SS_SNS_REFERER")));
        String state = snsMode + "@" + path + "@" + snsAuth + "@" + snsReferer;

        OAuth20Service oauthService = (new ServiceBuilder()).apiKey(CLIENT_ID).callback(REDIRECT_URL).state(state).build(KakaoLoginApi.instance());

        return oauthService.getAuthorizationUrl();
    }

    public static OAuthToken getOAuthToken(String code) {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = getTokenRequestHeaders();
        MultiValueMap<String, String> body = getBody(code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = template.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        String json = responseEntity.getBody();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (UnsupportedEncodingException var6) {
            throw new IllegalArgumentException(var6.getMessage());
        } catch (ClientProtocolException var7) {
            throw new IllegalArgumentException(var7.getMessage());
        } catch (IOException var8) {
            throw new IllegalArgumentException(var8.getMessage());
        }
    }

    /**
     * 카카오 사용자 프로필 가져오기
     * Todo : 리턴값 현재는 Map으로 해놨는데, 나중에 정의되면 모델로 변경해야 함
     */
    public static Map getUserProfile(String accessToken) {
        RestTemplate template = new RestTemplate();

        HttpHeaders headers = getProfileRequestHeaders(accessToken);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = template.exchange(PROFILE_API_URL,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class);

        String json = responseEntity.getBody();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, Map.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (UnsupportedEncodingException var6) {
            throw new IllegalArgumentException(var6.getMessage());
        } catch (ClientProtocolException var7) {
            throw new IllegalArgumentException(var7.getMessage());
        } catch (IOException var8) {
            throw new IllegalArgumentException(var8.getMessage());
        }
    }

    private static MultiValueMap<String, String> getBody(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URL);
        params.add("code", code);

        return params;
    }

    private static HttpHeaders getTokenRequestHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", KAKAO_CONTENT_TYPE);
        return httpHeaders;
    }

    private static HttpHeaders getProfileRequestHeaders(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", KAKAO_CONTENT_TYPE);
        httpHeaders.add("Authorization", String.format("Bearer %s", accessToken));
        return httpHeaders;
    }

}

