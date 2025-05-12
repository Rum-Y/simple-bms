package com.ruoyi.business.mapper;


import com.ruoyi.business.domain.EbUser;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 使用者Mapper接口
 *
 * @author travelshare
 * @date 2025-03-17
 */
public interface EbUserMapper {
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
     * 删除使用者
     *
     * @param uid 使用者主键
     * @return 结果
     */
    public int deleteEbUserByUid(String uid);

    /**
     * 批量删除使用者
     *
     * @param uids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEbUserByUids(String[] uids);

    EbUser selectEbUserByUserNameAndPassword(String username, String password);

    EbUser selectEbUserByPhone(String phone);

    @MapKey("uid")
    public Map<String, EbUser> selectEbUserByUids(@Param("pubIds") Set<Long> pubIds);

    @MapKey("uid")
    Map<String, EbUser> selectUserAvatarsByIds(@Param("userIds") Set<Long> userIds);

    List<EbUser> selectEbUsersByUids(@Param("userIds") ArrayList<String> userIds);

    int updateEbUserWithVersion(EbUser ebUser);
}
