package com.gxenSoft.config;

import org.apache.commons.lang.StringUtils;

public enum ProfilesActiveCode {
    LOCAL("로컬"),
    LIVE("운영")
    ;

    private String code;
    private String label;

    ProfilesActiveCode(String label) {
        this.code = name();
        this.label = label;
    }

    public String code() {
        return this.code;
    }

    public String label() {
        return this.label;
    }

    public static ProfilesActiveCode fromCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalStateException("code값은 필수입니다.");
        }

        for (ProfilesActiveCode profilesActiveCode : values()) {
            if (profilesActiveCode.name().equals(code.toUpperCase())) {
                return profilesActiveCode;
            }
        }

        throw new IllegalStateException("존재하지 않는 코드입니다.");
    }
}
