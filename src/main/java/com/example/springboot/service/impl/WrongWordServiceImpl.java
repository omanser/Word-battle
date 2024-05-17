package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.mapper.WrongWordMapper;
import com.example.springboot.pojo.WrongWord;
import com.example.springboot.service.WrongWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WrongWordServiceImpl extends ServiceImpl<WrongWordMapper, WrongWord> implements WrongWordService {
    @Autowired
    private WrongWordMapper wrongWordMapper;
    @Override
    public IPage<WrongWord> findWrongWordPage(IPage<WrongWord> page, QueryWrapper<WrongWord> queryWrapper) {
        return wrongWordMapper.selectPage(page,queryWrapper);
    }
}
