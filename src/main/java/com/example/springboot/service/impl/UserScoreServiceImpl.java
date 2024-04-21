package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.mapper.UserScoreMapper;
import com.example.springboot.pojo.UserScore;
import com.example.springboot.service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserScoreServiceImpl extends ServiceImpl<UserScoreMapper, UserScore> implements UserScoreService {
    @Autowired
    private UserScoreMapper userScoreMapper;
}
