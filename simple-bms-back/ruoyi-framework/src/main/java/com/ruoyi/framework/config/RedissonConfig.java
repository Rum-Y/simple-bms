package com.ruoyi.framework.config;

import org.apache.commons.lang3.ObjectUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直接使用
 *     @Autowired
 *     private RedissonClient redissonClient;   直接使用
 */
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password:}")  // 密码可为空
    private String redisPassword;

    @Bean("myRedissonClient")
    public RedissonClient redissonClient() {
        Config config = new Config();
        String address = String.format("redis://%s:%d", redisHost, redisPort);
        config.useSingleServer()
                    .setAddress(address)
                    .setDatabase(0);
        if (ObjectUtils.isNotEmpty(redisPassword)) {
            config.useSingleServer()
                    .setPassword(redisPassword);  // 无密码则删除此行
        }

        return Redisson.create(config);
    }
}