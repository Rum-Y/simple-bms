package com.ruoyi.business.service.impl;


import com.ruoyi.business.domain.EbUser;
import com.ruoyi.business.mapper.EbUserMapper;
import com.ruoyi.business.service.IEbUserService;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 使用者Service业务层处理
 * 
 * @author travelshare
 * @date 2025-03-17
 */
@Service
public class EbUserServiceImpl implements IEbUserService
{
    @Autowired
    private EbUserMapper ebUserMapper;

    /**
     * 查询使用者
     * 
     * @param uid 使用者主键
     * @return 使用者
     */
    @Override
    public EbUser selectEbUserByUid(String uid)
    {
        return ebUserMapper.selectEbUserByUid(uid);
    }

    /**
     * 查询使用者列表
     * 
     * @param ebUser 使用者
     * @return 使用者
     */
    @Override
    public List<EbUser> selectEbUserList(EbUser ebUser)
    {
        return ebUserMapper.selectEbUserList(ebUser);
    }

    /**
     * 新增使用者
     * 
     * @param ebUser 使用者
     * @return 结果
     */
    @Override
    public int insertEbUser(EbUser ebUser)
    {
        ebUser.setCreateTime(DateUtils.getNowDate());
        return ebUserMapper.insertEbUser(ebUser);
    }

    /**
     * 修改使用者
     * 
     * @param ebUser 使用者
     * @return 结果
     */
    @Override
    public int updateEbUser(EbUser ebUser)
    {
        ebUser.setUpdateTime(DateUtils.getNowDate());

        return ebUserMapper.updateEbUser(ebUser);
    }

    /**
     * 批量删除使用者
     * 
     * @param uids 需要删除的使用者主键
     * @return 结果
     */
    @Override
    public int deleteEbUserByUids(String[] uids)
    {
        return ebUserMapper.deleteEbUserByUids(uids);
    }

    /**
     * 删除使用者信息
     * 
     * @param uid 使用者主键
     * @return 结果
     */
    @Override
    public int deleteEbUserByUid(String uid)
    {
        return ebUserMapper.deleteEbUserByUid(uid);
    }

    @Override
    public EbUser selectEbUserByUserNameAndPassword(String username, String password) {
        return ebUserMapper.selectEbUserByUserNameAndPassword(username,password);
    }

    @Override
    public EbUser selectEbUserByPhone(String phone) {
        return ebUserMapper.selectEbUserByPhone(phone);
    }

    @Override
    public Map<String, EbUser> selectEbUserByUids(Set<Long> pubIds) {
        return ebUserMapper.selectEbUserByUids(pubIds);
    }

}
