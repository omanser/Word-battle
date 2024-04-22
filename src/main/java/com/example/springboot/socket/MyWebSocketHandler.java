package com.example.springboot.socket;

import com.example.springboot.Application;
import org.springframework.web.socket.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketHandler implements WebSocketHandler {

    // 存储活跃的 WebSocket 会话
    private final Map<Integer, Queue<WebSocketSession>> matchQueues  = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        //sessions.put(sessionId, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
//        // 接收到客户端消息
//        String wordBookId = extractWordBookId(message);
//        if (matchQueues.containsKey(wordBookId)) {
//            // 找到匹配
//            WebSocketSession matchedSession = matchQueues.get(wordBookId).poll();
//            notifyMatching(session, matchedSession);
//        } else {
//            // 没有匹配，加入队列
//            matchQueues.computeIfAbsent(wordBookId, k -> new LinkedList<>()).add(session);
//        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void notifyMatching(WebSocketSession user1, WebSocketSession user2) {
        // 通知匹配的用户
        sendMatchNotification(user1);
        sendMatchNotification(user2);
    }

    private void sendMatchNotification(WebSocketSession session) {
        // 实际发送消息的逻辑
    }
}
