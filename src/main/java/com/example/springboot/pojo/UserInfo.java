package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.LocalTime.now;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_info")
public class UserInfo {
    // 设置id和wordBookId为双主键
    @MppMultiId
    private String id;
    private String username;
    @MppMultiId
    private Integer wordBookId;
    private String userPicUrl;
    private Integer score;
    private LocalDateTime updateTime;

    public UserInfo(String userId, String username, Integer bookId, String userPicUrl, Integer score) {
        this.id = userId;
        this.username = username;
        this.wordBookId = bookId;
        this.userPicUrl = userPicUrl;
        this.score = score;
        this.updateTime = LocalDateTime.now();
    }
}
