package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.UserLearningInfo;
import com.example.springboot.service.UserLearningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user_learning_info")
public class UserLearningInfoController {
    @Autowired
    private UserLearningInfoService userLearningInfoService;

    @GetMapping("/get_process")
    public Result getProcess(String userId, String username, Integer bookId) {
        UserLearningInfo userLearningInfo = userLearningInfoService.getOne(
                new QueryWrapper<UserLearningInfo>()
                        .eq("id", userId)
                        .eq("book_id", bookId)
        );
        if (userLearningInfo == null) {
            userLearningInfoService.save(new UserLearningInfo(userId, username, bookId, 0,0));
            return Result.success(0);
        }
        return Result.success(userLearningInfo.getProcess());
    }
    @GetMapping("/get_maxProcess")
    public Result getMaxProcess(String userId, String username, Integer bookId) {
        UserLearningInfo userLearningInfo = userLearningInfoService.getOne(
                new QueryWrapper<UserLearningInfo>()
                        .eq("id", userId)
                        .eq("book_id", bookId)
        );
        if (userLearningInfo == null) {
            userLearningInfoService.save(new UserLearningInfo(userId, username, bookId, 0,0));
            return Result.success(0);
        }
        return Result.success(userLearningInfo.getMaxProcess());
    }

    @PutMapping("/update_process")
    public Result updateProcess(String userId, String username, Integer bookId, Integer process) {
        UserLearningInfo userLearningInfo = userLearningInfoService.getOne(
                new QueryWrapper<UserLearningInfo>()
                        .eq("id", userId)
                        .eq("book_id", bookId)
        );
        if (userLearningInfo == null) {
            userLearningInfoService.save(new UserLearningInfo(userId, username, bookId, process,100));
        }
        return Result.success();
    }
}
