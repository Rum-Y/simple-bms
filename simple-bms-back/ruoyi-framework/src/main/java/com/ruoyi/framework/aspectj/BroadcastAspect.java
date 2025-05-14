package com.ruoyi.framework.aspectj;

/**
 * @author eddie
 * @version 1.0
 * @date 2025/5/14 10:30
 */

import com.ruoyi.common.annotation.Broadcast;
import com.ruoyi.framework.websocket.UpdateWebSocket;
import com.ruoyi.framework.websocket.message.WebSocketMessage;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BroadcastAspect {

    // 拦截所有被@Broadcast注解的方法
    @AfterReturning(
            pointcut = "@annotation(broadcast)",
            returning = "result"
    )
    public void afterMethod(Broadcast broadcast, Object result) {
        if (broadcast.broadcastReturnValue() && result != null) {
            UpdateWebSocket.broadcast(
                    new WebSocketMessage(broadcast.prefix(), 200, result,System.currentTimeMillis())
            );
        }
    }

    // 在切面中添加异常处理
    @AfterThrowing(
            pointcut = "@annotation(broadcast)",
            throwing = "ex"
    )
    public void handleError(Broadcast broadcast, Exception ex) {
        UpdateWebSocket.broadcast(
                new WebSocketMessage("ERROR", 500, ex.getMessage(),System.currentTimeMillis())
        );
    }
}