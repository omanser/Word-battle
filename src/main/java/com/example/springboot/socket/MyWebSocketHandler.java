package com.example.springboot.socket;

import com.example.springboot.Application;
import org.springframework.web.socket.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyWebSocketHandler implements WebSocketHandler {

    // 存储活跃的 WebSocket 会话
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        try {
            if (message instanceof TextMessage textMessage) {
                String payload = textMessage.getPayload();
                // 处理文本消息，例如解析JSON、执行业务逻辑等
                Application.staticLogger.info("Received text message: {}", payload);

                // 在这里添加处理文本消息的具体逻辑
                handleTextMessage(session, payload);
            } else if (message instanceof BinaryMessage) {
                BinaryMessage binaryMessage = (BinaryMessage) message;
                // 获取二进制消息的PayloadData对象，然后转换为字节数组或其他形式的数据
                ByteBuffer payload = binaryMessage.getPayload();
                byte[] bytes = new byte[payload.remaining()];
                payload.get(bytes);

                // 在这里添加处理二进制消息的具体逻辑
                handleBinaryMessage(session, bytes);
            } else if (message instanceof PingMessage) {
                // 处理Ping消息，一般情况下只需回复Pong消息即可
                session.sendMessage(new PongMessage(((PingMessage) message).getPayload()));
            } else if (message instanceof PongMessage) {
                // 可以选择性处理Pong消息，通常不需要额外操作
            } else {
                // 如果有其他未知类型的WebSocketMessage，可以在这里处理或记录警告
                Application.staticLogger.warn("Unsupported message type received: {}", message.getClass().getName());
            }
        } catch (Exception e) {
            Application.staticLogger.error("Error occurred while handling message", e);
            // 根据实际情况决定如何处理异常，如关闭会话、发送错误消息等
        }
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

    // 分离处理文本消息的方法
    private void handleTextMessage(WebSocketSession session, String payload) throws IOException {
        // 在这里实现文本消息的处理逻辑
    }

    // 分离处理二进制消息的方法
    private void handleBinaryMessage(WebSocketSession session, byte[] payload) throws IOException {
        // 在这里实现二进制消息的处理逻辑
    }

    // 其他 WebSocket 处理器方法...
}
