package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.Words;
import com.example.springboot.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/word")
public class WordController {
    @Autowired
    private WordService wordService;
    @RequestMapping("/findWordById")
    public Result findWordById(Integer id) {
        Words word = wordService.findWordById(id);
        if (word == null) {
            return Result.error("未找到单词");
        } else {
            System.out.println(word.getTranslation());
            return Result.success(word);
        }
    }
    @RequestMapping("/findWordByWord")
    public Result findWordByWord(String word) {
        Words words = wordService.findWordByWord(word);
        if (words == null) {
            return Result.error("未找到单词");
        } else {
            return Result.success(words);
        }
    }
    @RequestMapping("/findAllWordsByBookId")
    public Result findAllWordsByBookId(Integer wordbook_id) {
        List<Words> words = wordService.findAllWordsByBookId(wordbook_id);
        if (words == null) {
            return Result.error("未找到单词");
        } else {
            return Result.success(words);
        }
    }
    @RequestMapping("/searchWords")
    public Result searchWord(String word) {
        QueryWrapper<Words> queryWrapper = new QueryWrapper<>();
        queryWrapper.likeRight("word", word);
        List<Words> words = wordService.list(queryWrapper);
        if (words == null) {
            return Result.error("未找到单词");
        } else {
            return Result.success(words);
        }
    }
}
