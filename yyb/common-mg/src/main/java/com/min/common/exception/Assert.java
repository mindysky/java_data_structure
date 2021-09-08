package com.min.common.exception;

import com.min.common.result.ResponseEnum;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Assert {
    /**
     * 断言对象不为空
     * 如果对象obj为空，则抛出异常
     * @param obj 待判断对象
     */
    public static void notNull(Object obj, ResponseEnum responseEnum) {
        if (obj == null) {
            log.info("obj is null...............");
            throw new BusinessException(responseEnum);
        }
    }
}
