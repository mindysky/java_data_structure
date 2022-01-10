package com.min.core.service;

import com.min.core.pojo.entity.UserBind;
import com.baomidou.mybatisplus.extension.service.IService;
import com.min.core.pojo.vo.UserBindVO;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author Mindy
 * @since 2021-09-06
 */
public interface UserBindService extends IService<UserBind> {
    String commitBindUser(UserBindVO userBindVO, Long userId);
}
