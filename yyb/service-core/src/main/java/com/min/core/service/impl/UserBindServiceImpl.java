package com.min.core.service.impl;

import com.min.core.pojo.entity.UserBind;
import com.min.core.mapper.UserBindMapper;
import com.min.core.service.UserBindService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author Mindy
 * @since 2021-09-06
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService {

}
