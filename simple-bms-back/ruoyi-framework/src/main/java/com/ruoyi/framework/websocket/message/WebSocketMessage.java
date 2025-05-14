package com.ruoyi.framework.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author eddie
 * @version 1.0
 * @date 2025/5/14 10:38
 */
@Data
@AllArgsConstructor
public class WebSocketMessage {
    private String type;   // 消息类型（如ORDER）
    private int code;      // 状态码
    private Object data;    // 消息内容
    private Long timestamp;
}
