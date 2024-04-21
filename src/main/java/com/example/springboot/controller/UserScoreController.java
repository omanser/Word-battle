package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.User;
import com.example.springboot.pojo.UserScore;
import com.example.springboot.service.UserScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userScore")
public class UserScoreController {
    @Autowired
    private UserScoreService userScoreService;
    @RequestMapping("/getScore")
    public Result getScore(String userId, Integer bookId) {
        UserScore userScore = userScoreService.getOne(new QueryWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        if(userScore == null) {
            return Result.error("用户未学习过该单词集");
        }
        return Result.success(userScore.getScore());
    }
    @PutMapping("/update")
    public Result updateUser(String userId, Integer bookId, Integer score) {
        UserScore userScoreDB = userScoreService.getOne(new QueryWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        if(userScoreDB == null) {
            userScoreService.save(new UserScore(userId, bookId, score));
        } else if(userScoreDB.getScore() >= score) {
            return Result.error("分数低于最高分分数");
        } else {
            userScoreDB.setScore(score);
            userScoreService.update(userScoreDB, new UpdateWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        }
        return Result.success();
    }
}
