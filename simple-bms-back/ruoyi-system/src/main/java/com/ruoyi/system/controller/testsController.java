package com.ruoyi.system.controller;

import com.ruoyi.common.annotation.Broadcast;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author eddie
 * @version 1.0
 * @date 2025/5/13 19:34
 */
@RestController
public class testsController extends BaseController {
    @Autowired
    SysUserServiceImpl sysUserService;

    @Broadcast(prefix = "ORDER", broadcastReturnValue = true)
    @GetMapping("/test")
    public TableDataInfo test(){
        List<SysUser> sysUsers = sysUserService.selectUserList(new SysUser());
        return getDataTable(sysUsers);
    }

}
