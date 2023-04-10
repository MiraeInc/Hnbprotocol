package com.gxenSoft.mall.mypage.token.code;

import org.apache.commons.lang.StringUtils;

public enum StatusCode {
    REQUEST("요청"),
    SEND("전송중"),
    COMPLETE("완료"),
    CANCEL("취소");       // 관리자가 취소한 내역이고 사용자가 REQUEST에서 취소한 경우는 데이터를 삭제 처리 함

    private String code;
    private String label;

    StatusCode(String label) {
        this.code = name();
        this.label = label;
    }

    public static StatusCode fromCode(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalStateException("code값은 필수입니다.");
        }

        for (StatusCode statusCode : values()) {
            if (statusCode.name().equals(code.toUpperCase())) {
                return statusCode;
            }
        }

        throw new IllegalStateException("존재하지 않는 코드입니다.");
    }
    public String getCode() {
        return this.code;
    }

    public String getLabel() {
        return this.label;
    }

    public boolean isCancelEnableCode() {
        return StatusCode.REQUEST.getCode().equals(this.code);
    }
}
