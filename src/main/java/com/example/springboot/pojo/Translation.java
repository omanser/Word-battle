package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.ArrayList;
@Data
public class Translation {
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ArrayList<Meanings> meanings; // 含义
}
