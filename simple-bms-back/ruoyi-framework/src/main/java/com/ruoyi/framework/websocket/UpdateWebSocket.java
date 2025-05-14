package com.ruoyi.framework.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.framework.websocket.message.WebSocketMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * websocket广播会发送更改的消息到所有连接的客户端。
 * 服务器每30秒检测一次   客户端需要响应pong，服务器会定时发送ping，客户端收到ping后应答pong，否则超时关闭连接
 * @version 1.0
 * @date 2025/5/13 19:32
 */
@Component
@ServerEndpoint(value = "/websocket/updates")
public class UpdateWebSocket {
    // 线程安全的 Session 集合
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Map<Session, Long> lastPongTime = new ConcurrentHashMap<>();

    // 初始化心跳检测
    static {
        scheduler.scheduleAtFixedRate(UpdateWebSocket::checkAliveSessions, 30, 30, TimeUnit.SECONDS);
    }

    // 心跳检测方法
    private static void checkAliveSessions() {
        sessions.forEach(session -> {
            try {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText("ping"); // 发送心跳包
                }
            } catch (Exception e) {
                sessions.remove(session);
            }
            if (System.currentTimeMillis() - lastPongTime.getOrDefault(session, 0L) > 60000) {
                try {
                    session.close(); // 超时60秒未响应则关闭
                } catch (IOException e) {
                }
            }
        });
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        lastPongTime.put(session, System.currentTimeMillis());
        System.out.println("新连接建立，当前在线数：" + sessions.size());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("连接关闭，剩余在线数：" + sessions.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
        if ("pong".equals(message)) {
            lastPongTime.put(session, System.currentTimeMillis());
        }
    }
    @PreDestroy
    public void destroy() {
        scheduler.shutdownNow();
        sessions.forEach(session -> {
            if (session.isOpen()) {
                try {
                    session.close();
                } catch (IOException e) {
                }
            }
        });
    }

    // 静态广播方法（关键）
    public static void broadcast(WebSocketMessage message) {
        try {
            String json = new ObjectMapper().writeValueAsString(message);
            sessions.forEach(session -> {
                if(session.isOpen()) {
                    session.getAsyncRemote().sendText(json);
                }
            });
        } catch (JsonProcessingException e) {
            System.out.println("JSON序列化失败");
        }
    }
}