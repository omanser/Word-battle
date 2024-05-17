package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@TableName("new_word")
public class NewWord {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String word;
    private String meaning;
    private LocalDateTime updateTime;
}
