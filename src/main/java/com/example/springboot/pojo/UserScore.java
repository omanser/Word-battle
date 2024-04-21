package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_score")
public class UserScore {
    // 设置id和wordBookId为双主键
    @MppMultiId
    @TableId(value = "user_id")
    private String id;
    @MppMultiId
    private Integer wordBookId;
    private Integer score;
    private LocalDateTime updateTime;

    public UserScore(String userId, Integer bookId, Integer score) {
        this.id = userId;
        this.wordBookId = bookId;
        this.score = score;
        this.updateTime = LocalDateTime.now();
    }
}
