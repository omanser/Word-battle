package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.pojo.Words;

import java.util.List;

public interface WordService extends IService<Words> {
    Words findWordById(Integer word);
    Words findWordByWord(String word);
    List<Words> findAllWordsByBookId(Integer bookId);
}
