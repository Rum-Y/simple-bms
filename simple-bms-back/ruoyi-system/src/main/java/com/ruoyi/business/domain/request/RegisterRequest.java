package com.ruoyi.business.domain.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author eddie
 * @version 1.0
 * @date 2025/3/18 09:28
 */
public class RegisterRequest {
    private static final long serialVersionUID=1L;

    @NotBlank(message = "手机号不能为空")
//    @Pattern(regexp = "\"^(1[3-9]\\\\d{9})$|^([569]\\\\d{7}|7\\\\d{7}|9\\\\d{7})$\"", message = "手机号码格式错误")
//    @JsonProperty(value = "account")
    private String phone;
    private String code;

    //    @Pattern(regexp = RegularConstants.PASSWORD, message = "密码格式错误，密码必须以字母开头，长度在6~18之间，只能包含字符、数字和下划线")
    private String password;

    private String ids;

    private String captcha;

    private Integer loginKind;
    //昵称
    private String nickName;
    //性别
    private int sex;
    //所在地区
    private String location;
    //行业领域
    private String Sector;
    //用户头像
    private MultipartFile avatar;
    //用户头像路径
    private String avatarUrl;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public @NotBlank(message = "手机号不能为空") @Pattern(regexp = "\"^(1[3-9]\\\\d{9})$|^([569]\\\\d{7}|7\\\\d{7}|9\\\\d{7})$\"", message = "手机号码格式错误") String getPhone() {
        return phone;
    }

    public void setPhone(@NotBlank(message = "手机号不能为空") @Pattern(regexp = "\"^(1[3-9]\\\\d{9})$|^([569]\\\\d{7}|7\\\\d{7}|9\\\\d{7})$\"", message = "手机号码格式错误") String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Integer getLoginKind() {
        return loginKind;
    }

    public void setLoginKind(Integer loginKind) {
        this.loginKind = loginKind;
    }
}
