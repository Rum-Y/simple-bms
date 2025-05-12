package com.ruoyi.business.service;


import com.ruoyi.business.domain.request.LoginRequest;
import com.ruoyi.business.domain.request.RegisterRequest;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * @author eddie
 * @version 1.0
 * @date 2025/3/17 17:57
 */

public interface IEbLoginService {

    String login(LoginRequest loginRequest);

}
