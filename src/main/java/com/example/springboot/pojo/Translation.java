package com.example.springboot.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.ArrayList;

public class Translation {
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ArrayList<Meanings> meanings; // 含义

    public ArrayList<Meanings> getMeanings() {
        return meanings;
    }

    public void setMeanings(ArrayList<Meanings> meanings) {
        this.meanings = meanings;
    }
}
