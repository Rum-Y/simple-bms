package com.ruoyi.business.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 使用者对象 eb_user
 * 
 * @author travelshare
 * @date 2025-03-17
 */
public class EbUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 使用者id */
    private String uid;

    /** 使用者賬號 */
    @Excel(name = "使用者賬號")
    private String account;

    /** 使用者密碼 */
    @Excel(name = "使用者密碼")
    private String pwd;

    /** 真實姓名 */
    @Excel(name = "真實姓名")
    private String realName;

    /** 生日 */
    @Excel(name = "生日")
    private String birthday;

    /** 身份證號碼 */
    @Excel(name = "身份證號碼")
    private String cardId;

    /** 使用者備註 */
    @Excel(name = "使用者備註")
    private String mark;

    /** 合夥人id */
    @Excel(name = "合夥人id")
    private Long partnerId;

    /** 使用者分組id */
    @Excel(name = "使用者分組id")
    private String groupId;

    /** 小程序代表用户的openid */
    @Excel(name = "標籤id")
    private String tagId;

    /** 使用者昵稱 */
    @Excel(name = "使用者昵稱")
    private String nickname;

    /** 使用者頭像 */
    @Excel(name = "使用者頭像")
    private String avatar;

    /** 手機號碼 */
    @Excel(name = "手機號碼")
    private String phone;

    /** 新增ip */
    @Excel(name = "新增ip")
    private String addIp;

    /** 最後一次登錄ip */
    @Excel(name = "最後一次登錄ip")
    private String lastIp;

    /** 使用者餘額 */
    @Excel(name = "使用者餘額")
    private BigDecimal nowMoney;

    /** 傭金金額 */
    @Excel(name = "傭金金額")
    private BigDecimal brokeragePrice;

    /** 使用者剩餘積分 */
    @Excel(name = "使用者剩餘積分")
    private Long integral;

    /** 使用者剩餘經驗 */
    @Excel(name = "使用者剩餘經驗")
    private String experience;

    /** 連續簽到天數 */
    @Excel(name = "連續簽到天數")
    private Long signNum;

    /** 1為正常，0為禁止 */
    @Excel(name = "1為正常，0為禁止")
    private boolean status;

    /** 等級 */
    @Excel(name = "等級")
    private String level;

    /** 推廣員id */
    @Excel(name = "推廣員id")
    private String spreadUid;

    /** 推廣員關聯時間 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "推廣員關聯時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date spreadTime;

    /** 使用者型別 */
    @Excel(name = "使用者型別")
    private String userType;

    /** 是否為推廣員 */
    @Excel(name = "是否為推廣員")
    private String isPromoter;

    /** 使用者購買次數 */
    @Excel(name = "使用者購買次數")
    private String payCount;

    /** 下級人數 */
    @Excel(name = "下級人數")
    private Long spreadCount;

    /** 詳細地址 */
    @Excel(name = "詳細地址")
    private String addres;

    /** 管理員編號  */
    @Excel(name = "管理員編號 ")
    private String adminid;

    /** 使用者登陸型別，h5,wechat,routine */
    @Excel(name = "使用者登陸型別，h5,wechat,routine")
    private String loginType;

    /** 最後一次登錄時間 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最後一次登錄時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLoginTime;

    /** 清除時間 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "清除時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cleanTime;

    /** 推廣等級記錄 */
    @Excel(name = "推廣等級記錄")
    private String path;

    /** 是否關注公眾號 */
    @Excel(name = "是否關注公眾號")
    private Long subscribe;

    /** 關注公眾號時間 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "關注公眾號時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date subscribeTime;

    /** 性別，0未知，1男，2女，3保密 */
    @Excel(name = "性別，0未知，1男，2女，3保密")
    private Integer sex;

    /** 國家，中國CN，其他OTHER */
    @Excel(name = "國家，中國CN，其他OTHER")
    private String country;

    /** 成為分銷員時間 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成為分銷員時間", width = 30, dateFormat = "yyyy-MM-dd")
    private Date promoterTime;

    /** 邀请成功的用户uid */
    @Excel(name = "邀请成功的用户uid")
    private String ids;

    /** 邀请人手机号 */
    @Excel(name = "邀请人手机号")
    private String invitePhone;

    @Excel(name = "所在地区")
    private String location;

    @Excel(name = "行业领域")
    private String sector;

    private BigDecimal withdrawable;

    private BigDecimal freezeMoney;



    public boolean isStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUid()
    {
        return uid;
    }
    public void setAccount(String account) 
    {
        this.account = account;
    }

    public String getAccount() 
    {
        return account;
    }
    public void setPwd(String pwd) 
    {
        this.pwd = pwd;
    }

    public String getPwd() 
    {
        return pwd;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setBirthday(String birthday) 
    {
        this.birthday = birthday;
    }

    public String getBirthday() 
    {
        return birthday;
    }
    public void setCardId(String cardId) 
    {
        this.cardId = cardId;
    }

    public String getCardId() 
    {
        return cardId;
    }
    public void setMark(String mark) 
    {
        this.mark = mark;
    }

    public String getMark() 
    {
        return mark;
    }
    public void setPartnerId(Long partnerId) 
    {
        this.partnerId = partnerId;
    }

    public Long getPartnerId() 
    {
        return partnerId;
    }
    public void setGroupId(String groupId) 
    {
        this.groupId = groupId;
    }

    public String getGroupId() 
    {
        return groupId;
    }
    public void setTagId(String tagId) 
    {
        this.tagId = tagId;
    }

    public String getTagId() 
    {
        return tagId;
    }
    public void setNickname(String nickname) 
    {
        this.nickname = nickname;
    }

    public String getNickname() 
    {
        return nickname;
    }
    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAddIp(String addIp) 
    {
        this.addIp = addIp;
    }

    public String getAddIp() 
    {
        return addIp;
    }
    public void setLastIp(String lastIp) 
    {
        this.lastIp = lastIp;
    }

    public String getLastIp() 
    {
        return lastIp;
    }
    public void setNowMoney(BigDecimal nowMoney) 
    {
        this.nowMoney = nowMoney;
    }

    public BigDecimal getNowMoney() 
    {
        return nowMoney;
    }
    public void setBrokeragePrice(BigDecimal brokeragePrice) 
    {
        this.brokeragePrice = brokeragePrice;
    }

    public BigDecimal getBrokeragePrice() 
    {
        return brokeragePrice;
    }
    public void setIntegral(Long integral) 
    {
        this.integral = integral;
    }

    public Long getIntegral() 
    {
        return integral;
    }
    public void setExperience(String experience) 
    {
        this.experience = experience;
    }

    public String getExperience() 
    {
        return experience;
    }
    public void setSignNum(Long signNum) 
    {
        this.signNum = signNum;
    }

    public Long getSignNum() 
    {
        return signNum;
    }
    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public boolean getStatus()
    {
        return status;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setSpreadUid(String spreadUid) 
    {
        this.spreadUid = spreadUid;
    }

    public String getSpreadUid() 
    {
        return spreadUid;
    }
    public void setSpreadTime(Date spreadTime) 
    {
        this.spreadTime = spreadTime;
    }

    public Date getSpreadTime() 
    {
        return spreadTime;
    }
    public void setUserType(String userType) 
    {
        this.userType = userType;
    }

    public String getUserType() 
    {
        return userType;
    }
    public void setIsPromoter(String isPromoter) 
    {
        this.isPromoter = isPromoter;
    }

    public String getIsPromoter() 
    {
        return isPromoter;
    }
    public void setPayCount(String payCount) 
    {
        this.payCount = payCount;
    }

    public String getPayCount() 
    {
        return payCount;
    }
    public void setSpreadCount(Long spreadCount) 
    {
        this.spreadCount = spreadCount;
    }

    public Long getSpreadCount() 
    {
        return spreadCount;
    }
    public void setAddres(String addres) 
    {
        this.addres = addres;
    }

    public String getAddres() 
    {
        return addres;
    }
    public void setAdminid(String adminid) 
    {
        this.adminid = adminid;
    }

    public String getAdminid() 
    {
        return adminid;
    }
    public void setLoginType(String loginType) 
    {
        this.loginType = loginType;
    }

    public String getLoginType() 
    {
        return loginType;
    }
    public void setLastLoginTime(Date lastLoginTime) 
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTime() 
    {
        return lastLoginTime;
    }
    public void setCleanTime(Date cleanTime) 
    {
        this.cleanTime = cleanTime;
    }

    public BigDecimal getWithdrawable() {
        return withdrawable;
    }

    public void setWithdrawable(BigDecimal withdrawable) {
        this.withdrawable = withdrawable;
    }

    public BigDecimal getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(BigDecimal freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public Date getCleanTime()
    {
        return cleanTime;
    }
    public void setPath(String path) 
    {
        this.path = path;
    }

    public String getPath() 
    {
        return path;
    }
    public void setSubscribe(Long subscribe) 
    {
        this.subscribe = subscribe;
    }

    public Long getSubscribe() 
    {
        return subscribe;
    }
    public void setSubscribeTime(Date subscribeTime) 
    {
        this.subscribeTime = subscribeTime;
    }

    public Date getSubscribeTime() 
    {
        return subscribeTime;
    }
    public void setSex(Integer sex) 
    {
        this.sex = sex;
    }

    public Integer getSex() 
    {
        return sex;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
    }
    public void setPromoterTime(Date promoterTime) 
    {
        this.promoterTime = promoterTime;
    }

    public Date getPromoterTime() 
    {
        return promoterTime;
    }
    public void setIds(String ids) 
    {
        this.ids = ids;
    }

    public String getIds() 
    {
        return ids;
    }
    public void setInvitePhone(String invitePhone) 
    {
        this.invitePhone = invitePhone;
    }

    public String getInvitePhone() 
    {
        return invitePhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uid", getUid())
            .append("account", getAccount())
            .append("pwd", getPwd())
            .append("realName", getRealName())
            .append("birthday", getBirthday())
            .append("cardId", getCardId())
            .append("mark", getMark())
            .append("partnerId", getPartnerId())
            .append("groupId", getGroupId())
            .append("tagId", getTagId())
            .append("nickname", getNickname())
            .append("avatar", getAvatar())
            .append("phone", getPhone())
            .append("addIp", getAddIp())
            .append("lastIp", getLastIp())
            .append("nowMoney", getNowMoney())
            .append("brokeragePrice", getBrokeragePrice())
            .append("integral", getIntegral())
            .append("experience", getExperience())
            .append("signNum", getSignNum())
            .append("status", getStatus())
            .append("level", getLevel())
            .append("spreadUid", getSpreadUid())
            .append("spreadTime", getSpreadTime())
            .append("userType", getUserType())
            .append("isPromoter", getIsPromoter())
            .append("payCount", getPayCount())
            .append("spreadCount", getSpreadCount())
            .append("addres", getAddres())
            .append("adminid", getAdminid())
            .append("loginType", getLoginType())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("lastLoginTime", getLastLoginTime())
            .append("cleanTime", getCleanTime())
            .append("path", getPath())
            .append("subscribe", getSubscribe())
            .append("subscribeTime", getSubscribeTime())
            .append("sex", getSex())
            .append("country", getCountry())
            .append("promoterTime", getPromoterTime())
            .append("ids", getIds())
            .append("invitePhone", getInvitePhone())
            .toString();
    }
}
