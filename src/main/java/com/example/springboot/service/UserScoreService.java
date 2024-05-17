package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot.pojo.UserScore;

import java.util.List;

public interface UserScoreService extends IService<UserScore> {
    List<UserScore> getScoreRank(Integer bookId);
}
