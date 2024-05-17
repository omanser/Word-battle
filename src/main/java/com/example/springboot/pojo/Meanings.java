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

    @Override
    public String toString() {
        String ans = "";
        for(int i = 0; i < meaning.size() - 1; i++){
            ans = ans.concat(meaning.get(i)).concat("，");
        }
        if(meaning.size() != 0) ans = ans.concat(meaning.get(meaning.size() - 1));
        return ans;
    }
}
