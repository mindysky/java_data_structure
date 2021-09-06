package com.min.core.service.impl;

import com.min.core.pojo.entity.UserLoginRecord;
import com.min.core.mapper.UserLoginRecordMapper;
import com.min.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author Mindy
 * @since 2021-09-06
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
