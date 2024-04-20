package com.example.springboot.controller;

import com.example.springboot.pojo.Words;
import com.example.springboot.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordController {
    @Autowired
    private WordService wordService;
    @RequestMapping("/findWordById")
    public Words findWordById(Integer id) {
        return wordService.getById(id);
    }
    @RequestMapping("/findWordByWord")
    public Words findWordByWord(String word) {
        return wordService.findWordByWord(word);
    }
    @RequestMapping("/findAllWordsByBookId")
    public Object findAllWordsByBookId(Integer wordbook_id) {
        return wordService.findAllWordsByBookId(wordbook_id);
    }
}
