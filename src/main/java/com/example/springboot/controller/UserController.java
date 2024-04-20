package com.example.springboot.controller;

import com.example.springboot.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/login.do")
    public UserInfo login(@RequestParam(value = "username", required = false, defaultValue = "") String username,
                          @RequestParam("password") String password){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setUserId(0);
        return userInfo;
    }

}
