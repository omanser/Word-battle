package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.User;
import com.example.springboot.service.UserService;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String userId, String username) {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if (user == null) {
            user = new User(userId, username);
            userService.save(user);
            return Result.success();
        } else {
            return Result.error(1,"用户已存在");
        }
    }
    @PutMapping("/updateName")
    public Result update(String userId, String username) {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if (user != null) {
            user.setUsername(username);
            user.setUpdateTime(LocalDateTime.now());
            userService.updateById(user);
            return Result.success();
        } else {
            return Result.error(1,"用户不存在");
        }
    }
    @PatchMapping("/updateUrl")
    public Result updateUrl(String userId, @URL String url) {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if (user != null) {
            user.setUserPicUrl(url);
            user.setUpdateTime(LocalDateTime.now());
            userService.updateById(user);
            return Result.success();
        } else {
            return Result.error(1,"用户不存在");
        }
    }
    @GetMapping("get_score_rank")
    public Result getScoreRank(Integer bookId) {
        return Result.success(userService.getScoreRank(bookId));
    }
    @GetMapping("/getUser")
    public Result getUser(String userId) {
        User user = userService.getOne(new QueryWrapper<User>().eq("id", userId));
        if (user != null) {
            return Result.success(user);
        }
        return Result.error(1,"用户不存在");
    }
}
