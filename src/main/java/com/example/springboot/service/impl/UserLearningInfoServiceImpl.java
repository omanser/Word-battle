package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.mapper.UserLearningInfoMapper;
import com.example.springboot.pojo.UserLearningInfo;
import com.example.springboot.service.UserLearningInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserLearningInfoServiceImpl extends ServiceImpl<UserLearningInfoMapper, UserLearningInfo> implements UserLearningInfoService {
}
