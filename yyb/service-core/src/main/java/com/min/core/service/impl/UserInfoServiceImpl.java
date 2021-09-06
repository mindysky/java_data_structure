package com.min.core.service.impl;

import com.min.core.pojo.entity.UserInfo;
import com.min.core.mapper.UserInfoMapper;
import com.min.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author Mindy
 * @since 2021-09-06
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
