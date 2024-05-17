package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.pojo.NewWord;
import com.example.springboot.pojo.WrongWord;

public interface NewWordService extends IService<NewWord> {
    IPage<NewWord> findNewWordPage(IPage<NewWord> page, QueryWrapper<NewWord> queryWrapper);
}
