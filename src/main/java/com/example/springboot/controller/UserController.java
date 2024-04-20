package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot.pojo.Result;
import com.example.springboot.pojo.UserInfo;
import com.example.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @RequestMapping("/updateUserScore")
    public Result updateUser(String userId, Integer bookId, Integer score, String userPicUrl, String username) {
        UserInfo userInfo = new UserInfo(userId, username, bookId, userPicUrl, score);
        userInfoService.saveOrUpdate(userInfo);
        return Result.success();
    }
}
