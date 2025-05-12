package com.ruoyi.business.service;



import com.ruoyi.business.domain.EbUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 使用者Service接口
 * 
 * @author travelshare
 * @date 2025-03-17
 */
public interface IEbUserService 
{
    /**
     * 查询使用者
     * 
     * @param uid 使用者主键
     * @return 使用者
     */
    public EbUser selectEbUserByUid(String uid);

    /**
     * 查询使用者列表
     * 
     * @param ebUser 使用者
     * @return 使用者集合
     */
    public List<EbUser> selectEbUserList(EbUser ebUser);

    /**
     * 新增使用者
     * 
     * @param ebUser 使用者
     * @return 结果
     */
    public int insertEbUser(EbUser ebUser);

    /**
     * 修改使用者
     * 
     * @param ebUser 使用者
     * @return 结果
     */
    public int updateEbUser(EbUser ebUser);

    /**
     * 批量删除使用者
     * 
     * @param uids 需要删除的使用者主键集合
     * @return 结果
     */
    public int deleteEbUserByUids(String[] uids);

    /**
     * 删除使用者信息
     * 
     * @param uid 使用者主键
     * @return 结果
     */
    public int deleteEbUserByUid(String uid);


    EbUser selectEbUserByUserNameAndPassword(String username, String password);

    EbUser selectEbUserByPhone(String phone);

    public Map<String, EbUser> selectEbUserByUids(Set<Long> pubIds);
}
