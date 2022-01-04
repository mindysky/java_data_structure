package com.min.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.min.core.pojo.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.min.core.pojo.query.UserInfoQuery;
import com.min.core.pojo.vo.LoginVO;
import com.min.core.pojo.vo.RegisterVO;
import com.min.core.pojo.vo.UserIndexVO;
import com.min.core.pojo.vo.UserInfoVO;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author Mindy
 * @since 2021-09-06
 */
public interface UserInfoService extends IService<UserInfo> {
    void register(RegisterVO registerVO);
    UserInfoVO login(LoginVO loginVO, String ip);


    IPage<UserInfo> listPage(Page<UserInfo> pageParam, UserInfoQuery userInfoQuery);

    void lock(Long id, Integer status);

    boolean checkMobile(String mobile);

    UserIndexVO getIndexUserInfo(Long userId);

    String getMobileByBindCode(String bindCode);
}
