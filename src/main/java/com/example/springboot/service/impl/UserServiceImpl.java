package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.pojo.User;
import com.example.springboot.pojo.UserScore;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> getScoreRank(Integer bookId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.exists("select 1 from user_score where user.id = user_score.user_id " +
                        "and word_book_id = " + bookId + " ORDER BY score DESC,user_score.update_time ASC");
        return userMapper.selectList(queryWrapper);
    }
}
