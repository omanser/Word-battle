package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.pojo.Words;

import java.util.List;

public interface WordService extends IService<Words> {
    Words findWordById(Integer word);
    Words findWordByWord(String word);
    List<Words> findAllWordsByBookId(Integer bookId);
    IPage<Words> findWordPage(IPage<Words> page, QueryWrapper<Words> queryWrapper);
    long selectCount(QueryWrapper<Words> queryWrapper);
}
