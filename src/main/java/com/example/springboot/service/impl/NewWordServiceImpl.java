package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.mapper.NewWordMapper;
import com.example.springboot.pojo.NewWord;
import com.example.springboot.service.NewWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewWordServiceImpl extends ServiceImpl<NewWordMapper, NewWord> implements NewWordService {
    @Autowired
    private NewWordMapper newWordMapper;
    @Override
    public IPage<NewWord> findNewWordPage(IPage<NewWord> page, QueryWrapper<NewWord> queryWrapper) {
        return newWordMapper.selectPage(page, queryWrapper);
    }
}
