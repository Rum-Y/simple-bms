package com.ruoyi.business.service.impl;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.business.domain.EbUser;
import com.ruoyi.business.domain.LoginAppUser;
import com.ruoyi.business.domain.request.LoginRequest;
import com.ruoyi.business.domain.request.RegisterRequest;
import com.ruoyi.business.service.IEbLoginService;
import com.ruoyi.business.service.IEbUserService;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author eddie
 * @version 1.0
 * @date 2025/3/17 17:52
 */
@Service
public class EbLoginService implements IEbLoginService {
    @Autowired
    private IEbUserService ebUserService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private AppTokenService appTokenService;


    @Override
    public String login(LoginRequest loginRequest) {

        EbUser ebUser=new EbUser();
        ebUser.setPhone(loginRequest.getPhone());
        List<EbUser> users=ebUserService.selectEbUserList(ebUser);

        if(users.isEmpty()){
            throw new ServiceException("此账号未注册");
        }
        EbUser ebUser1=users.get(0);
        if (!ebUser1.getStatus()) {
            throw new ServiceException("此账号被禁用");
        }

        //校验验证码

        //校验密码

//        if (!SecurityUtils.matchesPassword(loginRequest.getPassword(), ebUser1.getPwd())) {
//            throw new ServiceException("密码错误");
//        }

        // 记录最后一次登录时间
        ebUser1.setLastLoginTime(DateUtils.getNowDate());

        LoginAppUser loginAppUser=new LoginAppUser();
        loginAppUser.setAppUser(ebUser1);
        return appTokenService.createAppToken(loginAppUser);
    }



}
