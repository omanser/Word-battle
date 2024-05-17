package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@TableName("user")
public class User {
    private String id;
    private String username;
    private String userPicUrl = "http://lc-qtElbL7m.cn-n1.lcfile.com/Q3gKiYJm5xc0zCRuM9wBGbveOq3c7cUW/72f298b9_E880884_d0f63115.png";
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }
}
