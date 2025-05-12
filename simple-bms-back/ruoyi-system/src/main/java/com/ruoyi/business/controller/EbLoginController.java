package com.ruoyi.business.controller;

import com.ruoyi.business.domain.EbUser;
import com.ruoyi.business.domain.LoginAppUser;
import com.ruoyi.business.service.IEbLoginService;
import com.ruoyi.business.service.IEbUserService;
import com.ruoyi.business.service.impl.AppTokenService;
import com.ruoyi.common.core.domain.AjaxResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author eddie
 * @version 1.0
 * @date 2025/3/17 17:51
 */
@RestController
@RequestMapping("/app/user")
public class EbLoginController {

    @Autowired
    private IEbLoginService loginService;
    @Autowired
    private AppTokenService appTokenService;
    @Autowired
    private IEbUserService ebUserService;

    //用户登录测试使用
    @PostMapping("/login")
    public AjaxResult login()
    {
        List<EbUser> users=ebUserService.selectEbUserList(new EbUser());
        LoginAppUser loginAppUser=new LoginAppUser();
        loginAppUser.setAppUser(users.get(0));
        //记录登录日志
        // 生成token
        String token = appTokenService.createAppToken(loginAppUser);
        return AjaxResult.success(token);
    }




}
