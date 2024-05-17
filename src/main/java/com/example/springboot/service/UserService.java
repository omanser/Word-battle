package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.pojo.User;
import com.example.springboot.pojo.UserScore;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getScoreRank(Integer bookId);
}
