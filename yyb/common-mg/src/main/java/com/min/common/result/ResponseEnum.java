package com.min.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ResponseEnum {
    SUCCESS(0, "success"),
    ERROR(-1, "error"),
    ;

    // 响应状态码
    private final Integer code;
    // 响应信息
    private final String message;
}
