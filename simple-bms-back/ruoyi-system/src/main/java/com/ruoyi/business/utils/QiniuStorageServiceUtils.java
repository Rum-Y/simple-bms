package com.ruoyi.business.utils;

import com.qiniu.cdn.CdnManager;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.ruoyi.business.config.QiniuPropertiesConfig;
import com.ruoyi.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 自定义七牛云上传存储服务工具类
 */
@Component
public class QiniuStorageServiceUtils {

    @Autowired
    QiniuPropertiesConfig qiniuPropertiesConfig;

    private static String ACCESS_KEY;
    private static String SECRET_KEY;
    //空间名称
    private static String BUCKET_NAME;
    private static String DOMAIN;
    private static String fileAccess;

    // 初始化七牛云组件（线程安全）
    private static Auth auth;
    //自动选择上传区域，比如广东什么的
    private static UploadManager uploadManager;

    // 使用 @PostConstruct 来初始化静态变量
    @PostConstruct
    public void init() {
        ACCESS_KEY =qiniuPropertiesConfig.getAccessKey();
        SECRET_KEY = qiniuPropertiesConfig.getSecretKey();
        //空间名称
        BUCKET_NAME = qiniuPropertiesConfig.getBucketName();
        DOMAIN = qiniuPropertiesConfig.getDomain();
        fileAccess=qiniuPropertiesConfig.getFileAccess();
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
    }
    public String saveRegAvatar(MultipartFile avatar) {
        if (avatar == null || avatar.isEmpty()) {
            return "";
        }
        // 初始化七牛云组件（线程安全）
        try (InputStream inputStream = avatar.getInputStream()) {
            // 1. 图像格式转换处理
            BufferedImage processedImage = processImage(inputStream);

            // 2. 转换为JPG字节流
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(processedImage, "jpg", os);
            byte[] imageBytes = os.toByteArray();

            // 3. 生成云端文件名
            String fileKey = "wxAvatars/" + UUID.randomUUID() + ".jpg";

            // 4. 上传到七牛云
            String uploadToken = auth.uploadToken(BUCKET_NAME);
            Response response = uploadManager.put(
                    imageBytes,
                    fileKey,
                    uploadToken,
                    null,
                    "image/jpeg",
                    true
            );

            // 5. 验证上传结果
            if (response.isOK()) {
                return Constants.OSSRESOURCE_PREFIX + "/" + fileKey;
            }
        } catch (QiniuException e) {
            e.printStackTrace();
            System.err.println("七牛云上传错误：" + e.response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 图像处理逻辑（保持原有处理流程）
     */
    private BufferedImage processImage(InputStream inputStream) throws IOException {
        BufferedImage originalImage = ImageIO.read(inputStream);
        if (originalImage == null) {
            throw new IOException("无法读取图片数据");
        }

        // 转换为RGB格式（处理透明背景）
        if (originalImage.getType() != BufferedImage.TYPE_INT_RGB) {
            BufferedImage rgbImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g = rgbImage.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());
            g.drawImage(originalImage, 0, 0, null);
            g.dispose();
            return rgbImage;
        }
        return originalImage;
    }
    public String moveFileToQiniu(String avatarUrl, String phone) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");
        String phoneUrl = generateUUIDFromPhone(phone);

        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));
        CdnManager cdnManager=new CdnManager(auth);

//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/avatars/" + phoneUrl + "/avatar.jpg"; // 目标文件路径

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }
            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }
            //刷新cdn缓存
            cdnManager.refreshUrls(new String[]{fileAccess + DOMAIN+"/" + targetKey});
            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/uploads/avatars/" + phoneUrl + "/avatar.jpg";
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }

    //移动到封面
    public String moveCoversFileToQiniu(String avatarUrl, String phone) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");
        String phoneUrl = generateUUIDFromPhone(phone);

        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));
//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/covers/" + phoneUrl + "/"+ UUID.randomUUID().toString() +".jpg";

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }
            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }

            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/"+targetKey;
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }

    //移动到俱乐部封面
    public String moveClubCoversFileToQiniu(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");


        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));
//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/club/covers/" + UUID.randomUUID().toString() +".jpg";

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }
            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }

            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/"+targetKey;
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }

    //移动到俱乐部头像
    public String moveClubAvatarFileToQiniu(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");

        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));
//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/club/avatar/" + UUID.randomUUID().toString() +".jpg";

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }
            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }

            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/"+targetKey;
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }


    public String moveProIntroductionFileToQiniu(String avatarUrl, String phone) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");
        String phoneUrl = generateUUIDFromPhone(phone);

        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));

//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/project/" + phoneUrl + "/"+ UUID.randomUUID().toString() +".jpg";

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }
            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }

            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/"+targetKey;
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }

    //移动到公告图
    public String moveProNoticeIntroductionFileToQiniu(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");


        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));

