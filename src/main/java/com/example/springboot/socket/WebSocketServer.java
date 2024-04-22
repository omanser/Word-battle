package com.example.springboot.socket;

import com.example.springboot.Application;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.OnlineUser;
import com.example.springboot.pojo.Words;
import com.example.springboot.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint(value = "/api/websocket/{user_id}/{book_id}")
@Slf4j
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    // 存储所有连接的WebSocket客户端
    private static final ConcurrentHashMap<Integer,ConcurrentHashMap<Long,ConcurrentHashMap<String, OnlineUser>>> roomSessionMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, ArrayList<Words>> wordsMap = new ConcurrentHashMap<>();
    @Autowired
    private UserService userService;
    private Long m_roomId;
    private static Long roomId = 0L;

    public ConcurrentHashMap<Integer,ConcurrentHashMap<Long,ConcurrentHashMap<String, OnlineUser>>> getSessionMap() {
        return roomSessionMap;
    }
    public ConcurrentHashMap<Long, ArrayList<Words>> getWordsMap() {
        return wordsMap;
    }
    private ObjectMapper objectMapper = new ObjectMapper(); // Jackson的对象映射器
    @OnOpen
    public void onOpen(Session session, @PathParam("user_id") String userId, @PathParam("book_id") Integer bookId) {
        OnlineUser onlineUser = new OnlineUser(userId, -1L, session);
        ConcurrentHashMap<String, OnlineUser> sessionMap = new ConcurrentHashMap<>();
        sessionMap.put(userId, onlineUser);
        ConcurrentHashMap<Long,ConcurrentHashMap<String, OnlineUser>> room = roomSessionMap.get(bookId);
        if (room == null || room.isEmpty()) {
            room = new ConcurrentHashMap<>();
            sessionMap.get(userId).setRoomId(roomId);
            room.put(roomId, sessionMap);
            m_roomId = roomId;
            roomId += 1L;
            roomSessionMap.put(bookId, room);
        } else {
            // 遍历room寻找房间人数小于2的房间，未找到则新创建一个房间
            boolean success = false;
            for (Map.Entry<Long,ConcurrentHashMap<String, OnlineUser>> entry : room.entrySet()) {
                if (entry.getValue().size() < 2) {
                    m_roomId = entry.getKey();
                    onlineUser.setRoomId(m_roomId);
                    entry.getValue().put(userId, onlineUser);
                    success = true;
                    logger.info("有新的用户进入，book_id={}，userId={}，当前房间在线人数：{}",bookId, userId, roomSessionMap.get(bookId).get(m_roomId).size());
                    try {
                        session.getBasicRemote().sendText("连接房间成功房间号：" + entry.getKey());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!success) {
                m_roomId = roomId;
                onlineUser.setRoomId(m_roomId);
                sessionMap.put(userId, onlineUser);
                room.put(m_roomId, sessionMap);
                roomId += 1L;
                logger.info("有新的用户进入，book_id={}，userId={}，当前房间在线人数：{}",bookId, userId, roomSessionMap.get(bookId).get(m_roomId).size());
                try {
                    session.getBasicRemote().sendText("连接房间成功房间号：" + m_roomId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("user_id") String userId, @PathParam("book_id") Integer bookId) {
        roomSessionMap.get(bookId).get(m_roomId).remove(userId);
        logger.info("有用户退出，当前在线人数：{}", roomSessionMap.get(bookId).get(m_roomId).size());
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("user_id") String userId, @PathParam("book_id") Integer bookId) {
        logger.info("接收到客户端消息：{}", message);
        try {
            session.getBasicRemote().sendText("服务器收到消息：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("WebSocket连接出错");
        error.printStackTrace();
    }
    private void sendToAllUserForRoomCount(Integer bookId, Long roomId) {



    }

}
