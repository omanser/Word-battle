package com.example.springboot.socket;

import com.example.springboot.Application;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ServerEndpoint(value = "/api/websocket")
@Component
public class WebSocketServer {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket连接成功");

        try {
            session.getBasicRemote().sendText("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        System.out.println("WebSocket连接关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("接收到客户端消息：" + message);

        try {
            session.getBasicRemote().sendText("服务器收到消息：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("WebSocket连接出错");
        error.printStackTrace();
    }

}
