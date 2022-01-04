package com.min.core.service;

import com.min.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author Mindy
 * @since 2021-09-06
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {
    List<UserLoginRecord> listTop50(Long userId);
}
