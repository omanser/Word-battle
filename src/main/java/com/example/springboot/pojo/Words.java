package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
@Data
@TableName(value = "words", autoResultMap = true)
public class Words {
    private int id;
    private String word;
    @TableField(value = "usPhonetic")
    private String usPhonetic; // 美式音标
    @TableField(value = "ukPhonetic")
    private String ukPhonetic;// 英式音标
    @TableField(value = "translation", typeHandler = JacksonTypeHandler.class)
    private Translation translation;//中文释义
}
