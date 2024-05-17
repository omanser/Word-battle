package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("user_learning_info")
public class UserLearningInfo {
    private String id;
    private String username;
    private Integer bookId;
    private Integer process;
    private Integer maxProcess;
}