//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/notice/" + UUID.randomUUID().toString() +".jpg";

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }
            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }

            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/"+targetKey;
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }

    //移动到轮播图
    public String moveBannerFileToQiniu(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");

        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));

//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/banner/" + UUID.randomUUID().toString() +".jpg";

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }

            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }

            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/"+targetKey;
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }

    //移动到评论图
    public String moveCommentIntroductionFileQiniu(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");

        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));

//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/comment/" + UUID.randomUUID().toString() +".jpg";

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }
            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }

            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/"+targetKey;
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }


    //移动到俱乐部评论图
    public String moveClubCommentIntroductionFileQiniu(String avatarUrl) {
        if (avatarUrl == null || avatarUrl.equals("")) {
            return "";
        }
        //去除七牛云前缀
        String fileKey=extractFileKey(avatarUrl);
//        String realAvatarUrl = avatarUrl.replace(Constants.RESOURCE_PREFIX, "");

        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));

//        String sourceKey = "wxAvatars/" + realAvatarUrl; // 源文件路径
        String targetKey = "uploads/club/comment/" + UUID.randomUUID().toString() +".jpg";

        try {
            // 1. 检查目标文件是否存在
            try {
                FileInfo targetFileInfo = bucketManager.stat(BUCKET_NAME, targetKey);
                // 如果存在，先删除
                bucketManager.delete(BUCKET_NAME, targetKey);
            } catch (QiniuException e) {
//                if (e.code() != 612) { // 612 是文件不存在的错误码，其他错误抛出
//
//                }
            }
            // 复制文件到新路径
            Response copyResponse = bucketManager.copy(BUCKET_NAME, fileKey, BUCKET_NAME, targetKey);

            if (!copyResponse.isOK()) {
                return "";
            }

            // 3. 删除原文件（忽略不存在的情况）
            try {
                bucketManager.delete(BUCKET_NAME, fileKey);
            } catch (QiniuException e) {
                if (e.code() != 612) { // 不是文件不存在错误，则记录
//                    System.err.println("删除原文件失败: " + e.response);
                }
            }

            // 返回目标文件的 URL
            return Constants.OSSRESOURCE_PREFIX + "/"+targetKey;
        } catch (QiniuException e) {
            e.printStackTrace();
            return "";
        }
    }


    // 基于手机号生成相同的 UUID 字符串
    private String generateUUIDFromPhone(String phoneNumber) {
        try {
            // 使用 SHA-256 算法对手机号进行哈希处理
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(phoneNumber.getBytes(StandardCharsets.UTF_8));

            // 取哈希值的前 16 字节作为 UUID 的字节（UUID 长度是 16 字节）
            byte[] uuidBytes = new byte[16];
            System.arraycopy(hashedBytes, 0, uuidBytes, 0, 16);

            // 将字节数组转换为字符串格式的 UUID
            StringBuilder uuidString = new StringBuilder(36);
            for (int i = 0; i < 16; i++) {
                int b = uuidBytes[i] & 0xFF;
                if (b < 0x10) uuidString.append('0');
                uuidString.append(Integer.toHexString(b));
                if (i == 3 || i == 5 || i == 7 || i == 9) {
                    uuidString.append('-');
                }
            }
            return uuidString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> deleteImages(List<String> imgList) {
        // 初始化七牛云的认证信息
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        BucketManager bucketManager = new BucketManager(auth,new Configuration(Region.autoRegion()));
        List<String> failedDeletes = new ArrayList<>(); // 用于记录删除失败的文件路径
        // 获取项目根目录路径
        for (String imgPath : imgList) {
            try {
                //去除七牛云前缀
                String fileKey=extractFileKey(imgPath);
                // 1. 检查文件是否存在
                bucketManager.stat(BUCKET_NAME, fileKey); // 如果文件不存在会抛 QiniuException

                // 2. 执行删除
                Response response = bucketManager.delete(BUCKET_NAME, fileKey);
                response.isOK();
                if (!response.isOK()) {
                    failedDeletes.add(imgPath);
                }
            } catch (QiniuException e) {
//                if (e.code() == 612) { // 612 是七牛云定义的「文件不存在」错误码
//                } else {
//                    System.err.println("七牛云删除错误: " + e.response);
//                }
            }

        }
        return failedDeletes; // 返回删除失败的文件路径列表
    }

    private String extractFileKey(String avatarUrl) {
        String prefix = Constants.OSSRESOURCE_PREFIX;
        if (avatarUrl.startsWith(prefix)) {
            return avatarUrl.replace(prefix+"/", "");
        }
        return avatarUrl; // 如果已经是纯 Key，直接返回
    }
}