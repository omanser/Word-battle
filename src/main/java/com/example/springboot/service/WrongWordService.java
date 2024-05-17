package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.pojo.WrongWord;

public interface WrongWordService extends IService<WrongWord> {
    IPage<WrongWord> findWrongWordPage(IPage<WrongWord> page, QueryWrapper<WrongWord> queryWrapper);
}
