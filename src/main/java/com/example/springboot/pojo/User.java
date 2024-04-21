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
    private String userPicUrl;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
