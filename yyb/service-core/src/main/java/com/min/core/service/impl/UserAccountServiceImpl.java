package com.min.core.service.impl;

import com.min.core.pojo.entity.UserAccount;
import com.min.core.mapper.UserAccountMapper;
import com.min.core.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author Mindy
 * @since 2021-09-06
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
