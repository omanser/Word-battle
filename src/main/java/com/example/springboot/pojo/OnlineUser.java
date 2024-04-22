package com.example.springboot.pojo;

import jakarta.websocket.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnlineUser {
    private String username;
    private Long roomId;
    private Session session;

    public void closeSession() {
        try {
            if (session != null && session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            // 处理异常，可能是日志记录或其他操作
            System.err.println("关闭 Session 时出错: " + e.getMessage());
        }
    }

}