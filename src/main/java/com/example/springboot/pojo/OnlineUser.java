package com.example.springboot.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String username; //用户名
    private Long roomId; // 房间号
    private Integer userScore; // 用户得分
    @JsonIgnore
    private Boolean isSelect;
    @JsonIgnore
    private Session session; // 用户连接的session

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