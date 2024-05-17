package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.mapper.UserScoreMapper;
import com.example.springboot.pojo.UserScore;
import com.example.springboot.service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserScoreServiceImpl extends ServiceImpl<UserScoreMapper, UserScore> implements UserScoreService {
    @Autowired
    private UserScoreMapper userScoreMapper;

    @Override
    public List<UserScore> getScoreRank(Integer bookId) {
        QueryWrapper<UserScore> queryWrapper = new QueryWrapper<>();
        queryWrapper.exists("select 1 from user where user.id = user_score.user_id " +
                        "and word_book_id = " + bookId)
                .orderByDesc("score")
                .orderByAsc("update_time");
        return userScoreMapper.selectList(queryWrapper);
    }
}
