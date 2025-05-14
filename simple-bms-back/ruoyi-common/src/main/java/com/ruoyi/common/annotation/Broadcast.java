package com.ruoyi.common.annotation;

/**
 * @author eddie
 * @version 1.0
 * @date 2025/5/14 10:30
 */
import java.lang.annotation.*;

@Target(ElementType.METHOD) // 限定作用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时保留
@Documented
public @interface Broadcast {
    String prefix() default "SYSTEM"; // 消息前缀配置
    boolean broadcastReturnValue() default true; // 是否广播返回值
}