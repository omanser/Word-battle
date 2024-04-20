package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.ArrayList;
@Data
public class Meanings {
    private String wordType;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ArrayList<String> meaning;
}
