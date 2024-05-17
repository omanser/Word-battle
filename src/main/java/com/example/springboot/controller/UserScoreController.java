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
            return Result.error(1,"用户未学习过该单词集");
        }
        return Result.success(userScore.getScore());
    }
    @PutMapping("/update_score")
    public Result updateUser(String userId, Integer bookId, Integer score) {
        UserScore userScoreDB = userScoreService.getOne(new QueryWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        if(userScoreDB == null) {
            userScoreService.save(new UserScore(userId, bookId, score));
        } else if(userScoreDB.getScore() >= score) {
            return Result.error(1,"分数低于最高分分数");
        } else {
            userScoreDB.setScore(score);
            userScoreService.update(userScoreDB, new UpdateWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        }
        return Result.success();
    }
    @PutMapping("/update_win_num")
    public Result updateWinNum(String userId, Integer bookId) {
        UserScore userScoreDB = userScoreService.getOne(new QueryWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        if(userScoreDB == null) {
            userScoreDB = new UserScore(userId, bookId, 0);
            userScoreDB.setWinNum(1);
            userScoreService.save(userScoreDB);
        } else {
            userScoreDB.setWinNum(userScoreDB.getWinNum() + 1);
            userScoreService.update(userScoreDB, new UpdateWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        }
        return Result.success();
    }
    @PutMapping("/update_lose_num")
    public Result updateLoseNum(String userId, Integer bookId) {
        UserScore userScoreDB = userScoreService.getOne(new QueryWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        if(userScoreDB == null) {
            userScoreDB = new UserScore(userId, bookId, 0);
            userScoreDB.setLoseNum(1);
            userScoreService.save(userScoreDB);
        } else {
            userScoreDB.setLoseNum(userScoreDB.getLoseNum() + 1);
            userScoreService.update(userScoreDB, new UpdateWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        }
        return Result.success();
    }
    @GetMapping("get_score_rank")
    public Result getScoreRank(Integer bookId) {
        return Result.success(userScoreService.getScoreRank(bookId));
    }
    @GetMapping("get_rank_battle")
    public Result getRankBattle(String userId, Integer bookId) {
        UserScore userScoreDB = userScoreService.getOne(new QueryWrapper<UserScore>().eq("user_id", userId).eq("word_book_id", bookId));
        if(userScoreDB == null) {
            return Result.success(1,-1);
        }
        return Result.success(userScoreService.getScoreRank(bookId).indexOf(userScoreDB) + 1);
    }
}
