package com.ruoyi.business.config;

/**
 * @author eddie
 * @version 1.0
 * @date 2025/3/31 16:28
 */
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "qiniu") // 自动绑定 yaml 中 qiniu 开头的配置
public class QiniuPropertiesConfig {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String domain;
    private String fileAccess;

    // Getter 和 Setter 必须存在（否则无法注入）
    public String getAccessKey() { return accessKey; }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }

    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }

    public String getBucketName() { return bucketName; }
    public void setBucketName(String bucketName) { this.bucketName = bucketName; }

    public String getDomain() { return domain; }
    public void setDomain(String domain) { this.domain = domain; }

    public String getFileAccess() { return fileAccess; }
    public void setFileAccess(String fileAccess) { this.fileAccess = fileAccess; }
}